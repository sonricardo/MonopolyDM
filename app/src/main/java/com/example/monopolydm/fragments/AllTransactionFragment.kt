package com.example.monopolydm.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.monopolydm.R
import com.example.monopolydm.activities.interfaces.IDataChanged
import com.example.monopolydm.activities.viewModel.ViewModelAllTransactions
import com.example.monopolydm.activities.viewModel.ViewModelGameActivity
import com.example.monopolydm.activities.viewModel.ViewModelTransactionsFragment
import com.example.monopolydm.adapters.TransferAdapter
import com.example.monopolydm.model.Game
import com.example.monopolydm.model.Player
import java.lang.StringBuilder

/**
 * A simple [Fragment] subclass.
 */
class AllTransactionFragment : Fragment() {

    lateinit var model: ViewModelGameActivity
    lateinit var modelT: ViewModelAllTransactions
    lateinit var recyclerView: RecyclerView
    lateinit var listener: IDataChanged



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run { ViewModelProviders.of(this)[ViewModelGameActivity::class.java]
        } ?: throw Exception("Invalid Activity")

        modelT = ViewModelProviders.of(this)[ViewModelAllTransactions::class.java]




    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_all_transaction, container, false)

        listener = object : IDataChanged {
            override fun playerChanged(player: Player) {
                modelT.getTransactions(model.vmPlayerBank!!)
                updateUI()
            }
            override fun gameChanged(game: Game) {}
        }

        model.iDataChanguedAllTransactionF = listener

        recyclerView = rootView.findViewById(R.id.recycle_all_transactions)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        if(model.vmPlayerBank?.transactions != null){
            modelT.vmTransactions.clear()
            modelT.vmTransactions.addAll(model.vmPlayerBank!!.transactions!!)
        }

        recyclerView.adapter = TransferAdapter(modelT.vmTransactions)

        algo {

            print(it)
        }

        return rootView
    }

    fun algo(func:(Int)-> Unit ){ func(1) }

    fun updateUI(){
        recyclerView.adapter!!.notifyDataSetChanged()
    }


}
