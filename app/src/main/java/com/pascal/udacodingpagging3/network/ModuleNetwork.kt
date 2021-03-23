package com.pascal.udacodingpagging3.network
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://www.balldontlie.io/api/"
const val BASE_URL2 = "https://jsonplaceholder.typicode.com/"
class ModuleNetwork {

    companion object {

        fun getInterceptor(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            return client
        }

        fun getService(): ApiService {
            return Retrofit.Builder()
                .client(getInterceptor())
                .baseUrl(BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService::class.java)
        }

        fun getService2(): ApiService {
            return Retrofit.Builder()
                .client(getInterceptor())
                .baseUrl(BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build().create(ApiService::class.java)
        }
    }
}