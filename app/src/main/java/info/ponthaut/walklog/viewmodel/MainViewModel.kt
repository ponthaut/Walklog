package info.ponthaut.walklog.viewmodel

import androidx.lifecycle.LiveData
import info.ponthaut.walklog.entities.WalkUnit
import info.ponthaut.walklog.repository.WalkRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainViewModel {

    private val repository = WalkRepository()

    private var _uncompleted: LiveData<List<WalkUnit>> = repository.load()
    fun data() = _uncompleted

    fun load() {
        _uncompleted = repository.load()
    }

    private fun itemList(): List<WalkUnit> = data().value ?: arrayListOf()

    fun stepsAtDate(daysToSubtract: Long = 0) : Int {
        val today = LocalDate.now()
        return itemList().findLast { it.date == today.minusDays(daysToSubtract).toString() }?.steps ?: 0
    }

    fun countClearedDays(daysToSubtract: Long, goal: Int) : Int {
        val today = LocalDate.now()
        return itemList().asSequence().filter { isAfter(it.date, today.minusDays(daysToSubtract)) }.count { it.steps >= goal }
    }

    private fun isAfter(date: String, stdDate: LocalDate): Boolean = LocalDate.parse(date, DateTimeFormatter.ISO_DATE).isAfter(stdDate)
}