package com.vald3nir.diskwater.common

import android.content.Intent
import android.provider.MediaStore
import com.vald3nir.toolkit.componets.customviews.CustomSheetDialog
import com.vald3nir.toolkit.core.CoreFragment

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

    override fun onDestroy() {
        dialog?.dismiss()
        super.onDestroy()
    }
}