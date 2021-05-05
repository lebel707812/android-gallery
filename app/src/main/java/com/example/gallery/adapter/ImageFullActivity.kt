package com.example.gallery.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gallery.R

class ImageFullActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_full)

        val imagePath = intent.getStringExtra("path")
        val imageName = intent.getStringExtra("name")

        supportActionBar?.setTitle(imageName)
        Glide.with(this)
            .load(imagePath)
            .into(findViewById(R.id.imageView))


    }
}