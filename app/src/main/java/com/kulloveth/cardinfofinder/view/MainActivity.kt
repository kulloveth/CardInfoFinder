package com.kulloveth.cardinfofinder.view

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.google.android.material.snackbar.Snackbar
import com.kulloveth.cardinfofinder.R
import com.kulloveth.cardinfofinder.data.Injection
import com.kulloveth.cardinfofinder.databinding.ActivityMainBinding
import com.kulloveth.cardinfofinder.network.Status
import com.kulloveth.cardinfofinder.utils.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private var binding: ActivityMainBinding? = null
    private var viewModel: MainViewModel? = null
    var cardNo: String? = null
    var view: View? = null
    var storagePermission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        view = binding?.cardCv
        //initialize mainViewModel
        viewModel = ViewModelProvider(this,Injection.provideViewModelFactory())[MainViewModel::class.java]
        getDetailsfromEditText()
        getDetailsFromImage()
    }

    fun getDetailsfromEditText() {
        binding?.cardNoInput?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                cardNo = editable?.toString()?.trim()
                binding?.cardNo?.text = cardNo
                if (isNetworkAvailable(this@MainActivity)){
                if (cardNo?.length.isLessThan(20)&& !cardNo.equals("")) {
                    cardNo?.toLong()?.let { no ->
                        viewModel?.fetchCardDetails(no)?.observe(this@MainActivity, Observer {
                            Log.d(TAG, "card details are $it")
                            when (it.status) {
                                Status.SUCCESS -> {
                                    binding?.cardType?.text = it.data?.type
                                    binding?.bank?.text = it.data?.bank?.name
                                    binding?.schemeType?.text = it.data?.scheme
                                    binding?.currencyType?.text = it.data?.country?.currency
                                }
                                Status.ERROR -> {
                                    view?.let { view ->
                                        Snackbar.make(
                                            view,
                                            "Something went wrong ${it.message}",
                                            Snackbar.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        })
                    }}else {
                            binding?.cardType?.text = ""
                            binding?.bank?.text = ""
                            binding?.schemeType?.text = ""
                            binding?.currencyType?.text = ""

                    } } else{
                    view?.let { Snackbar.make(it,"No  internet",Snackbar.LENGTH_SHORT).show() }
                }}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }) }

    fun getDetailsFromImage() {
        binding?.ocrTv?.setOnClickListener {
            showImageImportDialog()
        } }

    fun showImageImportDialog() {
        val items = arrayOf("Camera", "Gallery")
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Select Image")
            setItems(items) { _, index ->
                when (index) {
                    0 -> {
                        if (!checkCameraPermission()) {
                            //storage permission not allowed
                            requestCamera()
                        } else {
                            openCamera()
                        } }
                    1 -> {
                        //storage option
                        if (!checkStoragePermission()) {
                            //storage permission not allowed
                            requestStorage()
                        } else {
                            openStorage() } } } } }.create().show()

    }


    private fun requestStorage() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE)
    }

    private fun requestCamera() {
        ActivityCompat.requestPermissions(this, storagePermission, CAMERA_REQUEST_CODE)
    }

    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED) }


    //get camera and storage permission
    private fun checkCameraPermission(): Boolean {
        val cameraResult = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                (PackageManager.PERMISSION_GRANTED)
        return cameraResult }

    private fun openStorage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_FROM__GALLERY_CODE)
    }

    //take picture from camera and send to activity
    private fun openCamera() {
        val values = ContentValues()
        values.apply {
            put(MediaStore.Images.Media.TITLE, "CARD IMAGE")
            put(MediaStore.Images.Media.DESCRIPTION, "CREDIT CARD NO FROM IMAGE")
        }
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, PICK_IMAGE_FROM_CAMERA)
    }

    //handle permission result
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this, getString(R.string.camera_denied_message), Toast.LENGTH_LONG
                    ).show() } }


            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val writeToStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (writeToStorageAccepted) {
                        openStorage()
                    } else {
                        view?.let {
                            Snackbar.make(it, "GALERY PERMISSION DENIED", Snackbar.LENGTH_SHORT)
                                .show() } } } }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults) }
    }

    //handle imageResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_FROM__GALLERY_CODE) {
                CropImage.activity(data?.data).setGuidelines(CropImageView.Guidelines.ON)
                    .start(this)
            }
            if (requestCode == PICK_IMAGE_FROM_CAMERA) {
                CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON)
                    .start(this)
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                val bitmap: Bitmap = getBitmap(this.getContentResolver(), resultUri)
                val recognizer: TextRecognizer = TextRecognizer.Builder(applicationContext).build()
                val frame: Frame = Frame.Builder().setBitmap(bitmap).build()
                if (!recognizer.isOperational) {
                    view?.let {
                        Snackbar.make(
                            it,
                            "Error has occured please try again",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    recognizer.release()
                } else {
                    recognizer.setProcessor(object :Detector.Processor<TextBlock>{
                        override fun release() {
                        }
                        override fun receiveDetections(detector: Detector.Detections<TextBlock>) {
                            val textBlocks = detector.detectedItems
                            for (i in 0 until textBlocks.size()) {
                                val textBlock = textBlocks[textBlocks.keyAt(i)]
                                binding?.cardNoInput?.text = textBlock?.toString()?.toEditable() }
                            recognizer.release()
                        }})}
                recognizer.receiveFrame(frame)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                view?.let {
                    Snackbar.make(it, "$error", Snackbar.LENGTH_SHORT).show() } } } }


}
