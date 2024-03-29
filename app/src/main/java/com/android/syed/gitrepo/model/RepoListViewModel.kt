/*
 * Copyright (c) 2019. This class is only created by Syed & only means to used by Syed for Development & Testing Purpose. This class can be also used by other with proper permission. Any unauthorised used is strictly prohibited.
 * $used.year
 */

package com.android.syed.gitrepo.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.syed.gitrepo.repository.MyRepository
import com.android.syed.gitrepo.utils.Event
import com.android.syed.gitrepo.utils.Result
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RepoListViewModel @Inject constructor(private val mRepository: MyRepository) : ViewModel() {
    private val foregroundContext: CoroutineContext by lazy { Dispatchers.Main }
    private val computationContext: CoroutineContext by lazy { Dispatchers.Default }

    private var fetchJob: Job? = null

    private val _uiState = MutableLiveData<UIModel>()
    val uiState: LiveData<UIModel>
        get() = _uiState

    private val _reFetchingState = MutableLiveData<Unit>()
    val reFetchingState: LiveData<Unit>
        get() = _reFetchingState

    init {
        fetchTrendingRepositories()
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        fetchJob!!.cancel()
    }

    fun fetchTrendingRepositories() {
        if (fetchJob?.isActive == true) {
            return
        }
        fetchJob = fetchRepos()
    }


    fun onSwipeRefreshed() {
        updateReFetch()
    }

    fun onRetryClicked() {
        updateReFetch()
    }

    private fun updateReFetch() {
        _reFetchingState.postValue(null)
    }

    private fun fetchRepos(): Job {
        return viewModelScope.launch(computationContext) {
            withContext(foregroundContext) { showLoading() }
            delay(2_000)
            val result = mRepository.fetchTrendingRepos()
            withContext(foregroundContext) {
                if (result is Result.Success) {
                    emitUiState(
                        showSuccess = Event(result.data)
                    )
                } else {
                    emitUiState(
                        showError = Event(true)
                    )
                }
            }
        }
    }

    private fun showLoading() {
        emitUiState(showProgress = true)
    }

    private fun emitUiState(
        showProgress: Boolean = false,
        showError: Event<Boolean>? = null,
        showSuccess: Event<List<RepoModel>>? = null
    ) {
        val uiModel = UIModel(showProgress, showError, showSuccess)
        _uiState.value = uiModel
    }

}

data class UIModel(
    val showProgress: Boolean,
    val showError: Event<Boolean>?,
    val showSuccess: Event<List<RepoModel>>?
)