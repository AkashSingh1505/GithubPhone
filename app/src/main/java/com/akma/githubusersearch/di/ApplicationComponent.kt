package com.akma.githubusersearch.di

import androidx.lifecycle.ViewModel
import com.akma.githubusersearch.app.GitHubUserSearchApp
import com.akma.githubusersearch.ui.main.MainActivity
import com.akma.githubusersearch.ui.profile.ProfileFragment
import com.akma.githubusersearch.ui.search.SearchFragment
import dagger.BindsInstance

import dagger.Component
import javax.inject.Singleton




@Singleton
@Component(modules = [ViewModelModule::class,NetworkModule::class])
interface ApplicationComponent {

    fun injectInMainActivity(activity: MainActivity)
    fun injectInSearchFragment(searchFragment: SearchFragment)
    fun injectInProfileFragment(profileFragment: ProfileFragment)
    fun getMap():Map<Class<*>,ViewModel>

    @Component.Factory
    interface Factory{
       fun create(@BindsInstance app:GitHubUserSearchApp):ApplicationComponent
    }
}

