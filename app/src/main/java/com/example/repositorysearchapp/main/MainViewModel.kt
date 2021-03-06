package com.example.repositorysearchapp.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repositorysearchapp.network.dto.Item
import com.example.repositorysearchapp.network.repository.GitHubRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val gitHubRepository: GitHubRepository = GitHubRepository()
) : ViewModel() {

    private val _itemList = MutableLiveData<List<Item>>()
    val itemList: LiveData<List<Item>>
        get() = _itemList

    val inputText = MutableLiveData<String>()

    fun searchItem(type: Int) {
        viewModelScope.launch {
            try {
                _itemList.value = gitHubRepository.getGitHubRepository(inputText.value ?: "", type)
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}
