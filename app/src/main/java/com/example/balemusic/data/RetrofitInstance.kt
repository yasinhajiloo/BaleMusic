package com.example.balemusic.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    //Creating Auth Interceptor to add api_key query in front of all the requests.
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("x-rapidapi-host", "billboard-api2.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "400698a87dmsh5ca5c8c332a4accp1d306ajsn46cdc083c54c")
                .addHeader("useQueryString", "true")
                .build()

            return@addInterceptor it.proceed(request)
        }

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(httpClient.build())
        .baseUrl("https://billboard-api2.p.rapidapi.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    val billBoardApi: BillBoardAPI = retrofit().create(BillBoardAPI::class.java)

}