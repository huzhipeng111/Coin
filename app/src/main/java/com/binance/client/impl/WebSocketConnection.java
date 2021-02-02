package com.binance.client.impl;

import com.binance.client.coin.SubscriptionOptions;
import com.binance.client.constant.BinanceApiConstants;
import com.binance.client.exception.BinanceApiException;
import com.binance.client.impl.utils.JsonWrapper;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketConnection extends WebSocketListener {


    private static int connectionCounter = 0;

    public enum ConnectionState {
        IDLE, DELAY_CONNECT, CONNECTED, CLOSED_ON_ERROR
    }

    private WebSocket webSocket = null;

    private volatile long lastReceivedTime = 0;

    private volatile ConnectionState state = ConnectionState.IDLE;
    private int delayInSecond = 0;

    private final WebsocketRequest request;
    private final Request okhttpRequest;
    private final WebSocketWatchDog watchDog;
    private final int connectionId;
    private final boolean autoClose;

    private String subscriptionUrl = BinanceApiConstants.WS_API_BASE_URL;

    WebSocketConnection(String apiKey, String secretKey, SubscriptionOptions options, WebsocketRequest request,
            WebSocketWatchDog watchDog) {
        this(apiKey, secretKey, options, request, watchDog, false);
    }

    WebSocketConnection(String apiKey, String secretKey, SubscriptionOptions options, WebsocketRequest request,
            WebSocketWatchDog watchDog, boolean autoClose) {
        this.connectionId = WebSocketConnection.connectionCounter++;
        this.request = request;
        this.autoClose = autoClose;

        this.okhttpRequest = request.authHandler == null ? new Request.Builder().url(subscriptionUrl).build()
                : new Request.Builder().url(subscriptionUrl).build();
        this.watchDog = watchDog;
    }

    int getConnectionId() {
        return this.connectionId;
    }

    void connect() {
        if (state == ConnectionState.CONNECTED) {
            return;
        }
        webSocket = RestApiInvoker.createWebSocket(okhttpRequest, this);
    }

    void reConnect(int delayInSecond) {
        if (webSocket != null) {
            webSocket.cancel();
            webSocket = null;
        }
        this.delayInSecond = delayInSecond;
        state = ConnectionState.DELAY_CONNECT;
    }

    void reConnect() {
        if (delayInSecond != 0) {
            delayInSecond--;
        } else {
            connect();
        }
    }

    long getLastReceivedTime() {
        return this.lastReceivedTime;
    }

    void send(String str) {
        boolean result = false;
        if (webSocket != null) {
            result = webSocket.send(str);
        }
        if (!result) {
            closeOnError();
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        lastReceivedTime = System.currentTimeMillis();

        try {
            JsonWrapper jsonWrapper = JsonWrapper.parseFromString(text);

            if (jsonWrapper.containKey("result") || jsonWrapper.containKey("id")) {
                // onReceiveAndClose(jsonWrapper);
            } else {
                onReceiveAndClose(jsonWrapper);
            }

        } catch (Exception e) {
            closeOnError();
        }
    }

    private void onError(String errorMessage, Throwable e) {
        if (request.errorHandler != null) {
            BinanceApiException exception = new BinanceApiException(BinanceApiException.SUBSCRIPTION_ERROR, errorMessage, e);
            request.errorHandler.onError(exception);
        }
    }

    private void onReceiveAndClose(JsonWrapper jsonWrapper) {
        onReceive(jsonWrapper);
        if (autoClose) {
            close();
        }
    }

    @SuppressWarnings("unchecked")
    private void onReceive(JsonWrapper jsonWrapper) {
        Object obj = null;
        try {
            obj = request.jsonParser.parseJson(jsonWrapper);
        } catch (Exception e) {
            onError("Failed to parse server's response: " + e.getMessage(), e);
        }
        try {
            request.updateCallback.onReceive(obj);
        } catch (Exception e) {
            onError("Process error: " + e.getMessage() + " You should capture the exception in your error handler", e);
        }
    }

    public ConnectionState getState() {
        return state;
    }

    public void close() {
        webSocket.cancel();
        webSocket = null;
        watchDog.onClosedNormally(this);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        if (state == ConnectionState.CONNECTED) {
            state = ConnectionState.IDLE;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        this.webSocket = webSocket;
        watchDog.onConnectionCreated(this);
        if (request.connectionHandler != null) {
            request.connectionHandler.handle(this);
        }
        state = ConnectionState.CONNECTED;
        lastReceivedTime = System.currentTimeMillis();
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        onError("Unexpected error: " + t.getMessage(), t);
        closeOnError();
    }

    private void closeOnError() {
        if (webSocket != null) {
            this.webSocket.cancel();
            state = ConnectionState.CLOSED_ON_ERROR;
        }
    }
}
