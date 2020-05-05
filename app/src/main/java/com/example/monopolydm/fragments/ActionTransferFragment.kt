package com.example.monopolydm.fragments


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.monopolydm.R
import com.example.monopolydm.activities.interfaces.IDataChanged
import com.example.monopolydm.activities.viewModel.ViewModelActionTransfer
import com.example.monopolydm.activities.viewModel.ViewModelGameActivity
import com.example.monopolydm.adapters.PlayersMenuAdapter
import com.example.monopolydm.model.Game
import com.example.monopolydm.model.Player
import com.example.monopolydm.network.Callback
import kotlinx.android.synthetic.main.fragment_create_new_game.*
import org.w3c.dom.Text
import java.util.Collections.addAll
import android.opengl.ETC1.getWidth
import android.opengl.ETC1.getHeight
import com.example.monopolydm.activities.interfaces.IPlayerInGameChanged


/**
 * A simple [Fragment] subclass.
 */
class ActionTransferFragment : Fragment(), View.OnClickListener {


    lateinit var model: ViewModelGameActivity
    lateinit var modelV: ViewModelActionTransfer
    var listPlayerNames: MutableList<String> = arrayListOf()
    lateinit var adapter: PlayersMenuAdapter
    lateinit var textScreen: TextView

    lateinit var btn_1: Button
    lateinit var btn_2: Button
    lateinit var btn_3: Button
    lateinit var btn_4: Button
    lateinit var btn_5: Button
    lateinit var btn_6: Button
    lateinit var btn_7: Button
    lateinit var btn_8: Button
    lateinit var btn_9: Button
    lateinit var btn_0: Button
    lateinit var btn_x: Button
    lateinit var btn_t: Button
    lateinit var recycler_players: RecyclerView
    lateinit var listenerData: IPlayerInGameChanged

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this)[ViewModelGameActivity::class.java]
        } ?: throw Exception("Invalid Activity")
        modelV = ViewModelProviders.of(this)[ViewModelActionTransfer::class.java]

        modelV.activityModel = model

        listenerData = object : IPlayerInGameChanged {
            override fun playersListChanged(playersList: MutableList<Player>) {
                listPlayerNames.clear()
                modelV.setReadyListNames(listPlayerNames,model.vmPlayer?.name!!,model.vmPlayerBank?.name!!)
            }
        }


        modelV.setReadyListNames(listPlayerNames,model.vmPlayer?.name!!,model.vmPlayerBank?.name!!)
        model.iPlayersListChangedActionTransfer = listenerData



    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_action_transfer, container, false)

        btn_1 = rootView.findViewById(R.id.btn_1)
        btn_2 = rootView.findViewById(R.id.btn_2)
        btn_3 = rootView.findViewById(R.id.btn_3)
        btn_4 = rootView.findViewById(R.id.btn_4)
        btn_5 = rootView.findViewById(R.id.btn_5)
        btn_6 = rootView.findViewById(R.id.btn_6)
        btn_7 = rootView.findViewById(R.id.btn_7)
        btn_8 = rootView.findViewById(R.id.btn_8)
        btn_9 = rootView.findViewById(R.id.btn_9)
        btn_0 = rootView.findViewById(R.id.btn_0)
        btn_x = rootView.findViewById(R.id.btn_erase)
        btn_t = rootView.findViewById(R.id.btn_transfer)
        textScreen = rootView.findViewById(R.id.text_screem_transfer)




        recycler_players = rootView.findViewById(R.id.recycler_players)
        recycler_players.layoutManager = GridLayoutManager(activity, 3)
        adapter = PlayersMenuAdapter(listPlayerNames, object : PlayersMenuAdapter.OnItemClickListener {
                override fun onItemClick(name: String) {
                    modelV.playerSelected = name
                    adapter.setPs(modelV.playerSelected!!)
                    recycler_players.adapter?.notifyDataSetChanged()
                }
            })
        recycler_players.adapter = adapter
        updateScreen("$ 0")



        btn_1.setOnClickListener(this)
        btn_2.setOnClickListener(this)
        btn_3.setOnClickListener(this)
        btn_4.setOnClickListener(this)
        btn_5.setOnClickListener(this)
        btn_6.setOnClickListener(this)
        btn_7.setOnClickListener(this)
        btn_8.setOnClickListener(this)
        btn_9.setOnClickListener(this)
        btn_0.setOnClickListener(this)
        btn_x.setOnClickListener(this)
        btn_t.setOnClickListener(this)





        return rootView
    }

    fun updateScreen(text: String) {
        textScreen.text = text
    }


    override fun onClick(v: View?) {

        when (v?.id) {
            btn_0.id -> {
                updateScreen(modelV.pushCalculator(0))
            }
            btn_1.id -> {
                updateScreen(modelV.pushCalculator(1))
            }
            btn_2.id -> {
                updateScreen(modelV.pushCalculator(2))
            }
            btn_3.id -> {
                updateScreen(modelV.pushCalculator(3))
            }
            btn_4.id -> {
                updateScreen(modelV.pushCalculator(4))
            }
            btn_5.id -> {
                updateScreen(modelV.pushCalculator(5))
            }
            btn_6.id -> {
                updateScreen(modelV.pushCalculator(6))
            }
            btn_7.id -> {
                updateScreen(modelV.pushCalculator(7))
            }
            btn_8.id -> {
                updateScreen(modelV.pushCalculator(8))
            }
            btn_9.id -> {
                updateScreen(modelV.pushCalculator(9))
            }
            btn_x.id -> {
                updateScreen(modelV.pushCalculator(-1))
            }
            btn_t.id -> {
                modelV.transferAction(object :Callback<Boolean>{
                    override fun onSuccess(result: Boolean?) {

                        if(result!!){
                                Toast.makeText(context,R.string.transfer_succes,Toast.LENGTH_SHORT).show()
                                updateScreen(modelV.pushCalculator(0))
                        }
                        else{
                            Toast.makeText(context,R.string.tranfer_error,Toast.LENGTH_SHORT).show()
                        }
                     }

                    override fun onFailed(exception: Exception) {
                        Log.e("TRANSACTION",exception.message)
                    }
                })
            }
        }
    }
}
