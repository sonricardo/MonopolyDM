package com.example.monopolydm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.monopolydm.R
import com.example.monopolydm.model.Transaction
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TransferAdapter(val dataSet:MutableList<Transaction>) : RecyclerView.Adapter<TransferAdapter.MyViewHolder>(){

    companion object{
        var idGame:String? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val fixPosition = dataSet.size - 1 - position   // this val is for show first the last transactions

        if( dataSet.get(fixPosition).transmitter!! == idGame)
            holder.txtPlaSen.text = "Banco"
        else
            holder.txtPlaSen.text = dataSet.get(fixPosition).transmitter!!

        if( dataSet.get(fixPosition).receiver!! == idGame)
            holder.txtPlaRec.text = "Banco"
        else
            holder.txtPlaRec.text = dataSet.get(fixPosition).receiver!!

        holder.txtAmount.text = dataSet.get(fixPosition).ammount.toString()
        holder.txtDate.text = dataSet.get(fixPosition).date

    }


    class MyViewHolder(v: View): RecyclerView.ViewHolder(v){
        val txtPlaSen:TextView
        val txtPlaRec:TextView
        val txtAmount:TextView
        val txtDate: TextView

        init {
            txtPlaSen = v.findViewById(R.id.text_player_sender)
            txtPlaRec = v.findViewById(R.id.text_player_receiver)
            txtAmount = v.findViewById(R.id.text_ammount)
            txtDate = v.findViewById(R.id.text_date_transfer)
        }

    }

}

