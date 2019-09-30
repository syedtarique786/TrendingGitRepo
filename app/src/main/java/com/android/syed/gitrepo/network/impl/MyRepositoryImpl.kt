/*
 * Copyright (c) 2019. This class is only created by Syed & only means to used by Syed for Development & Testing Purpose. This class can be also used by other with proper permission. Any unauthorised used is strictly prohibited.
 * $used.year
 */

package com.android.syed.gitrepo.network.impl

import com.android.syed.gitrepo.model.RepoModel
import com.android.syed.gitrepo.network.APIService
import com.android.syed.gitrepo.network.MyRepository
import com.android.syed.gitrepo.utils.Result
import com.android.syed.gitrepo.utils.safeApiCall
import java.io.IOException

class MyRepositoryImpl(private val mApiService: APIService) : MyRepository {

    override suspend fun fetchTrendingRepos() = safeApiCall(
        call = { fetchRepositories() },
        errorMessage = "Error in fetching Trending Repositories from Remote"
    )

    private suspend fun fetchRepositories(): Result<List<RepoModel>> {
        val response = mApiService.fetchTrendingRepos()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return Result.Error(IOException("Error in Fetching Trending Repositories ${response.code()} ${response.message()}"))
    }

    override fun sortByNames(list: List<RepoModel>): List<RepoModel> =
        list.sortedWith(compareBy { it.name })

    override fun sortByStars(list: List<RepoModel>): List<RepoModel> =
        list.sortedWith(compareBy { it.stars })


}