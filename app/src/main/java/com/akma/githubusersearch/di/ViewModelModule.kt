package com.akma.githubusersearch.di

import androidx.lifecycle.ViewModel
import com.akma.githubusersearch.ui.GitHubViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(GitHubViewModel::class)
    @IntoMap
    abstract fun bindGitHubViewModel(videoViewModel: GitHubViewModel): ViewModel
}