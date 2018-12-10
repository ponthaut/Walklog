package info.ponthaut.walklog.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import info.ponthaut.walklog.R
import kotlinx.android.synthetic.main.input_dialog.*
import android.view.ViewGroup



class InputDialogFragment : DialogFragment() {

    companion object {
        const val KEY_TITLE = "key_title"
        const val KEY_STEPS = "key_steps"
    }

    var onSaveListener: DialogInterface.OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(context!!).apply {
            setContentView(R.layout.input_dialog)

            label_input_title.text = arguments?.getString(KEY_TITLE)
            edit_input_steps.setText(String.format("%d", arguments?.getInt(KEY_STEPS) ?: 0))
            button_input_cancel.setOnClickListener {
                dismiss()
            }
            button_input_save.setOnClickListener {
                val steps = Integer.parseInt(edit_input_steps.text.toString())
                onSaveListener?.onClick(this, steps)
                dismiss()
            }
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    override fun onResume() {
        super.onResume()

        val params = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as android.view.WindowManager.LayoutParams
    }
}