/*
 * Copyright (c) 2019. This class is only created by Syed & only means to used by Syed for Development & Testing Purpose. This class can be also used by other with proper permission. Any unauthorised used is strictly prohibited.
 * $used.year
 */

package com.android.syed.gitrepo.di.module

import androidx.lifecycle.ViewModelProvider
import com.android.syed.gitrepo.di.provider.ActivityViewModelProvider
import com.android.syed.gitrepo.model.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [ActivityViewModelProvider::class])
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(mFactory: ViewModelFactory): ViewModelProvider.Factory
}