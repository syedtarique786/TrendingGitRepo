package com.android.syed.gitrepo.network

import com.android.syed.gitrepo.model.RepoModel
import com.android.syed.gitrepo.utils.Result
import javax.inject.Singleton

@Singleton
interface MyRepository {
    suspend fun fetchTrendingRepos(): Result<List<RepoModel>>

    fun sortByStars(list: List<RepoModel>): List<RepoModel>

    fun sortByNames(list: List<RepoModel>): List<RepoModel>

}