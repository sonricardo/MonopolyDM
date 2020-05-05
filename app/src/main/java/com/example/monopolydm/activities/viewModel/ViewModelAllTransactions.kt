package com.example.monopolydm.activities.viewModel

import androidx.lifecycle.ViewModel
import com.example.monopolydm.model.Player
import com.example.monopolydm.model.Transaction

class ViewModelAllTransactions: ViewModel() {


    var vmTransactions:MutableList<Transaction> = mutableListOf()



    fun getTransactions(player: Player) {
        if (player.transactions != null) {
            vmTransactions.clear()
            vmTransactions.addAll(player.transactions!!)
        }
    }
}