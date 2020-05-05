package com.example.monopolydm.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.example.monopolydm.R
import com.example.monopolydm.activities.GameActivity
import com.example.monopolydm.activities.MenuActivity
import com.example.monopolydm.model.Game
import com.example.monopolydm.network.Callback
import com.example.monopolydm.network.FireStoreService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.fragment_create_new_game.*

/**
 * A simple [Fragment] subclass.
 */
class CreateNewGameFragment : Fragment(),View.OnClickListener {


    lateinit var btn_crear:Button
    lateinit var prog_bar :ProgressBar
    lateinit var edt_game_name:EditText
    lateinit var edt_money:EditText
    lateinit var firestoreService: FireStoreService
    lateinit var userName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firestoreService = FireStoreService(FirebaseFirestore.getInstance())

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_create_new_game, container, false)

        val activity = activity as MenuActivity
        userName = activity.nameUser
        edt_game_name = view.findViewById(R.id.edit_text_new_game)
        edt_money = view.findViewById(R.id.edit_text_init_money)

        btn_crear = view.findViewById(R.id.button_create_game)
        prog_bar = view.findViewById(R.id.progress_create_game)

        btn_crear.setOnClickListener(this)


        return view
    }


    override fun onClick(v: View?) {
        if(v?.id == btn_crear.id) {

             loadMode()
             firestoreService.consultGamePlayer(edt_game_name.text.toString(),userName,"hosterName", object : Callback<Game> {
                 override fun onFailed(exception: Exception) {
                     normalMode()
                     Toast.makeText(context, R.string.game_name_error, Toast.LENGTH_LONG).show()
                 }

                 override fun onSuccess(result: Game?) {
                     if( result != null) {
                         Toast.makeText(context, R.string.game_name_error, Toast.LENGTH_LONG).show()
                         normalMode()

                     }

                    else{
                         var newGame = createGame(
                             edt_game_name.text.toString(),
                             userName,
                             edt_money.text.toString().toInt()
                         )
                         firestoreService.createGame(newGame, object : Callback<String> {
                             override fun onSuccess(result: String?) {
                                 newGame.id = result
                                 newGame.IdsPlayers = arrayListOf(userName)

                                 firestoreService.updateGame(newGame, object : Callback<Void> {
                                     override fun onSuccess(result: Void?) {

                                         firestoreService.createBankAndUpdatePlayer(userName,newGame.id!!,newGame.initMoney ,object : Callback<Boolean>{
                                             override fun onSuccess(result: Boolean?) {
                                                 Toast.makeText(context, "ahora si vamonooos", Toast.LENGTH_LONG).show()
                                                 startMenuActivity()
                                             }

                                             override fun onFailed(exception: Exception) {
                                                 Toast.makeText(context, R.string.game_name_error, Toast.LENGTH_LONG).show()
                                                 normalMode()
                                             }
                                         })
                                         normalMode()
                                     }

                                     override fun onFailed(exception: Exception) {
                                         normalMode()
                                     }
                                 })
                             }

                             override fun onFailed(exception: Exception) {
                                 Toast.makeText(
                                     context,
                                     R.string.game_name_error,
                                     Toast.LENGTH_LONG
                                 )
                                     .show()
                                 normalMode()
                             }

                         })
                     }
                 }
             } )
        }


    }

    fun createGame(gameName:String, nameUser: String, initMoney:Int): Game {
        val newGame = Game(nameGame = gameName, initMoney = initMoney, hosterName = nameUser)
        newGame.IdsPlayers = arrayListOf(nameUser)
        return newGame

    }

    fun loadMode(){
        prog_bar.visibility = View.VISIBLE
        btn_crear.isEnabled = false
    }
    fun normalMode(){
        prog_bar.visibility = View.INVISIBLE
        btn_crear.isEnabled = true
    }

    fun startMenuActivity() {
        val intent = Intent(context, GameActivity::class.java)
        intent.putExtra("player_name" ,userName)

        startActivity(intent)
        activity?.finish()
    }
}
