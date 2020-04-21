package com.example.monopolydm.activities


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
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.fragment_create_new_game.*

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btn_new:Button
    lateinit var btn_join:Button
    lateinit var btn_last:Button
    lateinit var btn_create:Button
    lateinit var fragment:CreateNewGameFragment

    lateinit var view_charging : ViewGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val nameUser = intent.getStringExtra("lo")

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
                            replace(R.id.frame_content_fragment, fragment).
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                            commit()
                }
                btn_join.id -> {Toast.makeText(this,"join",Toast.LENGTH_LONG).show()}
                btn_last.id -> {Toast.makeText(this,"last",Toast.LENGTH_LONG).show()}

            }

        }
    }
}
