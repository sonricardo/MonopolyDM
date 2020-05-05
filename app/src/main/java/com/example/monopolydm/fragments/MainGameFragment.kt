package com.example.monopolydm.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monopolydm.R
import com.example.monopolydm.activities.functions.getPlayer
import com.example.monopolydm.activities.interfaces.IDataChanged
import com.example.monopolydm.activities.viewModel.ViewModelGameActivity
import com.example.monopolydm.adapters.TransferAdapter
import com.example.monopolydm.model.Game
import com.example.monopolydm.model.Player
import com.example.monopolydm.model.Transaction

/**
 * A simple [Fragment] subclass.
 */
class MainGameFragment : Fragment(),  View.OnClickListener {

    lateinit var model: ViewModelGameActivity
    lateinit var texMoney: TextView
    lateinit var texNamePlayer: TextView
    lateinit var recViewMainGame: RecyclerView
    lateinit var btnPlayerView: Button
    lateinit var btnBankView: Button
    lateinit var listenerDataChanged : IDataChanged
    var transction:MutableList<Transaction> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run { ViewModelProviders.of(this)[ViewModelGameActivity::class.java]
        } ?: throw Exception("Invalid Activity")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_main_game, container, false)

        btnBankView = rootView.findViewById(R.id.button_bank)
        btnPlayerView = rootView.findViewById(R.id.button_player)
        texMoney = rootView.findViewById(R.id.text_view_money)
        texNamePlayer = rootView.findViewById(R.id.text_view_player_name_main)
        btnBankView.setOnClickListener(this)
        btnPlayerView.setOnClickListener(this)

        recViewMainGame = rootView.findViewById(R.id.recyvler_last_transfer)
        recViewMainGame.layoutManager = LinearLayoutManager(activity)

        listenerDataChanged = object : IDataChanged{
            override fun playerChanged(player: Player) { updateUI(player) }//setHosterOptions()}
            override fun gameChanged(game: Game) { setHosterOptions() }
        }

         model.iDataChanguedMainF = listenerDataChanged

         if ( model.vmPlayer != null)  {
             updateUI(model.vmPlayer!!)
             if(model.vmGame.hosterName != null)
               setHosterOptions()
         }

        return rootView

    }

    private fun updateUI(player:Player) {

        texMoney.text = "$ ${player.money}"
        if(player.name == player.idGame)
            texNamePlayer.text = "Banco"
        else
            texNamePlayer.text = player.name
        updateLastTransaction(player)

    }

    private fun updateLastTransaction(player:Player) {
        if(player.transactions != null) {
            transction.clear()
            transction.add(player.transactions!!.last().copy())
            recViewMainGame.adapter = TransferAdapter(transction)
        }
    }

    private fun setHosterOptions(){
        if(model.vmPlayerUser?.name != model.vmGame.hosterName){
            btnBankView.visibility = View.GONE
            btnPlayerView.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
       if(v?.id == btnBankView.id)    {
           model.playerSelector = false
           model.setPointerToPlayerBank()
           model.sendPlayerChangesToFragments(model.vmPlayer!!)
       }
        if(v?.id == btnPlayerView.id) {
            model.playerSelector = true
            model.setPointerToPlayerUser()
            model.sendPlayerChangesToFragments(model.vmPlayer!!)
        }


    }


}
