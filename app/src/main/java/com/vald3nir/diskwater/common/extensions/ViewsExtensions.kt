package com.vald3nir.diskwater.common.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.utils.MAX_VALUE_POWER_CONSUMPTION
import com.vald3nir.diskwater.common.utils.getColorPowerValue
import com.vald3nir.diskwater.data.dto.ItemChartRealTimeDTO
import kotlin.math.abs

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun RecyclerView.setup(
    adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.VERTICAL,
        false
    ),
    itemDecoration: RecyclerView.ItemDecoration? = null
) {
    this.adapter = adapter
    this.layoutManager = layoutManager
    if (itemDecoration != null) {
        while (itemDecorationCount > 0) {
            removeItemDecorationAt(0)
        }
        this.addItemDecoration(itemDecoration)
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {

    this.addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.actionDoneListener(actionDoneListener: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) actionDoneListener.invoke()
        false
    }
}
