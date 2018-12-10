package info.ponthaut.walklog.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import info.ponthaut.walklog.R
import kotlinx.android.synthetic.main.view_day_info.view.*
import java.time.LocalDate

class CircleDateStatusWidget @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_day_info, this, true)
    }

    private var goal: Int = 0

    fun setDate(date: LocalDate) {
        textViewDate.text = date.toString()
    }

    fun setGoal(goal: Int) {
        circleProgressBar.maxValue = goal.toFloat()
        this.goal = goal
    }

    fun setValue(value: Int) {
        textViewStepsAtDate.text = String.format("%d steps", value)
        circleProgressBar.setValue(calc(value.toFloat()))
    }

    private fun calc(f: Float) : Float = if (f > goal) goal.toFloat() else f
}