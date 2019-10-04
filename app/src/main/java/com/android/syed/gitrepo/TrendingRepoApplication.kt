/*
 * Copyright (c) 2019. This class is only created by Syed & only means to used by Syed for Development & Testing Purpose. This class can be also used by other with proper permission. Any unauthorised used is strictly prohibited.
 * $used.year
 */

package com.android.syed.gitrepo

import com.android.syed.gitrepo.di.DaggerMyApplicationComponent
import com.android.syed.gitrepo.di.MyApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class TrendingRepoApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        mAppComponent = DaggerMyApplicationComponent.builder()
            .application(this)
            .build()
        return mAppComponent
    }

    fun getAppComponent(): MyApplicationComponent {
        return mAppComponent
    }

    companion object {
        lateinit var mAppComponent: MyApplicationComponent
    }
}