package aandroid.example.duckapi

import aandroid.example.duckapi.Model.Duck
import aandroid.example.duckapi.Model.NetworkBroadcastReceiver
import aandroid.example.duckapi.Model.RetrofitInstance
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentFilter
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    lateinit var receiver: NetworkBroadcastReceiver
    val duckArray = ArrayList<Duck>()
    private lateinit var duckPicture:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        receiver = NetworkBroadcastReceiver()
        val scope = CoroutineScope(Dispatchers.Default)
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            .also{
                registerReceiver(receiver,
                it)
            }
        val duckTestButton = findViewById<Button>(R.id.duckTestButton)
        duckPicture = findViewById<ImageView>(R.id.duckView)
        val duckInitialUrl = "https://m.media-amazon.com/images/I/31rB6TRvW5L._AC_UF894,1000_QL80_.jpg"
        duckUpdate(duckInitialUrl)


        duckTestButton.setOnClickListener {
            scope.launch {
                duckTest()
            }
        }

    }

    override fun onStop()
    {
        super.onStop()
        unregisterReceiver(receiver)
    }

    suspend fun duckTest(){
        Log.d(TAG,"Hello from the otherside")

        val retrofitInstance = RetrofitInstance()
        val call = retrofitInstance.retrofitService.getDuck()

        call.enqueue(object : Callback<Duck>{
            override fun onResponse(call: Call<Duck>,
                                    response: Response<Duck>) {
                Log.d(TAG,"SUCCESS")
                val duckResult = response.body()
                if(duckResult != null) {
                    duckArray.clear()
                    duckArray.add(duckResult)
                    duckUpdate(duckResult.url)
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

    fun duckUpdate(url:String){
        Picasso.get()
            .load(url)
            .into(duckPicture)
    }

}