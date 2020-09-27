package com.example.balemusic.data

import retrofit2.http.GET
import retrofit2.http.Query

interface BillBoardAPI {
    @GET("hot-100")
    suspend fun getMusicList(@Query("date") date : String = "2019-05-11" , @Query("range") range : String = "1-10" ) : Response
}