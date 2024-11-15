package com.akma.githubusersearch.utils

sealed class NetworkResponse<T>( val body:T?=null, val errorMsz:String?=null){
    class Loading<T>:NetworkResponse<T>()
    class Success<T>(body: T?):NetworkResponse<T>(body)
    class Error<T>(errorMsz: String?=null,body: T?=null):NetworkResponse<T>(body ,errorMsz)

}

