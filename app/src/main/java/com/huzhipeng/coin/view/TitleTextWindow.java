package com.huzhipeng.coin.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huzhipeng.coin.R;
import com.huzhipeng.coin.application.AppConfig;
import com.huzhipeng.coin.db.CoinEntity;
import com.huzhipeng.coin.ui.activity.main.CoinDetailActivity;
import com.huzhipeng.coin.ui.activity.main.MainActivity;
import com.socks.library.KLog;


/**
 * Created by Chao on 2018-09-08.
 */

public class TitleTextWindow implements View.OnTouchListener {

    private Context mContext;
    private WindowManager wm;
    private LinearLayout linearLayout;
    private int downY;
    private int downX;
    private CoinEntity coinEntity;
    private long unShowTime = 30*60*1000;

    public CoinEntity getCoinEntity() {
        return coinEntity;
    }

    public void setCoinEntity(CoinEntity coinEntity) {
        this.coinEntity = coinEntity;
    }

    public TitleTextWindow(Context context) {
        mContext = context;
    }

    private android.os.Handler mHander = new android.os.Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            animDismiss();
        }
    };

    /**
     * 动画，从顶部弹出
     */
    private void animShow(){
        //使用动画从顶部弹出
        ObjectAnimator animator = ObjectAnimator.ofFloat(linearLayout, "translationY", -linearLayout.getMeasuredHeight(), 0);
        animator.setDuration(300);
        animator.start();
    }

    /**
     * 动画，从顶部收回
     */
    private void animDismiss(){
        if (linearLayout == null || linearLayout.getParent() == null) {
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(linearLayout, "translationY", linearLayout.getTranslationY(), -linearLayout.getMeasuredHeight());
        animator.setDuration(300);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //移除HeaderToast  (一定要在动画结束的时候移除,不然下次进来的时候由于wm里边已经有控件了，所以会导致卡死)
                if (null != linearLayout && null != linearLayout.getParent()) {
                    wm.removeView(linearLayout);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });
    }
    /**
     * 动画，从顶部收回
     */
    private void animDismissX(boolean isLeft){
        if (linearLayout == null || linearLayout.getParent() == null) {
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(linearLayout, "translationX", linearLayout.getTranslationX(), !isLeft? -linearLayout.getMeasuredWidth() : linearLayout.getMeasuredWidth());
        animator.setDuration(300);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //移除HeaderToast  (一定要在动画结束的时候移除,不然下次进来的时候由于wm里边已经有控件了，所以会导致卡死)
                if (null != linearLayout && null != linearLayout.getParent()) {
                    wm.removeView(linearLayout);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });
    }

    /**
     * 向外部暴露显示的方法
     */
    public void show(String content){
        createTitleView(content);
        animShow();
        if (AppConfig.instance.getAlarmAutoDismiss()) {
            //10S后自动关闭
            mHander.sendEmptyMessageDelayed(20, 5000);
        }
    }
    /**
     * 向外部暴露关闭的方法
     */
    public void dismiss(){
        animDismiss();
    }


    /**
     * 视图创建方法
     */
    private void createTitleView(String content){
        //准备Window要添加的View
//        linearLayout = new LinearLayout(mContext);
//        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        linearLayout.setLayoutParams(layoutParams);
        linearLayout = (LinearLayout) View.inflate(mContext, R.layout.notification, null);//这里是你弹窗的UI
        // 为titleView设置Touch事件
        linearLayout.setOnTouchListener(this);
        TextView tvContent = linearLayout.findViewById(R.id.tvContent);
        TextView tvEnterApp = linearLayout.findViewById(R.id.tvEnterApp);
        TextView tvEnterBinace = linearLayout.findViewById(R.id.tvEnterBinance);
        tvEnterBinace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                //com.binance.dev/.activities.marketdetail.MarketDetailActivity
                Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("com.binance.dev");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
            }
        });
        tvContent.setText(content);
        TextView tvDismiss = linearLayout.findViewById(R.id.tvDismiss);
        tvDismiss.setText("屏蔽" + AppConfig.instance.getPriceGain() + "M");
        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                coinEntity.setUnIngnoreTime(System.currentTimeMillis() + AppConfig.instance.getPriceGain() * 60 * 1000);
            }
        });
        tvEnterApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
//                KLog.i(mContext.getPackageName());
//                Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                Intent intent = new Intent(mContext, CoinDetailActivity.class);
//                intent.putExtra("coin", coinEntity.getSymbol());
//                mContext.startActivity(intent);
            }
        });
//        linearLayout.addView(titleView);
        // 定义WindowManager 并且将View添加到WindowManagar中去
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wm_params = new WindowManager.LayoutParams();
        wm_params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        //这里需要注意，因为不同系统版本策略不一，所以需要根据版本判断设置type，否则会引起崩溃。
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {//大于android SDK 7.1.1
            wm_params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wm_params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        wm_params.gravity =  Gravity.TOP;
        wm_params.x = 0;
        wm_params.y = 0;
        wm_params.format = -3;  // 会影响Toast中的布局消失的时候父控件和子控件消失的时机不一致，比如设置为-1之后就会不同步
        wm_params.alpha = 1f;
        linearLayout.measure(0, 0);
        wm_params.height = linearLayout.getMeasuredHeight();
        wm.addView(linearLayout, wm_params);
    }

    int disMissType = -1;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getRawY();
                downX = (int) event.getRawX();
                disMissType = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) event.getRawY();
                int moveX = (int) event.getRawX();
                if (disMissType == -1) {
                    if (Math.abs(moveY - downY) >= Math.abs(moveX - downX)) {
                        disMissType = 1;
                        if (moveY - downY < 0) {//如果是向上滑动
                            linearLayout.setTranslationY(moveY - downY);
                        }
                    } else {
                        disMissType = 2;
                        linearLayout.setTranslationX(moveX - downX);
                    }
                } else if (disMissType == 1) {
                    if (moveY - downY < 0) {//如果是向上滑动
                        linearLayout.setTranslationY(moveY - downY);
                    }
                } else if (disMissType == 2){
                    linearLayout.setTranslationX(moveX - downX);
                }

                break;
            case MotionEvent.ACTION_UP:
                //达到一定比例后，松开手指将关闭弹窗
                if ((Math.abs(linearLayout.getTranslationY()) > linearLayout.getMeasuredHeight() / 8)) {
                    Log.e("TAG", "回弹");
                    animDismiss();
                } else if (Math.abs(linearLayout.getTranslationY()) > 0){
                    linearLayout.setTranslationY(0);
                } else if ((Math.abs(linearLayout.getTranslationX()) > linearLayout.getMeasuredWidth() / 10)) {
                    animDismissX(linearLayout.getTranslationX() > 0);
                } else if (Math.abs(linearLayout.getTranslationX()) > 0) {
                    linearLayout.setTranslationX(0);
                }
                break;
            default:
                break;
        }
        return true;
    }

    public interface OnTextWindowClick {
//        void on
    }
}