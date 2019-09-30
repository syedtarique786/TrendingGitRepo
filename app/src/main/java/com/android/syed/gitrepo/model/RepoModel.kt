package com.android.syed.gitrepo.model

data class RepoModel(
    val author: String,
    val name: String,
    val avatar: String,
    val description: String,
    val stars: Int,
    val forks: Int,
    var language: String? = null,
    var languageColor: String? = null
)