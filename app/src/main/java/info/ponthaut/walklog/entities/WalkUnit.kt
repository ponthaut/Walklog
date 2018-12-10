package info.ponthaut.walklog.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WalkUnit(
        val date: String,
        var steps: Int
) {
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0
}