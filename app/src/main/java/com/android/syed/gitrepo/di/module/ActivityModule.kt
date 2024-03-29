/*
 * Copyright (c) 2019. This class is only created by Syed & only means to used by Syed for Development & Testing Purpose. This class can be also used by other with proper permission. Any unauthorised used is strictly prohibited.
 * $used.year
 */

package com.android.syed.gitrepo.di.module

import com.android.syed.gitrepo.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Scope
@MustBeDocumented
annotation class ActivityScope

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindTrendingActivity(): MainActivity

}