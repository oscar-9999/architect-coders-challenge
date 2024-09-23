package com.acchallenge.rickandmorty.di

import android.content.Context
import com.acchallenge.rickandmorty.BuildConfig
import com.acchallenge.rickandmorty.R
import com.acchallenge.rickandmorty.data.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    @Named("socketTimeout")
    fun provideSocketTimeout(context: Context): Int {
        return context.resources.getInteger(R.integer.socketTimeout)
    }

    @Provides
    @Singleton
    @Named("connectionTimeout")
    fun provideConnectionTimeout(context: Context): Int {
        return context.resources.getInteger(R.integer.connectionTimeout)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    @Named("OkHttpClient")
    @Singleton
    internal fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @Named("socketTimeout") socketTimeout: Int,
        @Named("connectionTimeout") connectionTimeout: Int,
    ): OkHttpClient {
        val clientBuilder = OkHttpClient().newBuilder()
        clientBuilder.readTimeout(socketTimeout.toLong(), TimeUnit.SECONDS)
        clientBuilder.connectTimeout(connectionTimeout.toLong(), TimeUnit.SECONDS)
        clientBuilder.writeTimeout(30, TimeUnit.SECONDS)
        clientBuilder.addInterceptor(loggingInterceptor)

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideApiServiceNeuron(
        @Named("OkHttpClient") client: OkHttpClient,
        gson: Gson,
    ): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiService::class.java)
    }
}
