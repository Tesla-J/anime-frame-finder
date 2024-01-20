package ao.rafaelmarcos.animeimagefinder

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import java.util.Random

class SearchService : Service() {
    private val binder = LocalBinder()
    private val mRandom = Random()

    val number: Int
        get() = mRandom.nextInt(100);

    inner class LocalBinder : Binder(){
        fun getService() : SearchService = this@SearchService
    }

    override fun onBind(intent: Intent): IBinder = binder

}