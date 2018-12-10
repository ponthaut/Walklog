package info.ponthaut.walklog.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import info.ponthaut.walklog.R
import kotlinx.android.synthetic.main.view_stat_info.view.*

class CircleStatisticsWidget @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_stat_info, this, true)
    }

    private var blockCount: Int = 0

    fun setLabel(label: String) {
        textViewLabel.text = label
    }

    fun setBlockCount(value: Int) {
        circleProgressBar.blockCount = value
        circleProgressBar.maxValue = value.toFloat()
        this.blockCount = value
    }

    fun setValue(value: Int) {
        circleProgressBar.setValue(value.toFloat())
        circleProgressBar.setText(String.format("%d/%d", value, blockCount))
    }
}