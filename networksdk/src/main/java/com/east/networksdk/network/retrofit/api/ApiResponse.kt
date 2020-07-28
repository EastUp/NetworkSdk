package com.east.networksdk.network.retrofit.api

import retrofit2.Response

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-09-30
 * |---------------------------------------------------------------------------------------------------------------|
 */
sealed class ApiResponse<T> {
    companion object{
        fun <T> create(error : Throwable): Resource<T> {
//            return ApiErrorResponse(
//                error.message ?: "unknown error"
//            )
            val errorMsg = error.message ?: "unknown error"
//            ToastUtils.show(errorMsg)
            return Resource.error(errorMsg, null)
        }

        fun <T> create(response: Response<T>): Resource<T> {
            return if(response.isSuccessful){
                val body = response.body()
                if(body == null || response.code() == 204){
                    Resource.success(null)
//                    ApiEmptyResponse()
                }else{
                    Resource.success(body)
//                    ApiSuccessResponse(
//                        body,
//                        response.headers()
//                    )
                }
            }else{
                val msg = response.errorBody()?.string()
                val errorMsg = if(msg.isNullOrEmpty()){
                    response.message()
                }else{
                    msg
                }
                val error = errorMsg ?: "unknown error"
//                ToastUtils.show(error)
                Resource.error(error, null)
//                ApiErrorResponse(
//                    errorMsg ?: "unknown error"
//                )
            }
        }
    }
}

///**
// * HTTP 204响应的单独类，以便我们可以使ApiSuccessResponse的主体不为空。
// */
//class ApiEmptyResponse<T> : ApiResponse<T>()
//
///**
// * 响应成功
// */
//data class ApiSuccessResponse<T>(
//    val body: T,
//    val headers : Headers) : ApiResponse<T>()
//
///**
// *  响应失败
// */
//data class ApiErrorResponse<T>(val errorMessage:String):
//    ApiResponse<T>()