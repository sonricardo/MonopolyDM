package com.example.monopolydm.activities.viewModel


import androidx.lifecycle.ViewModel
import com.example.monopolydm.model.Player
import com.example.monopolydm.model.Transaction


class ViewModelTransactionsFragment: ViewModel() {

    var vmTransactions:MutableList<Transaction> = arrayListOf()



   fun getTransactions(player: Player) {
       if (player.transactions != null) {
           vmTransactions.clear()
           vmTransactions.addAll(player.transactions!!)
       }
   }
}