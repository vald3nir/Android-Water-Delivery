package com.vald3nir.diskwater.presentation.product

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.databinding.FragmentProductDetailBinding
import com.vald3nir.toolkit.extensions.setupToolbar
import com.vald3nir.toolkit.extensions.toMoney
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductDetailFragment : BaseFragment() {

    private val viewModel: ProductViewModel by viewModel()
    lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewModel.loadData(this)
        viewModel.appView = appView
        binding.apply {
            toolbar.setupToolbar(
                activity = activity,
                showBackButton = true,
                title = getString(
                    if (viewModel.productDTO == null) R.string.add_product else R.string.update_product
                )
            )

//            val gsReference = FirebaseStorage.getInstance()
//                .getReferenceFromUrl("gs://diskwater-afb43.appspot.com/images/valdenir.jpg")
//            gsReference.downloadUrl.addOnSuccessListener {
//                Glide
//                    .with(this@ProductDetailFragment)
//                    .load(it)
//                    .centerCrop()
//                    .placeholder(R.drawable.generic_water)
//                    .into(binding.imageView)
//
//            }.addOnFailureListener { it.printStackTrace() }

            txtChangeImage.setOnClickListener {
                takePhoto()
            }
            edtName.setText(viewModel.productDTO?.name)
            edtPrice.setText(viewModel.productDTO?.price.toMoney())
            btnSaveProducts.apply {
                setTitle(getString(if (viewModel.productDTO == null) R.string.register else R.string.update))
                setOnClickListener { viewModel.clickSaveButton() }
            }
        }
    }

    val REQUEST_IMAGE_CAPTURE = 1
    var currentPhotoPath: String = ""

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            activity?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            uploadProductImage(data?.extras?.get("data") as Bitmap)

//            val baos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//            val data = baos.toByteArray()
//
//            val storageRef: StorageReference = FirebaseStorage.getInstance().reference
//            val mountainsRef = storageRef.child("images/valdenir.jpg")
//            var uploadTask = mountainsRef.putBytes(data)
//            uploadTask.addOnFailureListener {
//                it.printStackTrace()
//            }.addOnSuccessListener { taskSnapshot ->
//                println(taskSnapshot)
//            }

        }
    }

    private fun uploadProductImage(bitmap: Bitmap) {
        binding.apply {
            btnSaveProducts.showLoading(true)
            viewModel.uploadProductImage(bitmap, edtName.text.toString(), onSuccess = {
                btnSaveProducts.showLoading(false)
                imageView.setImageBitmap(bitmap)
            })
        }
    }
}