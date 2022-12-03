package com.salam.testapp.utils

class Event<out T>(private val content: T) {

    var hasBeenHandled = false
    private set

    fun getCoontentIfNotHandled(): T?{
        return if(hasBeenHandled){
            null
        }
        else{
            hasBeenHandled = true
            content
        }
    }

    fun peakContent():T = content
}