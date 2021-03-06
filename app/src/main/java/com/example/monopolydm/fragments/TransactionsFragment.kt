package com.example.monopolydm.fragments


import android.os.Bundle
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.monopolydm.R
import com.example.monopolydm.activities.interfaces.IDataChanged
import com.example.monopolydm.activities.viewModel.ViewModelGameActivity
import com.example.monopolydm.activities.viewModel.ViewModelTransactionsFragment
import com.example.monopolydm.adapters.TransferAdapter
import com.example.monopolydm.model.Game
import com.example.monopolydm.model.Player
import com.example.monopolydm.model.Transaction

/**
 * A simple [Fragment] subclass.
 */
class TransactionsFragment : Fragment() {

    lateinit var model: ViewModelGameActivity
    lateinit var modelT: ViewModelTransactionsFragment
    lateinit var recyclerView: RecyclerView
    lateinit var listener:IDataChanged



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run { ViewModelProviders.of(this)[ViewModelGameActivity::class.java]
        } ?: throw Exception("Invalid Activity")

        modelT = ViewModelProviders.of(this)[ViewModelTransactionsFragment::class.java]




    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_transactions, container, false)

        listener = object : IDataChanged{
            override fun playerChanged(player: Player) {
                modelT.getTransactions(player)
                updateUI()
            }
            override fun gameChanged(game: Game) {}
        }

        model.iDataChanguedTransactionF = listener

        recyclerView = rootView.findViewById(R.id.recycler_player_transactions)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        if(model.vmPlayer?.transactions != null){
            modelT.vmTransactions.clear()
            modelT.vmTransactions.addAll(model.vmPlayer!!.transactions!!)
        }

        recyclerView.adapter = TransferAdapter(modelT.vmTransactions)



        return rootView
    }

    fun updateUI(){
        recyclerView.adapter!!.notifyDataSetChanged()
    }


}
