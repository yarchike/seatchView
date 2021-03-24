package com.example.seatchview


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView

class EditTextSearch : SearchView {

    lateinit var textEt: EditText

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        // searchview
        setIconifiedByDefault(false)

        // root linear layout
        val rootLl = (getChildAt(0) as LinearLayout)
        rootLl.layoutParams.apply {
            height = ViewGroup.LayoutParams.WRAP_CONTENT
        }

        // edit frame linear layout
        val editFrameLl = rootLl.getChildAt(2) as LinearLayout
        editFrameLl.layoutParams.apply {
            this as LinearLayout.LayoutParams
            marginStart = 0
            marginEnd = 0
        }
        editFrameLl.layoutParams.apply {
            height = ViewGroup.LayoutParams.WRAP_CONTENT
        }

        // search icon
        val searchIv = editFrameLl.getChildAt(0) as AppCompatImageView
        searchIv.layoutParams.apply {
            width = 0
            this as LinearLayout.LayoutParams
            marginStart = 0
            marginEnd = 0
        }

        // plate linear layout
        val plateLl = editFrameLl.getChildAt(1) as LinearLayout
        plateLl.setPadding(0, 0, 0, 0)
        plateLl.background = ColorDrawable(Color.TRANSPARENT)
        plateLl.layoutParams.apply {
            height = ViewGroup.LayoutParams.WRAP_CONTENT
        }

        // edit text
        textEt = plateLl.getChildAt(0) as EditText
        textEt.hint = queryHint
        textEt.layoutParams.apply {
            height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        textEt.setPadding(11, 27, 11, 30)
        (textEt as AutoCompleteTextView).threshold = 1
        textEt.clearFocus()

        // cancel iv
        val cancelIv = plateLl.getChildAt(1) as ImageView
        cancelIv.layoutParams = LinearLayout.LayoutParams(0, 0)

        // defaults
        setTextSize(16f)
    }

    // interface ///////////////////////////////////////////////////////////////////////////////

    override fun setBackground(drawable: Drawable) {
        textEt.background = drawable
    }

    fun setTextSize(size: Float) {
        textEt.textSize = size
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        textEt.addTextChangedListener(textWatcher)
    }

    fun getText(): String {
        return textEt.text.toString()
    }
}
