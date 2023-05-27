package aandroid.example.duckapi.Model

import retrofit2.http.GET
import aandroid.example.duckapi.Model.Duck
import retrofit2.Call

interface DuckInterfaceService {
    @GET("random")
     fun getDuck(): Call<Duck>
    //https://random-d.uk/api/v2

}