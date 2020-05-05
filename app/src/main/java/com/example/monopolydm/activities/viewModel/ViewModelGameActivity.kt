package com.example.monopolydm.activities.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monopolydm.activities.functions.getPlayer
import com.example.monopolydm.activities.interfaces.IDataChanged
import com.example.monopolydm.activities.interfaces.IPlayerInGameChanged
import com.example.monopolydm.model.Game
import com.example.monopolydm.model.Player
import com.example.monopolydm.model.Transaction
import com.example.monopolydm.network.Callback
import com.example.monopolydm.network.FireStoreService
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class ViewModelGameActivity: ViewModel() {

    val fireStoreService: FireStoreService = FireStoreService(FirebaseFirestore.getInstance())

    var playerSelector = true   // TRUE = USER PLAYER ,  FALSE = BANK PLAYER

    var vmPlayer:Player? = null  // pointer Player

    //USER PLAYER VARS --------------------------------------------
    lateinit var playerName:String
    lateinit var playersName : MutableList<String>
    var vmPlayerUser:Player? = null

    //GAME VARS ----------------------------------------------------
    lateinit var gameId:String
    lateinit var vmGame:Game

    //BANK VARS ----------------------------------------------------
    lateinit var playerBankName:String
    var vmPlayerBank:Player? = null

    //FRAGMENT INTERFACES  ----------------------------------------------------
    var iDataChangued: IDataChanged? = null
    var iDataChanguedMainF: IDataChanged? = null
    var iDataChanguedTransactionF: IDataChanged? = null
    var iDataChanguedAllTransactionF: IDataChanged? = null
    var iPlayersListChangedActionTransfer: IPlayerInGameChanged? = null

    //ALL PLAYERS
    lateinit var vmAllPlayers : MutableList<Player>


    //---------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------

    //USER PLAYER SECTION------------------------------------------------------------------

    val player:MutableLiveData<Player> by lazy {
        MutableLiveData<Player>().also {
            loadPlayer(playerName)
        }
    }

    fun getPlayer(UIplayerName:String):LiveData<Player>{
        playerName = UIplayerName
        return  player
    }

    fun loadPlayer(playerName:String){

        fireStoreService.getPlayerAndObserve(playerName,object : Callback<Player>{
            override fun onSuccess(result: Player?) {
                if(result != null) {
                    if(vmPlayerUser == null){
                        vmPlayerUser = result.copy(transactions = result.transactions?.toMutableList())
                        setPointerToPlayerUser()
                    }
                    else updatePlayerUser(result)

                    gameId = result.idGame!!
                    playerBankName = result.idGame!!
                }
                player.postValue(result)

            }

            override fun onFailed(exception: Exception) {
                Log.d("modelView",exception.message!!)
            }
        })

    }

    //GAME SECTION------------------------------------------------------------------

    val game:MutableLiveData<Game> by lazy {
        MutableLiveData<Game>().also {
            loadGame(gameId)
        }
    }

     fun getGame(UIgameId:String):LiveData<Game>{
        gameId = UIgameId
        return  game
    }

    fun loadGame(gameId:String){

        fireStoreService.getGameAndObserve(gameId,object : Callback<Game>{
            override fun onSuccess(result: Game?) {
               if(result != null) {
                   vmGame = result
                   playersName = result.IdsPlayers!!
               }
                game.postValue(result)

            }

            override fun onFailed(exception: Exception) {
                Log.d("modelView",exception.message!!)
            }
        })

    }

    //BANK PLAYER SECTION -------------------------------------

    val playerBank:MutableLiveData<Player> by lazy {
        MutableLiveData<Player>().also {
            loadPlayerBank(playerBankName)
        }
    }

    fun getPlayerBank(UIplayerName:String):LiveData<Player>{

        return  playerBank
    }

    fun loadPlayerBank(playerName:String){

        fireStoreService.getPlayerAndObserve(playerBankName,object : Callback<Player>{
            override fun onSuccess(result: Player?) {
                if(result != null) {
                    if (vmPlayerBank == null) vmPlayerBank = result.copy(transactions = result.transactions?.toMutableList())
                    else updatePlayerBank(result)
                }
                playerBank.postValue(result)
            }

            override fun onFailed(exception: Exception) {
                Log.d("modelView",exception.message!!)
            }
        })

    }
    //ALL PLAYERS  SECTION --------------------------------------------------------------

    val allPlayers:MutableLiveData<MutableList<Player>> by lazy {
        MutableLiveData<MutableList<Player>>().also {
            loadPlayers()
        }
    }

    fun getAllPlayers():LiveData<MutableList<Player>>{

        return  allPlayers
    }

    fun loadPlayers(){

        fireStoreService.getPlayersAndObserve(gameId, object : Callback<MutableList<Player>>{
            override fun onSuccess(result: MutableList<Player>?) {
                if(result != null) {
                    vmAllPlayers = result.toMutableList()
                }
                allPlayers.postValue(result)
            }

            override fun onFailed(exception: Exception) {
                Log.d("modelView",exception.message!!)
            }
        })

    }

    //MAKE TRANSACTION DB

    fun makeTransaction(p1:Player,p2:Player,callback: Callback<Boolean>){
        fireStoreService.setTransaction2Players(p1,p2,callback)
    }


    //INTERFACES ACTIONS---------------------------------------------------------------------

    fun sendPlayerChangesToFragments(player:Player){
        iDataChangued?.playerChanged(player)
        iDataChanguedMainF?.playerChanged(player)
        iDataChanguedTransactionF?.playerChanged(player)
        iDataChanguedAllTransactionF?.playerChanged(player)
    }

    fun sendGameChangesToFragments(game:Game){
        iDataChangued?.gameChanged(game)
        iDataChanguedMainF?.gameChanged(game)
        iDataChanguedTransactionF?.gameChanged(game)
        iDataChanguedAllTransactionF?.gameChanged(game)
    }

    fun sendPlayerListToFragments(listPlayers: MutableList<Player>){
        iPlayersListChangedActionTransfer?.playersListChanged(listPlayers)
    }


    // CHANGE POINTER PLAYER and UPDATES

    fun setPointerToPlayerUser(){
        vmPlayer = vmPlayerUser
    }

    fun setPointerToPlayerBank(){
        vmPlayer = vmPlayerBank
    }

    fun updatePlayerUser(player:Player) {
        vmPlayerUser?.money = player.money
        if(player.transactions != null) {
            if(vmPlayerUser?.transactions == null)
                vmPlayerUser?.transactions = mutableListOf()
            vmPlayerUser?.transactions!!.clear()
            vmPlayerUser?.transactions!!.addAll(player.transactions!!)
        }

    }

    fun updatePlayerBank(player:Player) {
        vmPlayerBank?.money = player.money
       if(player.transactions != null) {
           if(vmPlayerBank?.transactions == null)
               vmPlayerBank?.transactions = mutableListOf()
            vmPlayerBank?.transactions!!.clear()
            vmPlayerBank?.transactions!!.addAll(player.transactions!!)
        }
    }



}