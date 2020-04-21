package com.example.monopolydm.model

import java.util.*
import java.util.concurrent.atomic.AtomicInteger

data class Transaction(val transmitter:Player, val receiver:Player, val date: Date, val idTransaction: AtomicInteger,
                       val ammount:Int)


