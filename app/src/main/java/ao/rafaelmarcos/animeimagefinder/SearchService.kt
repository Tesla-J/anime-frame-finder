package ao.rafaelmarcos.animeimagefinder

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log

import java.util.Random

import ao.rafaelmarcos.animeimagefinder.httprequest.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SearchService : Service() {
    private val binder = LocalBinder()
    private val mRandom = Random()

    // API URL
    private val mApiUrl = "https://api.trace.moe/search"

    val number: Int
        get() = mRandom.nextInt(100);

    inner class LocalBinder : Binder(){
        fun getService() : SearchService = this@SearchService
    }

    override fun onBind(intent: Intent): IBinder = binder

    fun search(uri : Uri){
        val imageStream = contentResolver.openInputStream(uri)
        val imageBytes = imageStream?.readBytes() ?: ByteArray(0)

        val request = Request(mApiUrl)
        runBlocking {
            withContext(Dispatchers.IO){
                Log.d("RESPONSE", request.sendPostRequest(imageBytes))
            }
        }
    }
}