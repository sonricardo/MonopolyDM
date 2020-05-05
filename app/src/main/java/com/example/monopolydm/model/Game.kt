package com.example.monopolydm.model

import java.util.concurrent.atomic.AtomicInteger

data class Game(var isEneable:Boolean = true, var IdsPlayers:MutableList<String>? = null, val nameGame:String? = null,
                val hosterName:String? = null, val initMoney:Int = 1500, var idsTransactions: MutableList<Int>? = null
                , var id:String? = null)

