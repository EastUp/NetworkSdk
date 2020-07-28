package com.east.networksdk.network.retrofit.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import com.east.networksdk.network.retrofit.api.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: retrofit返回LiveData的适配器
 *  @author: East
 *  @date: 2019-09-30
 * |---------------------------------------------------------------------------------------------------------------|
 */
class LiveDataCallAdapter<R>(private val responseType:Type) :CallAdapter<R,LiveData<Resource<R>>>{
    val TAG = LiveDataCallAdapter::class.java.simpleName

    var mHandler = Handler(Looper.getMainLooper())

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<Resource<R>> {
        return object :LiveData<Resource<R>>(){
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if(started.compareAndSet(false,true)){
                    call.enqueue(object :Callback<R>{
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            var resource = if(response.isSuccessful){
                                val body = response.body()
                                if(body == null || response.code() == 204){
                                    Resource.success(null)
                                }else{
                                    Resource.success(body)
                                }
                            }else{
                                val msg = response.errorBody()?.string()
                                val errorMsg = if(msg.isNullOrEmpty()){
                                    response.message()
                                }else{
                                    msg
                                }
                                val error = errorMsg ?: "unknown error"
                                Log.e(TAG,error)
//                                mHandler.post { ToastUtils.show(error) }
                                Resource.error(error,null)
                            }
                            postValue(resource)
                        }

                        override fun onFailure(call: Call<R>, t: Throwable) {
                            val errorMsg = t.message ?: "unknown error"
                            Log.e(TAG,errorMsg)
//                            mHandler.post { ToastUtils.show(errorMsg) }
                            postValue(Resource.error(errorMsg,null))
                        }

                    })
                }
            }
        }
    }
}