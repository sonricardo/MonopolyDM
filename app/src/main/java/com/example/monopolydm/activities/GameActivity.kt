package com.example.monopolydm.activities


import android.app.Fragment
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.monopolydm.R
import com.example.monopolydm.activities.functions.LoginViewModel
import com.example.monopolydm.activities.interfaces.IDataChanged
import com.example.monopolydm.activities.viewModel.ViewModelGameActivity
import com.example.monopolydm.adapters.PlayersMenuAdapter
import com.example.monopolydm.adapters.TransferAdapter
import com.example.monopolydm.fragments.ActionTransferFragment
import com.example.monopolydm.model.Game
import com.example.monopolydm.model.Player
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    lateinit var model:ViewModelGameActivity

    lateinit var playerName:String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        configNav()

        playerName = intent.getStringExtra("player_name")!!


        model = ViewModelProviders.of(this)[ViewModelGameActivity::class.java]
        model.playerName = playerName





        model.getPlayer(playerName).observe(this, Observer<Player>{ player->

            TransferAdapter.idGame = model.gameId
            PlayersMenuAdapter.idGame = model.gameId

            if(model.playerSelector) {

                model.updatePlayerUser(player)
                model.sendPlayerChangesToFragments(model.vmPlayer!!)
            }

            model.getGame(model.gameId).observe(this, Observer<Game>{ game->

                model.sendGameChangesToFragments(game)
            })

            model.getPlayerBank(model.playerBankName).observe(this, Observer<Player>{ playerBank->
                    model.updatePlayerBank(playerBank)
                    model.sendPlayerChangesToFragments(model.vmPlayer!!)

            })

            model.getAllPlayers().observe(this, Observer { allPlayerList ->
                    model.sendPlayerListToFragments(allPlayerList)
            })

         })




    }

    fun configNav(){
        NavigationUI.setupWithNavController(nav_bar, Navigation.findNavController(this,R.id.fragment_container))
    }


}


