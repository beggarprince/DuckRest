package aandroid.example.duckapi

import aandroid.example.duckapi.Model.Duck
import aandroid.example.duckapi.Model.RetrofitInstance
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val duckArray = ArrayList<Duck>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scope = CoroutineScope(Dispatchers.Default)

        val duckTestButton = findViewById<Button>(R.id.duckTestButton)

        duckTestButton.setOnClickListener {
            scope.launch {
                duckTest()
            }
        }

    }

    suspend fun duckTest(){
        Log.d(TAG,"Hello from the otherside")

        val retrofitInstance = RetrofitInstance()
        //val retrofitService = retrofitInstance.retrofitService
        val call = retrofitInstance.retrofitService.getDuck()

        call.enqueue(object : Callback<Duck>{
            override fun onResponse(call: Call<Duck>,
                                    response: Response<Duck>) {
                Log.d(TAG,"SUCCESS")
                val duckResult = response.body()
                if(duckResult != null) {
                    duckArray.add(duckResult)
                }
                for(duck in duckArray){
                    Log.d(TAG,duck.toString())
                }
            }

            override fun onFailure(call: Call<Duck>, t: Throwable) {
                Log.d(TAG,"FAILURE")
            }
        })

    }

}