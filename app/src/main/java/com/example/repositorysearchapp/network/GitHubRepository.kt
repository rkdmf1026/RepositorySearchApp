package com.example.repositorysearchapp.network

import com.example.repositorysearchapp.network.dto.Item

class GitHubRepository {
    suspend fun getGitHubRepository(q: String, perPage: Int, page: Int): List<Item> {
        return RetrofitBuilder.gitService.getRepositoryList(q, perPage, page).items
    }
}
