package com.example.monopolydm.network

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.monopolydm.model.Game
import com.example.monopolydm.model.Player
import com.example.monopolydm.model.Transaction
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_login.view.*

const val PLAYERS_COLLECTION_NAME = "players"
const val GAMES_COLLECTION_NAME = "games"
const val TRANSACTIONS_COLLECTION_NAME = "transactions"

class FireStoreService(val db: FirebaseFirestore) {
    //create and update player
    fun createAndUpdatePlayer(newPlayer: Player, collection: String, callback: Callback<Void>) {

        db.collection(collection).document(newPlayer.name!!)
            .set(newPlayer)
            .addOnSuccessListener { void -> callback.onSuccess(void) }
            .addOnFailureListener { e -> callback.onFailed(e) }
    }

    fun setTransaction2Players(p1:Player,p2:Player, callback: Callback<Boolean>) {
        val op1 = db.collection(PLAYERS_COLLECTION_NAME).document(p1.name!!)
        val op2 = db.collection(PLAYERS_COLLECTION_NAME).document(p2.name!!)

        db.runBatch { batch ->
            // Set the value of 'NYC'
            batch.set(op1,p1)
            batch.set(op2,p2)




        }.addOnCompleteListener {

            callback.onSuccess(true)
        }.addOnCanceledListener{ callback.onSuccess(false)}


    }

    fun getPlayer(namePlayer: String, callback: Callback<Player>){
        db.collection(PLAYERS_COLLECTION_NAME).document(namePlayer)
            .get()
            .addOnSuccessListener { document ->
                if(document.exists()){
                    try {
                        callback.onSuccess(document.toObject(Player::class.java))
                    }
                    catch (e:Exception){
                        Log.d("network",e.message)
                    }
                }
                else
                    callback.onFailed(java.lang.Exception())
            }
            .addOnFailureListener{ e ->
                callback.onFailed(e)
            }

    }

