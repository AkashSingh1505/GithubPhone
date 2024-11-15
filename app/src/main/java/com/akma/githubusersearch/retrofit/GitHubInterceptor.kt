package com.akma.githubusersearch.retrofit

import android.util.Log
import com.akma.githubusersearch.R
import com.akma.githubusersearch.app.GitHubUserSearchApp
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.io.IOException
import java.util.Date
import javax.inject.Inject


private const val TAG = "HeaderInterceptorTag"

class GitHubInterceptor @Inject constructor(private val application: GitHubUserSearchApp) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request().newBuilder()
        val request = originalRequest.build()

        try {
            val response: Response = chain.proceed(request)
            if (response.isSuccessful) {
                handleSuccess(response)
            } else {
                handleError(IOException(), request.url().toString())
                throw IOException(response.message())
            }

            return response
        } catch (e: Exception) {
            handleError(e, "server problem ${request.url()}")
            val errorResponse = Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(500)
                .message(if(e.message.isNullOrEmpty()) "Internal Server Error" else e.message)
                .body(
                    ResponseBody.create(
                        null,
                        if(e.message.isNullOrEmpty()) "Internal Server Error" else e.message
                    )
                ) // Set an empty response body
                .build()

            return errorResponse
        }
    }

    private fun handleSuccess(response: Response) {
        if (response.body() != null) {
            val responseString = response.body().toString()
            Log.d(
                "http", "endService "
                        + response.request().url()
                        + " : "
                        + Date().time
                        + ":"
                        + responseString
            )
        }
    }

    private fun handleError(e: Exception, service: String) {
        Log.d(TAG, e.toString())
        Log.d(TAG, "endService error " + service + " : " + Date().time)
    }

    companion object {
        private const val TAG = "HeaderInterceptorTag"
        const val KEY_AUTHORIZATION = "Authorization"
        private const val KEY_ACCEPT = "Accept"
        const val KEY_BEARER = "Bearer %s"
    }
}