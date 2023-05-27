package aandroid.example.duckapi.Model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://random-d.uk/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitService: DuckInterfaceService = retrofit.create(DuckInterfaceService::class.java)
}