package com.example.monopolydm.model



 data class Player( val name:String? = null, var money:Int = 0, val password:String? = null, var idGame:String? = null,
                   var transactions: MutableList<Transaction>? = null)


