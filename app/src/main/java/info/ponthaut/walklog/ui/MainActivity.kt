package info.ponthaut.walklog.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import info.ponthaut.walklog.R
import info.ponthaut.walklog.utils.Util
import info.ponthaut.walklog.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = MainViewModel()
        model.load()

        model.data().observe({ lifecycle }, {
            it?.apply {
                updateView()
            }
        })

        buttonMoreDetails.setOnClickListener {
            val intent = Intent(this, LogListActivity::class.java)
            startActivity(intent)
        }

        buttonMoreStatistics.setOnClickListener {
            val toast = Toast.makeText(applicationContext, R.string.alert_not_implemented, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onStart() {
        super.onStart()

        initView()
        updateView()
    }

    private fun initView() {
        val today = LocalDate.now()

        val goal = Util.goal(this)
        textViewCurrentGoalSteps.text = NumberFormat.getNumberInstance(Locale.US).format(goal)

        circleDateStatusWidget1.setDate(today)
        circleDateStatusWidget1.setGoal(goal)

        circleDateStatusWidget2.setDate(today.minusDays(1))
        circleDateStatusWidget2.setGoal(goal)

        circleDateStatusWidget3.setDate(today.minusDays(2))
        circleDateStatusWidget3.setGoal(goal)

        circleStatisticsWidget1.setLabel(getString(R.string.label_last_three_days))
        circleStatisticsWidget1.setBlockCount(3)

        circleStatisticsWidget2.setLabel(getString(R.string.label_last_seven_days))
        circleStatisticsWidget2.setBlockCount(7)

        circleStatisticsWidget3.setLabel(getString(R.string.label_last_thirty_days))
        circleStatisticsWidget3.setBlockCount(30)
    }

    private fun updateView() {
        val goal = Util.goal(applicationContext)

        circleDateStatusWidget1.setValue(model.stepsAtDate(0))
        circleDateStatusWidget2.setValue(model.stepsAtDate(1))
        circleDateStatusWidget3.setValue(model.stepsAtDate(2))

        val count1 = model.countClearedDays(3, goal)
        circleStatisticsWidget1.setValue(count1)

        val count2 = model.countClearedDays(7, goal)
        circleStatisticsWidget2.setValue(count2)

        val count3 = model.countClearedDays(30, goal)
        circleStatisticsWidget3.setValue(count3)
    }
}
