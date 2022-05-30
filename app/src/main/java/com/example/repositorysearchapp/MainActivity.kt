package com.example.repositorysearchapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.repositorysearchapp.databinding.ActivityMainBinding
import com.example.repositorysearchapp.main.GitHubAdapter
import com.example.repositorysearchapp.main.MainViewModel
import com.example.repositorysearchapp.network.dto.Item

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: MainViewModel by viewModels()
    private val gitHubAdapter = GitHubAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setViewModel()
        binding.rvGithub.adapter = gitHubAdapter
        observeItemList()
    }

    private fun setViewModel() {
        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun observeItemList() {
        viewModel.itemList.observe(this) { itemList ->
            val copyList = itemList.toMutableList()
            copyList.add(Item())
            gitHubAdapter.submitList(copyList)
        }
    }
}
