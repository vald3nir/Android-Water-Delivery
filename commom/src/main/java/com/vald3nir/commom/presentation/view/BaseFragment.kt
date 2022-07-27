package com.vald3nir.commom.presentation.view

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import com.vald3nir.core_ui.CoreFragment
import com.vald3nir.core_ui.components.CustomSheetDialog
import com.vald3nir.repository.BaseDTO

abstract class BaseFragment : CoreFragment() {

    val REQUEST_IMAGE_CAPTURE = 1

    protected var dialog: CustomSheetDialog? = null

    fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            activity?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    fun putExtraDTO(baseDTO: BaseDTO?) {
        val bundle = Bundle()
        bundle.putSerializable("EXTRA", baseDTO)
        this.arguments = bundle
    }

    fun loadExtraDTO(): BaseDTO? {
        return arguments?.getSerializable("EXTRA") as BaseDTO?
    }


    fun setToolbarTitle(title: Int) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).setToolbarTitle(title)
        }
    }

    fun setToolbarTitle(title: String) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).setToolbarTitle(title)
        }
    }

    override fun onDestroy() {
        dialog?.dismiss()
        super.onDestroy()
    }
}