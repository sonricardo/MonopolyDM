package com.example.monopolydm.fragments


import android.content.Context
import android.os.Bundle
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.example.monopolydm.R
import kotlinx.android.synthetic.main.fragment_create_new_game.*

/**
 * A simple [Fragment] subclass.
 */
class CreateNewGameFragment : Fragment(),View.OnClickListener {


    lateinit var btn_crear:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_create_new_game, container, false)

        btn_crear = view.findViewById(R.id.button_create_game)
        btn_crear.setOnClickListener(this)

        return view
    }


    override fun onClick(v: View?) {
        if(v?.id == btn_crear.id) {
            Toast.makeText(context, "sss", Toast.LENGTH_LONG).show()
            //consultar si el juego y jugador existen juego es documento, jugador campo
            //si existe crear juego. update al juagdor host de que pertenece al juego
        }
    }


}
