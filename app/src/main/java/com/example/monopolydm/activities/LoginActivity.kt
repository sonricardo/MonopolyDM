package com.example.monopolydm.activities

//import android.support.v7.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.monopolydm.R
import com.example.monopolydm.activities.functions.LoginViewModel
import com.example.monopolydm.activities.functions.createPlayer
import com.example.monopolydm.activities.functions.signUpPlayer
import com.example.monopolydm.activities.functions.singInPlayer
import com.example.monopolydm.activities.interfaces.ISingIn
import com.example.monopolydm.activities.interfaces.ISingUp
import com.example.monopolydm.model.Player
import com.example.monopolydm.network.Callback
import com.example.monopolydm.network.FireStoreService
import com.example.monopolydm.network.PLAYERS_COLLECTION_NAME
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_login.*



class LoginActivity : AppCompatActivity(),OnClickListener {

    val TAG = "LoginActivity"
    lateinit var btn_sin:Button
    lateinit var btn_sup:Button
    lateinit var edt_user:EditText
    lateinit var edt_pass:EditText
    lateinit var prg_bar:ProgressBar
    lateinit var db:FirebaseFirestore
    lateinit var context:Context
    lateinit var model:LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this
        db = FirebaseFirestore.getInstance()


        prg_bar = progress_login
        btn_sin = button_sing_in
        btn_sup = button_sing_up
        edt_user = edit_text_user
        edt_pass = edit_text_password

        btn_sin.setOnClickListener(this)
        btn_sup.setOnClickListener(this)

        model = ViewModelProviders.of(this)[LoginViewModel::class.java]
        /*
        model.getPlayersAndObserve().observe(this, Observer<List<Player>>{ players ->

            var string = ""

            for(player in players){

                string += player.name
            }
            text_test.text = string
        })  */

}



override fun onClick(v: View?) {
if(v != null){

   when(v.id){
       btn_sin.id -> {

           if(edt_user.text.toString() == "" ){
               Toast.makeText(context,R.string.user_void,Toast.LENGTH_LONG).show()
           }
           else{

               desactivateView()
               singInPlayer(createPlayer(edt_user.text.toString(),edt_pass.text.toString()),db, object : ISingIn{
                   override fun onSingInSucces() {
                       activateView()
                       startMenuActivity()



                   }


                   override fun onSingInFailed() {
                       activateView()
                       Toast.makeText(context,R.string.sing_in_error,Toast.LENGTH_LONG).show()
                   }
               })
           }


       }

       btn_sup.id -> {
           if(edt_user.text.toString() == "" ){
               Toast.makeText(context,R.string.user_void,Toast.LENGTH_LONG).show()
           }
           else{
               desactivateView()
               signUpPlayer(createPlayer(edt_user.text.toString(),edt_pass.text.toString()),db, object : ISingUp{
                   override fun onSingUpSucces() {
                       activateView()
                       startMenuActivity()

                   }

                   override fun onSingUpFailed() {
                       activateView()
                       Toast.makeText(context,R.string.sing_up_error,Toast.LENGTH_LONG).show()
                   }

               })
           }
       }

   }

}
}

fun startMenuActivity() {
 val intent = Intent(this@LoginActivity, MenuActivity::class.java)
 intent.putExtra("lo" ,edt_user.text.toString())
 startActivity(intent)

}

fun activateView(){
prg_bar.visibility = View.INVISIBLE
btn_sin.isEnabled = true
btn_sup.isEnabled = true
edt_user.isEnabled = true
edt_pass.isEnabled = true
}

fun desactivateView(){
prg_bar.visibility = View.VISIBLE
btn_sin.isEnabled = false
btn_sup.isEnabled = false
edt_user.isEnabled = false
edt_pass.isEnabled = false
}

}


