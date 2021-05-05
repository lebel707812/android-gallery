package com.example.gallery.activity

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import com.example.gallery.adapter.ImageAdapter
import com.example.gallery.fragment.Image
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private var imageRecycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var allPictures: ArrayList<Image>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    imageRecycler = findViewById(R.id.image_recycler)
    progressBar = findViewById(R.id.progress_recycler)

        imageRecycler?.layoutManager = GridLayoutManager(this, 3 )
        imageRecycler?.setHasFixedSize(true)



    // Storage Permissions
    if (ContextCompat.checkSelfPermission(this@MainActivity,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )!=PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
        101)
    }

     allPictures= ArrayList()

        if (allPictures!!.isEmpty()){
            progressBar?.visibility = View.VISIBLE
            //get All Images From Storage
            allPictures = getAllImages()
            // Set Adapter to recycler
            imageRecycler?.adapter = ImageAdapter(this,allPictures!!)
            progressBar?.visibility = View.GONE
        }


    }

    private fun getAllImages(): ArrayList<Image>? {
        val images = ArrayList<Image>()

        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        val cursor = this@MainActivity.contentResolver.query(allImageUri, projection,null,null, null)

        try {
            cursor!!.moveToFirst()
            do {
                val image = Image()
                image.imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA))
                image.imageName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                images.add(image)
            } while (cursor.moveToNext())
            cursor.close()


        } catch (e:Exception){
            e.printStackTrace()
        }
        return images
    }
}