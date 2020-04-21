package com.example.monopolydm.model

import java.util.concurrent.atomic.AtomicInteger

data class Game(var isEneable:Boolean = true, var IdsPlayers:List<Int>?, val idGame:AtomicInteger,
                val hosterPlayer:Player, val initMoney:Int, var idsTransactions: List<Int>?)

