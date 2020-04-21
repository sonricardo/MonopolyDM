package com.example.monopolydm.network

import android.content.ContentValues.TAG
import android.util.Log
import com.example.monopolydm.model.Player
import com.example.monopolydm.model.TAG_PLAYER_NAME
import com.example.monopolydm.model.TAG_PLAYER_PASSWORD
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*

const val PLAYERS_COLLECTION_NAME = "players"
const val GAMES_COLLECTION_NAME = "games"
const val TRANSACTIONS_COLLECTION_NAME = "transactions"

class FireStoreService(val db: FirebaseFirestore) {
    //create and update player
    fun createAndUpdatePlayer(newPlayer: Player, collection: String, callback: Callback<Void>) {
        /*val mapPlayer = hashMapOf(
                TAG_PLAYER_NAME to newPlayer.name,
                TAG_PLAYER_PASSWORD to newPlayer.password
        )
        */
        db.collection(collection).document(newPlayer.name)
            .set(newPlayer)
            .addOnSuccessListener { void -> callback.onSuccess(void) }
            .addOnFailureListener { e -> callback.onFailed(e) }
    }

    // get player
    fun readPlayer() {

    }

    //delete player
    fun deletePlayer() {

    }

    //consulta de documento
    fun queryDocument(collection: String, idDocument: String, callback: Callback<Boolean>) {
        db.collection(collection).whereEqualTo(FieldPath.documentId(), idDocument).get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    callback.onSuccess(false)
                } else {
                    callback.onSuccess(true)
                }
            }
            .addOnFailureListener { e -> callback.onFailed(e) }
    }

    fun queryField(
        collection: String,
        idDocument: String,
        tagFiled: String,
        fieldValue: Any,
        callback: Callback<Boolean>
    ) {
        db.collection(collection)
            .whereEqualTo(FieldPath.documentId(), idDocument)
            .whereEqualTo(tagFiled, fieldValue)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    callback.onSuccess(false)
                } else {
                    callback.onSuccess(true)
                }
            }
            .addOnFailureListener { e -> callback.onFailed(e) }
    }

   
}