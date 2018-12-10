package info.ponthaut.walklog.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import info.ponthaut.walklog.R
import info.ponthaut.walklog.ui.InputDialogFragment.Companion.KEY_STEPS
import info.ponthaut.walklog.ui.InputDialogFragment.Companion.KEY_TITLE
import info.ponthaut.walklog.utils.Util
import info.ponthaut.walklog.viewmodel.LogListViewModel
import kotlinx.android.synthetic.main.activity_log_list.*
import kotlinx.android.synthetic.main.list_item_log.view.*

class LogListActivity : AppCompatActivity() {

    private lateinit var model: LogListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_list)
        setSupportActionBar(toolbar)

        model = LogListViewModel()
        model.load()

        recyclerView.adapter = RecyclerAdapter(this, model, object : OnItemClickListener {
            override fun onItemClick(item: LogListViewModel.Companion.WalkingUnit) {
                val dialog = InputDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(KEY_TITLE, item.date)
                        putInt(KEY_STEPS, item.steps)
                    }
                    onSaveListener = DialogInterface.OnClickListener { _, which ->
                        model.update(item.date, which)
                    }
                }
                dialog.show(supportFragmentManager, "input")
            }
        })
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
    }

    interface OnItemClickListener {
        fun onItemClick(item: LogListViewModel.Companion.WalkingUnit)
    }

    class RecyclerAdapter(private val context: AppCompatActivity, private val model: LogListViewModel?, private val listener: OnItemClickListener) : androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

        private val goal = Util.goal(context)

        init {
            model?.loadedData()?.observe({ context.lifecycle }, {
                it?.apply {
                    model.complete()
                    notifyDataSetChanged()
                }
            })
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerAdapter.ViewHolder {
            val layoutInflater = LayoutInflater.from(context)
            return ViewHolder(layoutInflater.inflate(R.layout.list_item_log, viewGroup, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            model?.itemList()?.let {
                val steps = it[position].steps
                holder.itemView.itemTextViewDate.text = it[position].date
                holder.itemView.itemTextView.text = String.format("Steps : %d", steps)
                holder.itemView.itemProgressBar.max = goal
                holder.itemView.itemProgressBar.progress = steps
                holder.itemView.itemTextViewClear.visibility = if (steps >= goal) View.VISIBLE else View.INVISIBLE

                holder.bind(it[position], listener)
            }
        }

        override fun getItemCount(): Int {
            return model?.itemSize() ?: 0
        }

        inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

            init {
            }

            fun bind(item: LogListViewModel.Companion.WalkingUnit, listener: OnItemClickListener) {
                itemView.itemImageButtonEdit.setOnClickListener { listener.onItemClick(item) }
            }
        }
    }
}
