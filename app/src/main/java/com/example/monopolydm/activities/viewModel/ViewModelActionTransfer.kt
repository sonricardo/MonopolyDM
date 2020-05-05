package com.example.monopolydm.activities.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.monopolydm.model.Player
import com.example.monopolydm.model.Transaction
import com.example.monopolydm.network.Callback
import com.example.monopolydm.network.FireStoreService
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class ViewModelActionTransfer:ViewModel() {


    var playerSelected:String? = null
    var numberT:Int = 0
    lateinit var activityModel : ViewModelGameActivity





    fun uodatePlayerSelected(namePlayerSelected:String){
        playerSelected = namePlayerSelected
    }

    fun pushCalculator(number:Int):String{

        if (number>=0 && numberT<99999){

            numberT = numberT*10 +number
        }
        else if( numberT>0)
        {
            numberT = numberT/10
        }

        if(numberT > activityModel.vmPlayer?.money!!){
            numberT = activityModel.vmPlayer?.money!!
        }


        return "$ ${numberT}"
    }

    fun transferAction(callback: Callback<Boolean>){
        if(playerSelected != null) {


            val newTransaction = createTransaction(activityModel.vmPlayer?.name!!, playerSelected!!)
            var playerSeletedRealName = playerSelected
            val p1 = activityModel.vmPlayer?.copy()
            if(playerSelected == "Banco"){
                playerSeletedRealName = activityModel.vmPlayerBank?.name!!
            }
            val p2 = activityModel.vmAllPlayers.find { p -> p.name!! == playerSeletedRealName }?.copy()

            if(p1?.transactions == null){
                p1?.transactions = arrayListOf()
            }
            if(p2?.transactions == null){
                p2?.transactions = arrayListOf()
            }
            p1?.transactions!!.add(newTransaction)
            p2?.transactions!!.add(newTransaction)
            p1?.money = p1?.money!! - numberT
            p2?.money = p2?.money!! + numberT

            activityModel.makeTransaction(p1,p2,object : Callback<Boolean>{
                override fun onSuccess(result: Boolean?) {
                    if(result != null){
                        if(result){
                            numberT = 0
                        }
                        callback.onSuccess(result)
                    }
                    else{
                        callback.onFailed(java.lang.Exception("error transfer"))
                    }
                }

                override fun onFailed(exception: Exception) {
                    callback.onFailed(exception)
                }
            })


        }
        else callback.onSuccess(false)


    }

    fun createTwoPlayers(players:MutableList<Player>){

    }



    private fun createTransaction(nPlayer1:String, nPlayer2:String): Transaction {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val date = currentDate.toString()
        val ammountT = numberT

        val newT = Transaction(nPlayer1,nPlayer2,date,ammount = ammountT)
        return  newT
    }


    fun setReadyListNames(list:MutableList<String>,activePlayer:String,playerBankName:String){

        for(player in activityModel.vmAllPlayers){
            list.add(player.name!!)
        }

        list.remove(activePlayer)


    }



}