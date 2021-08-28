package com.fernandolerma.super_heros_coppel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fernandolerma.super_heros_coppel.adapters.AvatarsBoxAdapter
import com.fernandolerma.super_heros_coppel.adapters.AvatarsListAdapter
import com.fernandolerma.super_heros_coppel.interfaces.HeroesInterface
import com.fernandolerma.super_heros_coppel.models.HeroesModel
import com.fernandolerma.super_heros_coppel.pages.VisualizeItem
import com.fernandolerma.super_heros_coppel.services.HeroesServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private var itemsList: ArrayList<HeroesModel> = arrayListOf()
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

        etSearch.doOnTextChanged { text, _, _, _ ->
            avatarsBoxAdapter.filter.filter(text)
            avatarsListAdapter.filter.filter(text)
        }

        loadDataHeroes()
    }

    private fun prepareBoxList() {
        avatarsBoxAdapter = AvatarsBoxAdapter(itemsList, this) {
            val intent = Intent(this, VisualizeItem::class.java)
            intent.putExtra("name", it.name)
            intent.putExtra("imagePath", it.images.md)
            startActivity(intent)
        }
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvAvatars.layoutManager = mLayoutManager
        rvAvatars.itemAnimator = DefaultItemAnimator()
        rvAvatars.adapter = avatarsBoxAdapter
    }

    private fun prepareList() {
        avatarsListAdapter = AvatarsListAdapter(itemsList, this) {
            val intent = Intent(this, VisualizeItem::class.java)
            intent.putExtra("name", it.name)
            intent.putExtra("imagePath", it.images.md)
            startActivity(intent)
        }
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvList.layoutManager = mLayoutManager
        rvList.itemAnimator = DefaultItemAnimator()
        rvList.adapter = avatarsListAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareRvData() {
        prepareBoxList()
        prepareList()
        avatarsBoxAdapter.notifyDataSetChanged()
        avatarsListAdapter.notifyDataSetChanged()
    }

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Content-type"] = "application/json"
        return headerMap
    }

    private fun loadDataHeroes() {
        val destinationService = HeroesServiceBuilder.buildService(HeroesInterface::class.java)
        val requestCall = destinationService.getAllList(getHeaderMap())
        requestCall.enqueue(object : Callback<ArrayList<HeroesModel>> {
            override fun onResponse(
                call: Call<ArrayList<HeroesModel>>,
                response: Response<ArrayList<HeroesModel>>
            ) {
                if (response.isSuccessful) {
                    itemsList = response.body()!!
                    prepareRvData()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<HeroesModel>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: $t", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}