package com.east.networksdk.network.retrofit.util

import androidx.lifecycle.LiveData
import com.east.networksdk.network.retrofit.api.Resource
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:retrofit返回LiveData的适配器工厂
 *  @author: East
 *  @date: 2019-09-29
 * |---------------------------------------------------------------------------------------------------------------|
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if(getRawType(returnType) != LiveData::class.java)
            return null
        val observableType = getParameterUpperBound(0,returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if(rawObservableType != Resource::class.java)
            throw IllegalArgumentException("type must be a resource")
        if(observableType !is ParameterizedType)
            throw IllegalArgumentException("resource must be parameterized")
        val bodyType = getParameterUpperBound(0,observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}