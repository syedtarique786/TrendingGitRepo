/*
 * Copyright (c) 2019. This class is only created by Syed & only means to used by Syed for Development & Testing Purpose. This class can be also used by other with proper permission. Any unauthorised used is strictly prohibited.
 * $used.year
 */

package com.android.syed.gitrepo.di

import android.app.Application
import android.content.Context
import com.android.syed.gitrepo.TrendingRepoApplication
import com.android.syed.gitrepo.di.module.ActivityModule
import com.android.syed.gitrepo.di.module.ApplicationModule
import com.android.syed.gitrepo.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        NetworkModule::class
    ]
)
interface MyApplicationComponent : AndroidInjector<TrendingRepoApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): MyApplicationComponent
    }

    fun context(): Context

}