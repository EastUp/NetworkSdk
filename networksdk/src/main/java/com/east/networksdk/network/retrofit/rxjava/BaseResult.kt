package com.east.networksdk.network.retrofit.rxjava

import com.google.gson.annotations.SerializedName

open class BaseResult {
    @SerializedName(value = "status", alternate = arrayOf("code"))
    var code:Int = -1
    var message: String ? = null

    fun isOk(): Boolean{
        return 200 == code||0==code
    }

}

