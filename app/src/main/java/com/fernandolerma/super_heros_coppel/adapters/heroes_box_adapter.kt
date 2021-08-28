package com.fernandolerma.super_heros_coppel.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fernandolerma.super_heros_coppel.R
import com.fernandolerma.super_heros_coppel.models.HeroesModel

internal class AvatarsBoxAdapter(
    private var itemsList: ArrayList<HeroesModel>,
    private var context: Context,
    private var onTap: (HeroesModel) -> Unit
) :
    RecyclerView.Adapter<AvatarsBoxAdapter.MyViewHolder>(), Filterable {
    var itemsFiltered: ArrayList<HeroesModel> = itemsList

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llyAvatarBoxItem: LinearLayout = view.findViewById(R.id.llyAvatarBoxItem)
        val name: TextView = view.findViewById(R.id.tvName)
        val image: ImageView = view.findViewById(R.id.ivImage)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.avatar_box_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsFiltered[position]
        holder.name.text = item.name
        Glide.with(context).load(Uri.parse(item.images.md)).into(holder.image)
        holder.llyAvatarBoxItem.setOnClickListener {
            onTap(item)
        }
    }

    override fun getItemCount(): Int {
        return itemsFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) itemsFiltered = itemsList else {
                    val filteredList = ArrayList<HeroesModel>()
                    itemsList
                        .filter { (it.name.lowercase().contains(constraint!!)) }
                        .forEach { filteredList.add(it) }
                    itemsFiltered = filteredList

                }
                return FilterResults().apply { values = itemsFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<HeroesModel>
                notifyDataSetChanged()
            }
        }
    }
}