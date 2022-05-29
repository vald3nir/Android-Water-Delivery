package com.vald3nir.toolkit.extensions

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.vald3nir.toolkit.componets.HeaderNavigationComponent

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun RecyclerView.setupLayoutManager(
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.VERTICAL,
        false
    ),
    itemDecoration: RecyclerView.ItemDecoration? = null
) {
    this.layoutManager = layoutManager
    if (itemDecoration != null) {
        while (itemDecorationCount > 0) {
            removeItemDecorationAt(0)
        }
        this.addItemDecoration(itemDecoration)
    }
}

fun HeaderNavigationComponent.setupToolbar(
    activity: Activity?,
    title: String,
    showBackButton: Boolean = true,
    menuClickListener: (() -> Unit)? = null
) {
    setTitle(title)
    showBackButton(showBackButton)
    showMenuButton(menuClickListener != null)
    setBackButtonClickListener { activity?.onBackPressed() }
    setMenuButtonClickListener { menuClickListener?.invoke() }
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

fun TabLayout.actionClickListener(actionClickListener: (position: Int) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            actionClickListener.invoke(tab.position)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {}
        override fun onTabReselected(tab: TabLayout.Tab) {}
    })
}