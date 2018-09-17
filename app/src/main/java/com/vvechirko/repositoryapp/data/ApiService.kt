package com.vvechirko.repositoryapp.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    const val API_ENDPOINT = "https://jsonplaceholder.typicode.com/"
    const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"

    fun <S> create(clazz: Class<S>) = Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .client(OkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder()
                            .setDateFormat(DATE_FORMAT)
                            .create()
            )).build()
            .create(clazz)
}