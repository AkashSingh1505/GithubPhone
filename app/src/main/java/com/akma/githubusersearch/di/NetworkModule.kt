package com.akma.githubusersearch.di

import com.akma.githubusersearch.retrofit.GitHubApiService
import com.akma.githubusersearch.retrofit.GitHubInterceptor
import com.akma.githubusersearch.utils.Constants

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit():Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    }


    @Singleton
    @Provides
    fun providesOkhttpClient(intercepter: GitHubInterceptor):OkHttpClient {
        val apiClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .connectTimeout(30,TimeUnit.SECONDS)
            .addInterceptor(intercepter)

      return apiClient.build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofitBuilder: Retrofit.Builder,okHttpClient:OkHttpClient): GitHubApiService{
        return retrofitBuilder.client(okHttpClient).build().create(GitHubApiService::class.java)
    }

}