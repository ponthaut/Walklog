package info.ponthaut.walklog.db

import androidx.room.Database
import androidx.room.RoomDatabase
import info.ponthaut.walklog.dao.WalkDao
import info.ponthaut.walklog.entities.WalkUnit

@Database(entities = [WalkUnit::class], version = 1)
abstract class WalkDatabase : RoomDatabase() {
    abstract fun walkDao(): WalkDao
}