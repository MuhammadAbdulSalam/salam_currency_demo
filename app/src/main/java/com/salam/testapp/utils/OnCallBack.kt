package com.salam.testapp.utils

interface OnCallBack<T> {
    fun onSuccess(response: T)
    fun onError(message: String, errorCode: Int)
}