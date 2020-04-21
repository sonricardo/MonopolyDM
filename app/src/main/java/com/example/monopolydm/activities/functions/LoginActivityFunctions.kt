package com.example.monopolydm.activities.functions

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.monopolydm.activities.LoginActivity
import com.example.monopolydm.activities.interfaces.IQueryPlayer
import com.example.monopolydm.activities.interfaces.ISingIn
import com.example.monopolydm.activities.interfaces.ISingUp
import com.example.monopolydm.model.Player
import com.example.monopolydm.model.TAG_PLAYER_PASSWORD
import com.example.monopolydm.network.Callback
import com.example.monopolydm.network.FireStoreService
import com.example.monopolydm.network.PLAYERS_COLLECTION_NAME
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*


fun createPlayer(name:String,password:String): Player {
    return Player(name = name, password = password)
}

fun signUpPlayer(player: Player, db:FirebaseFirestore, callback: ISingUp) {

    findPlayerName(player.name,db, object :IQueryPlayer {
        override fun onQueryFailed() {

            val fireStoreServices = FireStoreService(db)
            fireStoreServices.createAndUpdatePlayer(player, PLAYERS_COLLECTION_NAME, object : Callback<Void> {
                override fun onSuccess(result: Void?) {
                    callback.onSingUpSucces()
                }

                override fun onFailed(exception: Exception) {
                    callback.onSingUpFailed()
                }
            })
        }

        override fun onQuerySucces() {
            callback.onSingUpFailed()
        }

    })

}


fun findPlayerName(name:String,   db:FirebaseFirestore, callback: IQueryPlayer){
    val fireStoreServices = FireStoreService(db)
    fireStoreServices.queryDocument(PLAYERS_COLLECTION_NAME,name,object : Callback<Boolean> {
        override fun onSuccess(result: Boolean?) {
            try {
                if(result!!){
                    callback.onQuerySucces()
                }
                else {
                    callback.onQueryFailed()
                }

            }catch (e:java.lang.Exception) {
                Log.e(TAG,e.message)
                callback.onQueryFailed()
            }
        }

        override fun onFailed(exception: Exception) {
            Log.e(TAG,exception.message)
        }
    })
}

fun findPlayer(name:String, password: String,  db:FirebaseFirestore, callback: IQueryPlayer){
    val fireStoreServices = FireStoreService(db)
    fireStoreServices.queryField(PLAYERS_COLLECTION_NAME,name, TAG_PLAYER_PASSWORD, password,object : Callback<Boolean> {
        override fun onSuccess(result: Boolean?) {
            try {
                if(result!!){
                    callback.onQuerySucces()
                }
                else {
                    callback.onQueryFailed()
                }

            }catch (e:java.lang.Exception) {
                Log.e(TAG,e.message)
                callback.onQueryFailed()
            }
        }

        override fun onFailed(exception: Exception) {
            Log.e(TAG,exception.message)
        }
    })
}

fun singInPlayer(player: Player, db:FirebaseFirestore, callback: ISingIn) {

    findPlayer(player.name,player.password,db, object :IQueryPlayer {
        override fun onQuerySucces() {

            callback.onSingInSucces()
        }

         override fun onQueryFailed() {
            callback.onSingInFailed()
        }

    })

}
