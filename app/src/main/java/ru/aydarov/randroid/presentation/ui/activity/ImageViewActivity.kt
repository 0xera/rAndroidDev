package ru.aydarov.randroid.presentation.ui.activity

import android.Manifest
import android.app.DownloadManager
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_image_view.*
import ru.aydarov.randroid.R
import ru.aydarov.randroid.data.util.Constants.SRC_OPEN_KEY
import ru.aydarov.randroid.presentation.ui.adapters.LoaderSourceAdapter
import java.io.File
import java.util.*


/**
 * @author Aydarov Askhar 2020
 */
class ImageViewActivity : AppCompatActivity() {
    private var mIsDownloading: Boolean = false

    private var mUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(mIsDownloading)
        }
        mUrl = intent.getStringExtra(SRC_OPEN_KEY)
        mUrl?.let {
            LoaderSourceAdapter.loadImage(ivShowView, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_image_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_download -> {
                if (mIsDownloading) {
                    return false
                }
                mIsDownloading = true

                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE)
                    } else {
                        downloadImage()
                    }
                } else {
                    downloadImage()
                }
            }
            android.R.id.home -> supportFinishAfterTransition()
        }
        return true
    }


    private fun downloadImage() {
        val request = DownloadManager.Request(Uri.parse(mUrl))
        val filename = IMAGE_FILE_NAME + UUID.randomUUID() + FORMAT
        request.setTitle(filename)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, filename)
        } else {
            val path: String = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
            val directory = File("$path$IMAGE_FILE_DIR")
            var saveToInfinityFolder = true
            if (!directory.exists()) {
                if (!directory.mkdir()) {
                    saveToInfinityFolder = false
                }
            } else {
                if (directory.isFile) {
                    if (!(directory.delete() && directory.mkdir())) {
                        saveToInfinityFolder = false
                    }
                }
            }
            if (saveToInfinityFolder) {
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES.toString() + IMAGE_FILE_DIR, filename)
            } else {
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, filename)
            }
        }

        val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        Toast.makeText(this, R.string.download, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, R.string.denied, Toast.LENGTH_SHORT).show()
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && mIsDownloading) {
                downloadImage()
            }
            mIsDownloading = false
        }
    }

    companion object {
        const val FORMAT = ".png"
        const val IMAGE_FILE_NAME = "IMAGE"
        const val IMAGE_FILE_DIR = "/rAndroidDev/image/"
        const val PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 121
    }
}
