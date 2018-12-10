package info.ponthaut.walklog

import android.app.Application
import androidx.room.Room
import info.ponthaut.walklog.db.WalkDatabase

class WalkLogApplication : Application() {
    companion object {
        lateinit var database: WalkDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, objectOf<WalkDatabase>(), "walklog.db").build()
    }
}

internal inline fun <reified T : Any> objectOf() = T::class.java