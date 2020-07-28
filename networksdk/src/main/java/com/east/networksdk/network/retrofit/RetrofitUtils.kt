package com.east.networksdk.network.retrofit

import com.east.networksdk.network.retrofit.util.LiveDataCallAdapterFactory
import com.east.networksdk.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: Retrofit工具类
 *  @author: East
 *  @date: 2019-09-30
 * |---------------------------------------------------------------------------------------------------------------|
 */
class RetrofitUtils {

    var okHttpClient: OkHttpClient? = null
    var baseUrl: String? = null

    constructor() {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(loggingInterceptor)
        }
//            clientBuilder.cookieJar(object : CookieJar {
//                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
//                    if (url.toString().startsWith(
//                            "https://www.wanandroid.com/user/login?"
//                        )
//                    ) {
//                        SP.saveCookies(cookies)
//                    }
//                }
//
//                override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
//                    return SP.getCookies()
//                }
//            })
        okHttpClient = clientBuilder.build()
    }

    //初始化Url
    fun init(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    /**
     * 获取Retrofit
     */
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl!!)
            .client(okHttpClient!!)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        @Volatile
        private var instance: RetrofitUtils? = null

        fun getInstance(): RetrofitUtils =
            instance ?: synchronized(this) {
                instance ?: RetrofitUtils().also { instance = it }
            }


    }


}