package com.huzhipeng.coin.utils;

import java.io.IOException;
import java.math.BigDecimal;

import io.neow3j.crypto.transaction.RawTransactionOutput;
import io.neow3j.protocol.Neow3j;
import io.neow3j.protocol.exceptions.ErrorResponseException;
import io.neow3j.protocol.http.HttpService;
import io.neow3j.wallet.Account;
import io.neow3j.wallet.AssetTransfer;

public class NeoUtil {
    public static void neoTransfer() {
        Neow3j neow3j = Neow3j.build(new HttpService("https://node2.neocompiler.io"));
        Account acct = Account.fromWIF("KzviPYuqHtQvw4T6vkbxJGnyoRGo1yYULAAn6WbTLwpQboHEkXcW").build();
        try {
            acct.updateAssetBalances(neow3j);
            RawTransactionOutput output = new RawTransactionOutput("0d821bd7b6d53f5c2b40e217c6defc8bbe896cf5", "1", "AbLSovbur8DHHwJdwDjCqEtDERnNMNnZhA");
            AssetTransfer transfer = new AssetTransfer.Builder(neow3j)
                    .account(acct)
                    .output(output)
                    .networkFee(new BigDecimal("0.00000001"))
                    .build()
                    .sign()
                    .send();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        }
    }
}
