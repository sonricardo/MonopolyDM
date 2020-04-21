package com.example.monopolydm.fragments


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
class actionTransferFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return return inflater.inflate(R.layout.fragment_action_transfer, container, false)
    }
}
