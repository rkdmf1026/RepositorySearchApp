package com.example.repositorysearchapp.network

import android.util.Log
import com.example.repositorysearchapp.network.dto.Item

class GitHubRepository {
    private var name = ""
    private var pageNum = 1
    private val itemList = mutableListOf<Item>()
    suspend fun getGitHubRepository(q: String): List<Item> {
        if (name == q) {
            pageNum++
        } else {
            name = q
            pageNum = 1
            itemList.clear()
        }
        itemList.addAll(
            RetrofitBuilder.gitService.getRepositoryList(
                q,
                PER_PAGE,
                pageNum
            ).items
        )
        return itemList
    }

    companion object {
        const val PER_PAGE = 10
    }
}
