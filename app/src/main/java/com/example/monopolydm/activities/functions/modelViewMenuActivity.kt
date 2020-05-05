package com.example.monopolydm.activities.functions

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.monopolydm.model.Player
import com.example.monopolydm.network.Callback
import com.example.monopolydm.network.FireStoreService
import com.google.firebase.firestore.FirebaseFirestore
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

fun getPlayer(namePlayer:String,callBack: Callback<Player>){
    val fireStoreService = FireStoreService(FirebaseFirestore.getInstance())
    fireStoreService.getPlayer(namePlayer, object : Callback<Player>{
        override fun onSuccess(result: Player?) {
            callBack.onSuccess(result)
        }

        override fun onFailed(exception: Exception) {
            callBack.onFailed(exception)
        }
    })

}