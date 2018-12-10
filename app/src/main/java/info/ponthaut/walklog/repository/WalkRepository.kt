package info.ponthaut.walklog.repository

import androidx.lifecycle.LiveData
import info.ponthaut.walklog.WalkLogApplication
import info.ponthaut.walklog.entities.WalkUnit

class WalkRepository {

    fun load() : LiveData<List<WalkUnit>> {
        val dao = WalkLogApplication.database.walkDao()
        return dao.findAllBySortedDesc()
    }
}