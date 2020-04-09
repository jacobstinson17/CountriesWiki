package com.jacobstinson.starwarswiki.binding

import androidx.databinding.BindingAdapter

object MyBindingAdapters {
    //Examples
    /********
     * onTouch
     ********/
    /*@BindingAdapter("onTouch")
    @JvmStatic
    fun setOnTouchListener(view: View, onTouchListener: View.OnTouchListener?) {
        if (onTouchListener != null)
            view.setOnTouchListener(onTouchListener)
    }*/

    /**********************
     * Boolean to Visibility
     **********************/
    /*@BindingAdapter("android:visibility")
    @JvmStatic
    fun setVisibility(view: View, value: Boolean) {
        view.visibility = if (value) View.VISIBLE else View.GONE
    }*/

    /****************
     * Seconds Increment
     ***************/
    /*@BindingAdapter("secondsIncrementAttrChanged")
    @JvmStatic
    fun setSecondsIncrementListener(textView: TextView, listener: InverseBindingListener?) {
        if (listener != null) {
            textView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    listener.onChange()
                }
            })
        }
    }*/

    /*@BindingAdapter("secondsIncrement")
    @JvmStatic
    fun setSecondIncrements(view: TextView, secondIncrements: Int) {
        val text = secondIncrements.toString() + "s"
        if(view.text.toString() != text) {
            view.text = text
        }
    }*/
}