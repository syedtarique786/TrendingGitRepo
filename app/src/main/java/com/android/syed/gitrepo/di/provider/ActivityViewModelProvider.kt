/*
 * Copyright (c) 2019. This class is only created by Syed & only means to used by Syed for Development & Testing Purpose. This class can be also used by other with proper permission. Any unauthorised used is strictly prohibited.
 * $used.year
 */

package com.android.syed.gitrepo.di.provider

import androidx.lifecycle.ViewModel
import com.android.syed.gitrepo.di.ViewModelKey
import com.android.syed.gitrepo.model.RepoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ActivityViewModelProvider {
    @Binds
    @IntoMap
    @ViewModelKey(RepoListViewModel::class)
    abstract fun bindConversionViewModel(mViewModel: RepoListViewModel): ViewModel
}