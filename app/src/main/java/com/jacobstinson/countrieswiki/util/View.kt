package com.jacobstinson.countrieswiki.util

import android.content.Context

object View {
    fun dpFromPx(context: Context, px: Int): Int {
        return (px / context.resources.displayMetrics.density).toInt()
    }

    fun pxFromDp(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}