    fun getPlayers(callback: Callback<MutableList<Player>>){
        db.collection(PLAYERS_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { data ->
                val players = mutableListOf<Player>()
                for(document in data!!.documents) {
                    if(document.exists()) {
                        try {
                            val player = document.toObject(Player::class.java)
                            if(player != null) {
                                Log.e("lo que sea", player.name)
                                players.add(player)
                            }
                        }
                        catch (e:Exception){
                            Log.e("lo que sea", e.message)
                        }


                    }

                }

                callback.onSuccess(players)
            }

    }

    fun getPlayerAndObserve(namePlayer: String, callback: Callback<Player>){
        db.collection(PLAYERS_COLLECTION_NAME).document(namePlayer)

            .addSnapshotListener { document,e ->
                if(document!!.exists()){
                    try {
                        callback.onSuccess(document.toObject(Player::class.java))
                    }
                    catch (e:Exception){
                        Log.d("network",e.message!!)
                    }
                }
                else
                    callback.onFailed(e as Exception)
            }


    }

    fun getGameAndObserve(id: String, callback: Callback<Game>){
        db.collection(GAMES_COLLECTION_NAME).document(id)

            .addSnapshotListener { document,e ->
                if(document!!.exists()){
                    try {
                        callback.onSuccess(document.toObject(Game::class.java))
                    }
                    catch (e:Exception){
                        Log.d("network",e.message!!)
                    }
                }
                else
                    callback.onFailed(e as Exception)
            }


    }

    fun getGame(gameId: String, callback: Callback<Game>){
        db.collection(GAMES_COLLECTION_NAME).document(gameId)
            .get()
            .addOnSuccessListener { document ->
                if(document.exists()){
                    try {
                        callback.onSuccess(document.toObject(Game::class.java))
                    }
                    catch (e:Exception){
                        Log.d("network",e.message!!)
                    }
                }
                else
                    callback.onFailed(java.lang.Exception())
            }
            .addOnFailureListener{ e ->
                callback.onFailed(e)
            }

    }



    fun updatePlayer(playerName: String,gameId:String, initMoney:Int, callback: Callback<Void>) {

        db.collection(PLAYERS_COLLECTION_NAME).document(playerName)
            .update("idGame",gameId,"money",initMoney)

            .addOnSuccessListener { void -> callback.onSuccess(void) }
            .addOnFailureListener { e -> callback.onFailed(e) }
    }

    fun createBankAndUpdatePlayer(playerName: String,gameId:String, initMoney:Int, callback: Callback<Boolean>){

        val op1 = db.collection(PLAYERS_COLLECTION_NAME).document(playerName)
        val op2 = db.collection(PLAYERS_COLLECTION_NAME).document(gameId)

        db.runBatch { batch ->
            // Set the value of 'NYC'
            batch.update(op1,"idGame",gameId)
            batch.update(op1,"money",initMoney)
            batch.update(op1,"transactions",null)

            val playerBank = Player(gameId,500000,null,gameId)
            batch.set(op2,playerBank)


        }.addOnCompleteListener {

            callback.onSuccess(true)
        }

    }

    fun createGame(newGame: Game,  callback: Callback<String>) {

        db.collection(GAMES_COLLECTION_NAME).add(newGame)
            .addOnSuccessListener { document ->

                callback.onSuccess(document.id)
            }
            .addOnFailureListener { e -> callback.onFailed(e) }
    }

    fun updateGame(game: Game,  callback: Callback<Void>) {

        db.collection(GAMES_COLLECTION_NAME).document(game.id!!).set(game)
            .addOnSuccessListener { void ->

                callback.onSuccess(void)
            }
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

    fun consultGamePlayer(gameName:String, namePlayer:String, tagName:String, callback:Callback<Game>)
    {
        db.collection(GAMES_COLLECTION_NAME)
            .whereEqualTo("nameGame", gameName)
            .whereEqualTo(tagName, namePlayer)
            .get()
            .addOnSuccessListener { result ->

                try {
                    callback.onSuccess(result.documents.first().toObject(Game::class.java))
                }
                catch (e:Exception){
                    Log.d("network",e.message)
                    callback.onSuccess(null)
                }


            }
            .addOnFailureListener { e -> callback.onFailed(e) }
    }


    fun getAllPlayersAndObserve(callback: Callback<MutableList<Player>>) {

        var players = mutableListOf<Player>()
        db.collection(PLAYERS_COLLECTION_NAME).addSnapshotListener{ data, e ->

            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
             players.clear()

            for(document in data!!.documents) {
                if(document.exists()) {
                    try {
                           val player = document.toObject(Player::class.java)
                           if(player != null) {
                               Log.e("lo que sea", player.name)
                               players.add(player)
                           }
                       }
                       catch (e:Exception){
                           Log.e("lo que sea", e.message)
                       }


                   }

                }

            callback.onSuccess(players)

        }
    }

    fun getPlayersAndObserve(idGame:String,callback: Callback<MutableList<Player>>) {

        var players = mutableListOf<Player>()
        db.collection(PLAYERS_COLLECTION_NAME).whereEqualTo("idGame",idGame).addSnapshotListener{ data, e ->

            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            players.clear()

            for(document in data!!.documents) {
                if(document.exists()) {
                    try {
                        val player = document.toObject(Player::class.java)
                        if(player != null) {
                            Log.e("lo que sea", player.name)
                            players.add(player)
                        }
                    }
                    catch (e:Exception){
                        Log.e("lo que sea", e.message)
                    }


                }

            }

            callback.onSuccess(players)

        }
    }

    fun getAndObservePlayerTransactions(namePlayer: String, callback: Callback<MutableList<Transaction>>){
        db.collection(PLAYERS_COLLECTION_NAME).document(namePlayer).addSnapshotListener{ doc,e ->
            if(doc != null){
                val player = doc.toObject(Player::class.java)
                callback.onSuccess(player!!.transactions)
            }
            else{
                callback.onFailed(e as Exception)
            }
        }
    }




}

