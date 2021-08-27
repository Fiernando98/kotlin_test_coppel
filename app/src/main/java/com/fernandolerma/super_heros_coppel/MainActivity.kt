package com.fernandolerma.super_heros_coppel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fernandolerma.super_heros_coppel.adapters.AvatarsBoxAdapter
import com.fernandolerma.super_heros_coppel.adapters.AvatarsListAdapter
import com.fernandolerma.super_heros_coppel.interfaces.CountryService
import com.fernandolerma.super_heros_coppel.models.AvatarModel
import com.fernandolerma.super_heros_coppel.models.CountryModel
import com.fernandolerma.super_heros_coppel.pages.VisualizeItem
import com.fernandolerma.super_heros_coppel.services.CountryServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

        loadCountries()
    }

    private fun prepareBoxList() {
        avatarsBoxAdapter = AvatarsBoxAdapter(avatarsList, this) {
            val intent = Intent(this, VisualizeItem::class.java)
            intent.putExtra("name", it.getName())
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
            intent.putExtra("name", it.getName())
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

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Content-type"] = "application/json"
        return headerMap
    }

    private fun loadCountries() {
        val destinationService = CountryServiceBuilder.buildService(CountryService::class.java)
        val requestCall = destinationService.getCountryList(getHeaderMap())
        requestCall.enqueue(object : Callback<List<CountryModel>> {
            override fun onResponse(
                call: Call<List<CountryModel>>,
                response: Response<List<CountryModel>>
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "${response.body()}",
                    Toast.LENGTH_SHORT
                ).show()
                if (response.isSuccessful) {
                    val countryList = response.body()!!
                    Log.d("Response", "countrylist size : ${countryList.size}")
                    /*country_recycler.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(this@MainActivity, 2)
                        adapter = CountriesAdapter(response.body()!!)
                    }*/
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<CountryModel>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: $t", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}