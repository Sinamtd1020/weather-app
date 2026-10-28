package com.sinamtd.sixappcompose.domain.repository

import android.util.Log
import com.google.gson.JsonSyntaxException
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ApiRepository @Inject constructor() {
    suspend fun <T> getSafe(request:suspend ()->T): Result<T>{
        return try {
            val response= request()
            Result.success(response)
        }
        catch (e: Exception){
            when(e){
                is SocketTimeoutException -> Log.e("Tag","Socket")
                is IOException -> Log.e("Tag","conection")
                is HttpException -> Log.e ("Tag","Http")
                is JsonSyntaxException -> Log.e("Tag","Json")
                else -> Log.e("Tag","unknown error")

            }
            Result.failure(e)
        }
    }
}