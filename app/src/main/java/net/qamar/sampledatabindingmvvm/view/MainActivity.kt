package net.qamar.sampledatabindingmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import com.example.kotlinrv.Adapters.RecyclerViewAdapterKT
import kotlinx.android.synthetic.main.activity_main.*
import net.qamar.sampledatabindingmvvm.R
import net.qamar.sampledatabindingmvvm.databinding.ActivityMainBinding
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.apiNetworking.Status.*
import net.qamar.sampledatabindingmvvm.viewmodel.SampleViewModel
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private val viewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = setContentView(
            this,
        R.layout.activity_main
        )
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setupObservers()

    }

     private fun setupObservers() {

        viewModel.albumList.observe(this, Observer { data ->

            when (data.status) {
                SUCCESS -> {
                    successState(data.data!!)
                }
                ERROR -> {

                    Toast.makeText(this, data.msg, Toast.LENGTH_LONG)
                        .show()
                }
                LOADING -> progressBar.visibility = View.VISIBLE

            }

        })
    }


    private fun successState(data : List<RetroPhoto>){
        progressBar.visibility = View.GONE

        data.let {
            val adapterKT =
                RecyclerViewAdapterKT(it as ArrayList<RetroPhoto>, this)
            recyclerView.adapter = adapterKT
        }
    }



}
