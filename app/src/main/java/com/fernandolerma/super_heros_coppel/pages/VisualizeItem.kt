package com.fernandolerma.super_heros_coppel.pages

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fernandolerma.super_heros_coppel.R

class VisualizeItem : AppCompatActivity() {
    private lateinit var ivImage: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvInfo: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualize_item)
        val name = intent.getStringExtra("name")
        val imagePath = intent.getStringExtra("imagePath")
        title = name

        ivImage = findViewById(R.id.ivVisualizeItemImage)
        tvName = findViewById(R.id.tvVisualizeItemName)
        tvInfo = findViewById(R.id.tvVisualizeItemInfo)

        Glide.with(this).load(Uri.parse(imagePath)).into(ivImage)
        tvName.text = name
        tvInfo.text = "Información general del personaje"
    }
}