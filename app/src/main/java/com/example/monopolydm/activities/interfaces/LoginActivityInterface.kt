package com.example.monopolydm.activities.interfaces

interface ISingUp{

    fun onSingUpSucces()
    fun onSingUpFailed()

}

interface ISingIn{
    fun onSingInSucces()
    fun onSingInFailed()

}

interface IQueryPlayer{
    fun onQuerySucces()
    fun onQueryFailed()

}