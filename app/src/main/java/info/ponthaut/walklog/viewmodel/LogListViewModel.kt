package info.ponthaut.walklog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import info.ponthaut.walklog.WalkLogApplication
import info.ponthaut.walklog.entities.WalkUnit
import info.ponthaut.walklog.repository.WalkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate

class LogListViewModel : ViewModel() {

    companion object {
        const val Max = 30

        class WalkingUnit(val date: String, val steps: Int)
    }

    private val repository = WalkRepository()

    private var _loaded: LiveData<List<WalkUnit>> = repository.load()
    fun loadedData() = _loaded

    private var completedData = arrayListOf<LogListViewModel.Companion.WalkingUnit>()

    fun update(date: String, steps: Int) {
        val dao = WalkLogApplication.database.walkDao()

        CoroutineScope(Dispatchers.Main).launch {
            async(Dispatchers.IO) {
                dao.findByDate(date)?.let {
                    it.steps = steps
                    dao.update(it)
                } ?: run {
                    dao.create(WalkUnit(date, steps))
                }
            }.await()
        }
    }

    fun load() {
        _loaded = repository.load()
    }

    fun complete() {
        val array = arrayListOf<LogListViewModel.Companion.WalkingUnit>()
        val list = _loaded.value ?: arrayListOf()

        var index = 0
        val today = LocalDate.now()
        for (i in 0..Max) {
            val day = today.minusDays(i.toLong()).toString()
            var steps = 0
            searchList@ for (j in index..(list.size - 1)) {
                if (list[j].date == day) {
                    steps = list[j].steps
                    index = j
                    break@searchList
                }
            }
            array.add(LogListViewModel.Companion.WalkingUnit(day, steps))
        }
        completedData = array
    }

    fun itemList(): List<LogListViewModel.Companion.WalkingUnit> = completedData

    fun itemSize(): Int = completedData.size
}