package com.example.monopolydm.activities.functions

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.grpc.NameResolver

fun createGameMode(create: Button,join: Button,last: Button ){
    create.visibility = View.GONE
    last.visibility = View.GONE
    join.visibility = View.GONE
}

fun setChargingMonde(view:ViewGroup,set:Boolean){
    if(set){
        view.visibility = View.VISIBLE
    }else{
        view.visibility = View.GONE
    }
}