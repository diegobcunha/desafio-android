package com.picpay.desafio.android.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.extensions.livedata.whitOwner
import com.picpay.desafio.android.view.user.UserListAdapter
import com.picpay.desafio.android.view.user.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = UserListAdapter()
    private val viewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter
        viewModel.resourceLiveData.whitOwner(this)
            .onLoading { binding.userListProgressBar.visibility = View.VISIBLE }
            .onSuccess {
                it?.let {
                    adapter.users = it
                }
            }
            .onError {
                Toast.makeText(this, "Error on request", Toast.LENGTH_SHORT)
                    .show()
            }
            .onTerminate { binding.userListProgressBar.visibility = View.GONE }
            .observe()

    }
}
