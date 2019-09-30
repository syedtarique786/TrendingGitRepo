package com.android.syed.gitrepo.network

import com.android.syed.gitrepo.model.RepoModel
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("/repositories")
    suspend fun fetchTrendingRepos(): Response<List<RepoModel>>

}