package com.east.networksdk.network.retrofit.rxjava;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseSubscriber<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        // e ：网络异常，解析异常，结果处理过程中异常
        e.printStackTrace();
//        LogUtils.e("Retrofit", "错误：" + e == null ? "未知异常" : e.toString());
        if (e instanceof ErrorHandle.ServiceError) {
            ErrorHandle.ServiceError serviceError = (ErrorHandle.ServiceError) e;
            onError("", serviceError.getMessage());
        } else {
            /*if (e instanceof HttpException) {
                if (((HttpException) e).code() == 401) {
                    ToastUtils.show("请重新登录");
                    Activity activity = ActivityManager.getInstance().stackTopActivity();
                    Intent extraIntent = activity.getIntent();
                    Intent intent = new Intent(activity, LoginActivity.class);
                    intent.putExtra("className", activity.getClass());
                    intent.putExtra("reLogin", true);
                    if (extraIntent != null)
                        intent.putExtra("extraIntent", extraIntent);
                    activity.startActivity(intent);
                    activity.finish();
                    return;
                }
            }*/

            if (e != null) {
                if (e.getMessage() != null)
                    onError("", e.getMessage());
                else
                    onError("", "未知异常");
            } else
                onError("", "未知异常");
        }
    }

    protected void onError(String errorCode, String errorMessage) {
//        ToastUtils.show(errorMessage);
    }
}
