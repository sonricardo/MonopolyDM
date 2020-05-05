package com.example.monopolydm.adapters

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.monopolydm.R
import com.example.monopolydm.model.Transaction


class PlayersMenuAdapter(val dataSet:MutableList<String>, val onClickListener: OnItemClickListener) : RecyclerView.Adapter<PlayersMenuAdapter.MyViewHolder>()
{

     var pS: String? = null

    companion object{
        var idGame:String? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersMenuAdapter.MyViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_selct_player, parent, false)



        return MyViewHolder(v)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        if(idGame == dataSet.get(position))
            holder.txtPlayerName.text = "Banco"
        else
        holder.txtPlayerName.text = dataSet.get(position)


        if(pS != null) {
            if (dataSet.get(position) == pS) {
                holder.layout.setBackgroundColor(Color.GRAY)
            } else {
                holder.layout.setBackgroundColor(Color.WHITE)
            }
        }



        holder.itemView.setOnClickListener { view ->
            onClickListener.onItemClick(dataSet.get(position))

        }
    }


    class MyViewHolder(v: View): RecyclerView.ViewHolder(v){
        val txtPlayerName: TextView
        val layout:LinearLayout


        init {
            txtPlayerName = v.findViewById(R.id.text_player_grid_menu)
            layout = v.findViewById(R.id.layout_item_player_menu)

        }

    }

     fun setPs(name:String){
        pS = name
    }

    interface OnItemClickListener {
        fun onItemClick( name: String)
    }
}