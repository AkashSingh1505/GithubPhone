package com.akma.githubusersearch.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import com.akma.githubusersearch.databinding.ProgressBarLayoutBinding

object Loader {

    private var _dialog: Dialog? = null
    private var layoutInflater: LayoutInflater? = null
    private var dialogBinding: ProgressBarLayoutBinding? = null

    fun showProgress(activity: Context, title: String?) {
        layoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _dialog?.let {
            hideProgress()
        }
        dialogBinding = ProgressBarLayoutBinding.inflate(layoutInflater!!)
        title?.let {
            dialogBinding?.progressTitle?.visibility = View.VISIBLE
            dialogBinding?.progressTitle?.text = it
        }
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(1)
        dialog.window?.setBackgroundDrawable(ColorDrawable(0))
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding!!.root)
        _dialog = dialog
        dialog.show()
    }

    fun hideProgress() {
        if (_dialog?.isShowing == true) {
            _dialog?.dismiss()
            dialogBinding?.progressTitle?.visibility = View.GONE
        }
    }
}
