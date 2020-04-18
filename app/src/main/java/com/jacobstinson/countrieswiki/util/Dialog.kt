package com.jacobstinson.countrieswiki.util

import android.app.AlertDialog
import android.content.Context

object Dialog {

    fun getRadioButtonsDialog(context: Context, title: String, choices: Array<String>, selectedIndex: Int, completion: (selection: String) -> Unit): AlertDialog {
        val alertBuilder = AlertDialog.Builder(context)

        alertBuilder.setTitle(title)
        alertBuilder.setSingleChoiceItems(choices, selectedIndex) { dialog, item ->
            completion(choices[item])
            dialog.dismiss()
        }

        return alertBuilder.create()
    }
}