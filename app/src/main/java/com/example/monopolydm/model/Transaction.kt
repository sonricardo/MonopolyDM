package com.example.monopolydm.model

import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

data class Transaction(val transmitter:String? = null, val receiver:String? = null, val date: String? = null, val idTransaction: String? = null,
                       val ammount:Int? = null)


