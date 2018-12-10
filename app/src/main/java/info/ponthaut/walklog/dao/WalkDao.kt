package info.ponthaut.walklog.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import info.ponthaut.walklog.entities.WalkUnit

@Dao
interface WalkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(walk: WalkUnit)

    @Query("SELECT * FROM WalkUnit")
    fun findAll(): List<WalkUnit>

    @Query("SELECT * FROM WalkUnit ORDER BY date(date) DESC")
    fun findAllBySortedDesc(): LiveData<List<WalkUnit>>

    @Query("SELECT * FROM WalkUnit WHERE date == :d")
    fun findByDate(d : String): WalkUnit?

    @Update
    fun update(walk: WalkUnit)

    @Delete
    fun delete(walk: WalkUnit)
}