/*
 * Copyright (c) 2019. This class is only created by Syed & only means to used by Syed for Development & Testing Purpose. This class can be also used by other with proper permission. Any unauthorised used is strictly prohibited.
 * $used.year
 */

package com.android.syed.gitrepo.ui.main

import ConnectionLiveData
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.android.syed.gitrepo.R
import com.android.syed.gitrepo.di.module.nonNullObserve
import com.android.syed.gitrepo.model.RepoListViewModel
import com.android.syed.gitrepo.model.RepoModel
import com.android.syed.gitrepo.model.UIModel
import com.android.syed.gitrepo.ui.adapter.RepoListAdapter
import com.android.syed.gitrepo.utils.ShimmerLayout
import com.android.syed.gitrepo.utils.showToast
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_app_toolbar.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private val viewModel: RepoListViewModel by lazy {
        ViewModelProviders.of(this, mViewModelFactory).get(RepoListViewModel::class.java)
    }

    private lateinit var mRepoAdapter: RepoListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initViews()
    }

    private fun initViews() {
        mRepoAdapter = RepoListAdapter(this@MainActivity)

        val dividerItemDecoration = DividerItemDecoration(this@MainActivity, RecyclerView.VERTICAL)
        rv_trending_repo.addItemDecoration(dividerItemDecoration)
        rv_trending_repo.adapter = mRepoAdapter

        layout_swipe.setOnRefreshListener {
            viewModel.onSwipeRefreshed()
            layout_swipe.isRefreshing = false
        }

        btn_retry.setOnClickListener { viewModel.onRetryClicked() }
        imgv_menu.setOnClickListener { Log.d("MainActivity", "Menu clicked") }
    }


    override fun onStart() {
        super.onStart()
        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.uiState.observe(this, Observer { it ->
            val uiModel = it ?: return@Observer

            if (uiModel.showProgress) {
                startShimmerAnimation()
            } else {
                stopShimmerAnimation()
            }

            if (uiModel.showError != null && !uiModel.showError.consumed) {
                uiModel.showError.consume()?.let { showErrorLayout() }
            } else {
                hideErrorLayout()
            }

            if (uiModel.showSuccess != null && !uiModel.showSuccess.consumed) {
                uiModel.showSuccess.consume()?.let { setRepoItems(it) }
            }
        })

        viewModel.reFetchingState.nonNullObserve(this, { reFetchTrendingRepos() })

        observeNetwork()
    }

    private fun showErrorLayout() {
        loading_error_layout.visibility = View.VISIBLE
        hideRecyclerView()
    }

    private fun hideErrorLayout() {
        loading_error_layout.visibility = View.GONE
    }

    private fun setRepoItems(repoList: List<RepoModel>) {
        mRepoAdapter.setItems(repoList)
    }

    private fun reFetchTrendingRepos() {
        hideErrorLayout()
        viewModel.fetchTrendingRepositories()
    }

    private fun startShimmerAnimation() {
        hideErrorLayout()
        hideRecyclerView()
        loading_layout.visibility = View.VISIBLE
        (loading_layout as ShimmerLayout).startShimmerAnimation()
    }

    private fun stopShimmerAnimation() {
        loading_layout.visibility = View.GONE
        (loading_layout as ShimmerLayout).stopShimmerAnimation()
        showRecyclerView()
    }

    private fun hideRecyclerView() {
        rv_trending_repo.visibility = View.GONE
    }

    private fun showRecyclerView() {
        rv_trending_repo.visibility = View.VISIBLE
    }

    private fun observeNetwork() {
        val connectionLiveData = ConnectionLiveData(application)
        connectionLiveData.nonNullObserve(this, { available ->
            val uiModel: UIModel = viewModel.uiState!!.value!!
            if (available) {
                if (uiModel.showProgress) {                             // Progress
                    reFetchTrendingRepos()
                } else if (uiModel.showSuccess != null && !uiModel.showSuccess.consumed) {  //Success
                    showRecyclerView()
                }
            } else if (uiModel.showError != null && !uiModel.showError.consumed) {      //Error
                showToast(this@MainActivity, getString(R.string.msg_check_connectivity))
            }
        })
    }

}
