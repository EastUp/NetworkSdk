package com.east.networksdk.network.okgo;

/**
 * 描述：对OKGO网络请求的进一步封装，提高代码的复用性,适用于东恒
 * Created by East at 2018/5/17 17:07
 */
public class OkGoUtils {

   /* public void init(Application app){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(30000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(30000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(30000, TimeUnit.MILLISECONDS);
        //超时后是否重试
        builder.retryOnConnectionFailure(false);
        OkGo.getInstance().init(app).setOkHttpClient(builder.build());
    }

    private static final String TAG = OkGoUtils.class.getSimpleName();
    public static void get(HttpParams params, final String url, final Context context, final RequestListener listener) {
        if(!NetworkUtils.isConnected()){
            showToast( "您还没有联网，请联网后重试...");
            return;
        }
        OkGo.<String>get(url)
                .tag(context)
                .retryCount(0)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        Log.i(TAG,url + "\n :" + result);
                        try {
                            JSONObject object = new JSONObject(result);
                            int code = Integer.parseInt(object.getString("error_code"));
                            if (code == 0) {
                                listener.onSuccessCallBack(object);
                            } else {
                                showToast( object.getString("reason"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG,e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        try {
                            String error_msg = response.getException().getMessage();
                            listener.onErrorListener(TextUtils.isEmpty(error_msg)?null:error_msg);
                            if(TextUtils.isEmpty(error_msg))
                                return;
                            if (error_msg.trim().contains("network error".trim())) {
                                showToast( "网络连接异常，请检查您网络...");
                            } else {
                                showToast( error_msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public static void post(JSONObject params, final String url, final Context context, final RequestListener listener) {
        if(!NetworkUtils.isConnected()){
            showToast( "您还没有联网，请联网后重试...");
            return;
        }
        OkGo.<String>post(url)
                .tag(context)
                .retryCount(0)
                .upJson(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        Log.i(TAG,url + "\n :" + result);
                        try {
                            JSONObject object = new JSONObject(result);
                            int code = 0;
                            if(object.has("status_code")){
                                code = Integer.parseInt(object.getString("status_code"));
                            }else if(object.has("code")){
                                code = Integer.parseInt(object.getString("code"));
                            }
                            if (code == 200) {
                                listener.onSuccessCallBack(object);
                            } else {
                                showToast( object.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG,e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        try {
                            String error_msg = response.getException().getMessage();
                            listener.onErrorListener(TextUtils.isEmpty(error_msg)?null:error_msg);
                            if(TextUtils.isEmpty(error_msg))
                                return;
                            Log.i(TAG,"error_msg：" + error_msg);
                            if (error_msg.trim().contains("network error".trim())) {
                                showToast( "网络连接异常，请检查您网络...");
                            } else if (error_msg.trim().contains("timeout")) {
                                showToast( "网络连接异常，请检查您网络...");
                            } else {
                                showToast( error_msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    *//**
     * 带Dailog的请求
     *
     * @param canceledOnTouchOutside 是否点击等待框外面可以关闭
     * @param success_pd_dissmiss 请求成功后 Dialog是否关闭
     * @param params
     * @param url
     * @param context
     * @param listener
     *//*
    public static void postWithLoadingDialog(boolean canceledOnTouchOutside,final boolean success_pd_dissmiss, JSONObject params, final String url, final Context context, final RequestSuccessListener listener) {
        if(!NetworkUtils.isConnected()){
            showToast( "您还没有联网，请联网后重试...");
            return;
        }
//        LoadingDialogUtils.show(context,"请求中...");
//        LoadingDialogUtils.setIsTouchOutsideCancelable(canceledOnTouchOutside);
        OkGo.<String>post(url)
                .tag(context)
                .retryCount(0)
                .upJson(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (success_pd_dissmiss) {
//                            LoadingDialogUtils.dismiss();
                        }
                        String result = response.body();
                        Log.i(TAG,url + "\n :" + result);
                        try {
                            JSONObject object = new JSONObject(result);
                            int code = 0;
                            if(object.has("status_code")){
                                code = Integer.parseInt(object.getString("status_code"));
                            }else if(object.has("code")){
                                code = Integer.parseInt(object.getString("code"));
                            }
                            if (code == 200) {
                                listener.onSuccessCallBack(object);
                            } else {
//                                LoadingDialogUtils.dismiss();
                                String message = object.getString("msg");
                                Log.i(TAG,"error_msg：" + message);
                                showToast( message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG,e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        LoadingDialogUtils.dismiss();
                        try {
                            String error_msg = response.getException().getMessage();
                            if(TextUtils.isEmpty(error_msg))
                                return;
                            Log.i(TAG,"error_msg：" + error_msg);
                            if (error_msg.trim().contains("network error".trim())) {
                                showToast( "网络连接异常，请检查您网络...");
                            } else if (error_msg.trim().contains("timeout")) {
                                showToast( "网络连接异常，请检查您网络...");
                            } else {
                                showToast( error_msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    *//**
     * 带Dailog的请求
     *
     * @param canceledOnTouchOutside 是否点击等待框外面可以关闭
     * @param success_pd_dissmiss 请求成功后 Dialog是否关闭
     * @param params
     * @param url
     * @param context
     * @param listener
     *//*
    public static void postWithLoadingDialog(boolean canceledOnTouchOutside,final boolean success_pd_dissmiss, JSONObject params, final String url, final Context context, final RequestListener listener) {
        if(!NetworkUtils.isConnected()){
            showToast( "您还没有联网，请联网后重试...");
            return;
        }
//        LoadingDialogUtils.show(context,"请求中...");
//        LoadingDialogUtils.setIsTouchOutsideCancelable(canceledOnTouchOutside);
        OkGo.<String>post(url)
                .tag(context)
                .retryCount(0)
                .upJson(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (success_pd_dissmiss) {
//                            LoadingDialogUtils.dismiss();
                        }
                        String result = response.body();
                        Log.i(TAG,url + "\n :" + result);
                        try {
                            JSONObject object = new JSONObject(result);
                            int code = 0;
                            if(object.has("status_code")){
                                code = Integer.parseInt(object.getString("status_code"));
                            }else if(object.has("code")){
                                code = Integer.parseInt(object.getString("code"));
                            }
                            if (code == 200) {
                                listener.onSuccessCallBack(object);
                            } else {
//                                LoadingDialogUtils.dismiss();
                                String message = object.getString("msg");
                                Log.i(TAG,"error_msg：" + message);
                                listener.onErrorListener(message);
                                showToast( message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG,e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        LoadingDialogUtils.dismiss();
                        try {
                            String error_msg = response.getException().getMessage();
                            listener.onErrorListener(TextUtils.isEmpty(error_msg)?null:error_msg);
                            if(TextUtils.isEmpty(error_msg))
                               return;
                            Log.i(TAG,"error_msg：" + error_msg);
                            if (error_msg.trim().contains("network error".trim())) {
                                showToast( "网络连接异常，请检查您网络...");
                            } else if (error_msg.trim().contains("timeout")) {
                                showToast( "网络连接异常，请检查您网络...");
                            } else {
                                showToast( error_msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    *//**
     * 文件下载
     *
     * @param progressDialog
     * @param success_pd_dissmiss
     * @param url
     * @param context
     * @param dir
     * @param file_name
     * @param downloadListener
     *//*
    public static void downloadFile(final ProgressDialog progressDialog, final boolean success_pd_dissmiss, final String url, final Context context, String dir, String file_name, final DownloadListener downloadListener) {
        if(!NetworkUtils.isConnected()){
            showToast( "您还没有联网，请联网后重试...");
            return;
        }
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!progressDialog.isShowing()) {
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    progressDialog.setCancelable(true);
                }
            });
        }
        OkGo.<File>get(url)
                .tag(context)
                .retryCount(0)
                .execute(new FileCallback(dir, file_name) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        File file1 = response.body();
                        Log.i(TAG,"文件下载路径为：\n" + file1.getAbsolutePath());
                        if (success_pd_dissmiss) {
                            progressDialog.dismiss();
                        }
                        downloadListener.onSuccess();
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        downloadListener.downloadProgress(progress);
                        //这里回调下载速度
                        progressDialog.setMessage("当前下载进度为：" + progress.fraction * 100 + "%");
                    }

                    @Override
                    public void onError(Response<File> response) {
                        progressDialog.dismiss();
                        try {
                            String error_msg = response.getException().getMessage();
                            downloadListener.onError(error_msg);
                            if(TextUtils.isEmpty(error_msg))
                               return;
                            Log.i(TAG,"error_msg：" + error_msg);
                            if (error_msg.trim().contains("network error".trim())) {
                                showToast( "网络连接异常，请检查您网络...");
                            } else {
                                if(error_msg.trim().contains("unexpected end of stream".trim())){
                                    showToast( "网络连接异常，请检查您网络...");
                                    return;
                                }
                                showToast( error_msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public static void showToast( String msg) {
//        ToastUtils.show(msg);
    }

    *//**
     * 网络请求成功的监听
     *//*
    public interface RequestSuccessListener {
        void onSuccessCallBack(JSONObject object);
    }

    *//**
     *  网络请求后的回调
     *//*
    public static abstract class RequestListener

    {
        public abstract void onSuccessCallBack(JSONObject object);
        public void onErrorListener(String error){}
    }

    *//**
     *
     *//*
    public static abstract class DownloadListener
    {
        public abstract void onSuccess();
        public abstract void downloadProgress(Progress progress);
        void onError(String error){}
    }*/

}
