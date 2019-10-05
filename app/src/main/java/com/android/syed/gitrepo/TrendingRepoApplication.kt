/*
 * Copyright (c) 2019. This class is only created by Syed & only means to used by Syed for Development & Testing Purpose. This class can be also used by other with proper permission. Any unauthorised used is strictly prohibited.
 * $used.year
 */

package com.android.syed.gitrepo

import android.app.Activity
import androidx.fragment.app.Fragment
import com.android.syed.gitrepo.di.DaggerMyApplicationComponent
import com.android.syed.gitrepo.di.MyApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class TrendingRepoApplication : DaggerApplication() {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>


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

    fun activityInjector(): AndroidInjector<Activity> = activityInjector

    fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}