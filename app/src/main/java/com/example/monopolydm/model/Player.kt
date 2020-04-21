package com.example.monopolydm.model

const val TAG_PLAYER_NAME = "name"
const val TAG_PLAYER_PASSWORD = "password"
const val TAG_PLAYER_ID_GAME = "idGame"

data class Player( val name:String, var money:Int = 0, val password:String, var idGame:Int = 0,
                   var IdsTransactions: MutableList<Int>? = null)