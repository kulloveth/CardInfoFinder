package com.kulloveth.cardinfofinder.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kulloveth.cardinfofinder.R

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private var viewModel: MainViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize mainViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel?.fetchCardDetails(5399235014069108)?.observe(this, Observer {
            Log.d(TAG, "card details are $it")
        })
    }
}
