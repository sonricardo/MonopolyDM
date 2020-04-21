package com.example.monopolydm.network
/*
    Esta es una interface mediadora para los listener que son activados cuando se intenta hacer
    modificaciones en la db
*/

interface Callback<T> {
    fun onSuccess(result:T?)
    fun onFailed(exception:Exception)
}