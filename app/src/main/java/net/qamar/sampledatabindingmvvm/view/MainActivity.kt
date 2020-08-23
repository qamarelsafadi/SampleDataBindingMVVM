package net.qamar.sampledatabindingmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinrv.Adapters.RecyclerViewAdapterKT
import kotlinx.android.synthetic.main.activity_main.*
import net.qamar.sampledatabindingmvvm.R
import net.qamar.sampledatabindingmvvm.databinding.ActivityMainBinding
import net.qamar.sampledatabindingmvvm.viewmodel.SampleViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SampleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(SampleViewModel::class.java)
        val binding: ActivityMainBinding = setContentView(
            this,
            R.layout.activity_main
        )
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setupObservers()

    }

    private fun setupObservers(){

        viewModel.albumList.observe(this, Observer { data ->

            val adapterKT = RecyclerViewAdapterKT(data,this)
            recyclerView.adapter = adapterKT
        })
    }
}
