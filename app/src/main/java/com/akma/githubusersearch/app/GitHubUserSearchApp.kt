package com.akma.githubusersearch.app

import android.app.Application
import com.akma.githubusersearch.di.ApplicationComponent
import com.akma.githubusersearch.di.DaggerApplicationComponent

class GitHubUserSearchApp: Application() {
    lateinit var appComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(this)
    }
}