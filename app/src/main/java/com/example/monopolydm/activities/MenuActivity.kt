package com.example.monopolydm.activities


import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.monopolydm.R
import com.example.monopolydm.activities.functions.createGameMode
import com.example.monopolydm.fragments.CreateNewGameFragment

import kotlinx.android.synthetic.main.fragment_create_new_game.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import android.content.Intent
import android.hardware.usb.UsbManager
import androidx.fragment.app.Fragment
import com.example.monopolydm.activities.functions.getPlayer
import com.example.monopolydm.fragments.JoinGameFragment
import com.example.monopolydm.model.Player
import com.example.monopolydm.network.Callback
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btn_new:Button
    lateinit var btn_join:Button
    lateinit var btn_last:Button
    lateinit var btn_create:Button
    lateinit var fragment:Fragment
    lateinit var nameUser : String
    lateinit var context: Context

    lateinit var view_charging : ViewGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.monopolydm.R.layout.activity_menu)
        context = this


        nameUser = intent.getStringExtra("lo")

        btn_new = button_new
        btn_join = button_join
        btn_last = button_last_game


        view_charging = layout_loading

        btn_new.setOnClickListener(this)
        btn_join.setOnClickListener(this)

        btn_last.setOnClickListener(this)

        Toast.makeText(this,nameUser,Toast.LENGTH_LONG).show()
    }



    override fun onClick(v: View?) {
        if(v != null){

            when(v.id){
                btn_new.id -> {


                    createGameMode(btn_new,btn_join,btn_last)
                    fragment = CreateNewGameFragment()
                     supportFragmentManager.
                            beginTransaction().
                            replace(com.example.monopolydm.R.id.frame_content_fragment, fragment).
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).

                            commit()
                }


                btn_join.id -> {
                    createGameMode(btn_new,btn_join,btn_last)
                    fragment = JoinGameFragment()
                    supportFragmentManager.
                        beginTransaction().
                        replace(com.example.monopolydm.R.id.frame_content_fragment, fragment).
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).

                        commit()
                }


                btn_last.id -> {

                    getPlayer(nameUser,object : Callback<Player>{
                    override fun onSuccess(result: Player?) {
                        val currentPlayer = result
                        if(currentPlayer!!.idGame != null){
                            startMenuActivity()
                        }
                        else{
                            Toast.makeText(context,R.string.last_error,Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailed(exception: Exception) {
                         Toast.makeText(context,R.string.last_error,Toast.LENGTH_LONG).show()
                    }
                })


                }

            }

        }
    }

    fun startMenuActivity() {
        val intent = Intent(this@MenuActivity, GameActivity::class.java)
        intent.putExtra("player_name" ,nameUser)
        startActivity(intent)

    }

}
