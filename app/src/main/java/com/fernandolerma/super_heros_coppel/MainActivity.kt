package com.fernandolerma.super_heros_coppel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fernandolerma.super_heros_coppel.adapters.AvatarsBoxAdapter
import com.fernandolerma.super_heros_coppel.adapters.AvatarsListAdapter
import com.fernandolerma.super_heros_coppel.models.AvatarModel
import com.fernandolerma.super_heros_coppel.pages.VisualizeItem


class MainActivity : AppCompatActivity() {
    private var avatarsList: ArrayList<AvatarModel> = arrayListOf()
    private lateinit var avatarsBoxAdapter: AvatarsBoxAdapter
    private lateinit var avatarsListAdapter: AvatarsListAdapter
    private lateinit var etSearch: EditText
    private lateinit var rvAvatars: RecyclerView
    private lateinit var rvList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Avatars"
        etSearch = findViewById(R.id.etSearch)
        rvAvatars = findViewById(R.id.rvAvatars)
        rvList = findViewById(R.id.rvList)
        prepareBoxList()
        prepareList()
        prepareMovieData()

        etSearch.doOnTextChanged { text, start, before, count ->
            avatarsBoxAdapter.filter.filter(text)
            avatarsListAdapter.filter.filter(text)
        }
    }

    private fun prepareBoxList() {
        avatarsBoxAdapter = AvatarsBoxAdapter(avatarsList, this) {
            val intent = Intent(this, VisualizeItem::class.java)
            intent.putExtra("name", it.getTitle())
            intent.putExtra("imagePath", it.getImagePath())
            startActivity(intent)
        }
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvAvatars.layoutManager = mLayoutManager
        rvAvatars.itemAnimator = DefaultItemAnimator()
        rvAvatars.adapter = avatarsBoxAdapter
    }

    private fun prepareList() {
        avatarsListAdapter = AvatarsListAdapter(avatarsList, this) {
            val intent = Intent(this, VisualizeItem::class.java)
            intent.putExtra("name", it.getTitle())
            intent.putExtra("imagePath", it.getImagePath())
            startActivity(intent)
        }
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvList.layoutManager = mLayoutManager
        rvList.itemAnimator = DefaultItemAnimator()
        rvList.adapter = avatarsListAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareMovieData() {
        avatarsList.addAll(
            listOf(
                AvatarModel(
                    "Super man",
                    "https://www.tomosygrapas.com/wp-content/uploads/2021/02/Portada-co%CC%81mic-Superman-78-de-Reeves-copia.jpg"
                ),
                AvatarModel(
                    "Batman",
                    "https://dam.smashmexico.com.mx/wp-content/uploads/2020/04/ayuda-a-tu-pequeno-a-hacer-el-cinturon-de-batman-cover.jpg"
                ),
                AvatarModel(
                    "Flash",
                    "https://img.europapress.es/fotoweb/fotonoticia_20210121145702_420.jpg"
                ),
                AvatarModel(
                    "Hulk",
                    "https://i.pinimg.com/originals/3e/2f/6a/3e2f6a9ce186fe59b4a7d392e1c96764.jpg"
                ),
                AvatarModel(
                    "Thanos",
                    "https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/media/image/2019/04/thanos_0.jpg"
                )
            )
        )
        avatarsBoxAdapter.notifyDataSetChanged()
        avatarsListAdapter.notifyDataSetChanged()
    }
}