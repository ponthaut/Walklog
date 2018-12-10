package info.ponthaut.walklog.utils

import android.content.Context
import androidx.core.content.edit

class Util {

    companion object {
        private const val PREF_KEY_GOAL = "info.ponthaut.walklog.goal"
        private const val KEY_GOAL = "KEY_GOAL"
        private const val DEFAULT_GOAL = 10_000

        fun saveGoal(context: Context, goal: Int) {
            context.getSharedPreferences(PREF_KEY_GOAL, Context.MODE_PRIVATE).edit {
                putInt(KEY_GOAL, goal)
            }
        }

        fun goal(context: Context): Int {
            return context.getSharedPreferences(PREF_KEY_GOAL, Context.MODE_PRIVATE).getInt(KEY_GOAL, DEFAULT_GOAL)
        }
    }
}