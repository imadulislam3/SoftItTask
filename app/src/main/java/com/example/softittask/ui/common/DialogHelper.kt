package com.example.softittask.ui.common

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.example.softittask.databinding.ProgressDialogLayoutBinding

object DialogHelper {
    private var loadingDialog: Dialog? = null

    fun showLoadingDialog(
        activity: Activity
    ) {
        loadingDialog = Dialog(activity)
        if (!loadingDialog?.isShowing!!) {
            loadingDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            loadingDialog?.setCancelable(false)
            val binding: ProgressDialogLayoutBinding =
                ProgressDialogLayoutBinding.inflate(LayoutInflater.from(activity))
            loadingDialog?.setContentView(binding.root)
            loadingDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

            loadingDialog?.show()
        }
    }

    fun hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog?.isShowing == true)
            loadingDialog?.dismiss()
    }
}