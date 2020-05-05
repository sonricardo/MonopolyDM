package com.example.monopolydm.activities.interfaces

import com.example.monopolydm.model.Game
import com.example.monopolydm.model.Player

interface IDataChanged {
    fun playerChanged(player:Player)
    fun gameChanged(game:Game)


}

interface IPlayerInGameChanged{
    fun playersListChanged(playersList:MutableList<Player>)
}