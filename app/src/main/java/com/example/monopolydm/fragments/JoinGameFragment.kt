package com.example.monopolydm.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.example.monopolydm.R
import com.example.monopolydm.activities.GameActivity
import com.example.monopolydm.activities.MenuActivity
import com.example.monopolydm.activities.functions.getPlayer
import com.example.monopolydm.model.Game
import com.example.monopolydm.model.Player
import com.example.monopolydm.network.Callback
import com.example.monopolydm.network.FireStoreService
import com.example.monopolydm.network.PLAYERS_COLLECTION_NAME
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class JoinGameFragment : Fragment(),View.OnClickListener {

    lateinit var txtGame:EditText
    lateinit var txtHoster:EditText
    lateinit var btnJoin:Button
    lateinit var progress:ProgressBar
    lateinit var userName:String
    lateinit var firestoreService: FireStoreService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_join_game, container, false)

        firestoreService = FireStoreService(FirebaseFirestore.getInstance())

        val activity = activity as MenuActivity
        userName = activity.nameUser

        txtGame = view.findViewById(R.id.edit_text_join_game)
        txtHoster = view.findViewById(R.id.edit_text_hoster_game)

        btnJoin = view.findViewById(R.id.button_join_game_frag)
        progress = view.findViewById(R.id.progress_join_game)

        btnJoin.setOnClickListener(this)


        return view
    }


    override fun onClick(v: View?) {
        if(v?.id == btnJoin.id) {

            loadMode()
            firestoreService.consultGamePlayer( txtGame.text.toString(), txtHoster.text.toString(),"hosterName", object :
                Callback<Game> {
                override fun onFailed(exception: Exception) {
                    normalMode()
                    Toast.makeText(context, R.string.game_name_error, Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: Game?) {
                    if(result != null){
                        val game = result
                        getPlayer(userName, object : Callback<Player>{
                            override fun onSuccess(result: Player?) {
                                if(result!=null){
                                    if(result!!.idGame != game.id && game.isEneable){
                                        result.idGame = game.id
                                        result.money = game.initMoney
                                        result.transactions = null

                                       if(game.IdsPlayers!!.contains(result.name)){
                                           game.IdsPlayers!!.remove(result.name)
                                       }
                                        firestoreService.createAndUpdatePlayer(result,
                                            PLAYERS_COLLECTION_NAME,object: Callback<Void>{
                                                override fun onSuccess(result: Void?) {
                                                    game.IdsPlayers!!.add(userName)
                                                    firestoreService.updateGame(game,object : Callback<Void>{
                                                        override fun onSuccess(result: Void?) {
                                                            startMenuActivity()
                                                        }

                                                        override fun onFailed(exception: Exception) {

                                                        }
                                                    })
                                                }

                                                override fun onFailed(exception: Exception) {
                                                    Toast.makeText(context, R.string.join_error_exist, Toast.LENGTH_LONG).show()
                                                }
                                            })
                                    } else{
                                        Toast.makeText(context, R.string.join_error_exist, Toast.LENGTH_LONG).show()
                                        normalMode()
                                    }
                                }
                            }

                            override fun onFailed(exception: Exception) {

                            }
                        })
                        //inscribir en el juego al jugadror  update al game, update al player y cambio de activity
                    }
                    else{
                        Toast.makeText(context, R.string.join_error, Toast.LENGTH_LONG).show()
                        normalMode()
                    }
                }

            } )
        }


    }

    fun loadMode(){
        progress.visibility = View.VISIBLE
        btnJoin.isEnabled = false
    }
    fun normalMode(){
        progress.visibility = View.INVISIBLE
        btnJoin.isEnabled = true
    }

    fun startMenuActivity() {
        val intent = Intent(context, GameActivity::class.java)
        intent.putExtra("player_name" ,userName)
        startActivity(intent)
        activity?.finish()
    }
}



