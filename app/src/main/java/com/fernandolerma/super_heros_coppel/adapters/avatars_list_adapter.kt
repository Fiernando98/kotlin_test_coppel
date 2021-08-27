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
import com.fernandolerma.super_heros_coppel.models.AvatarModel

internal class AvatarsListAdapter(
    private var avatarsList: ArrayList<AvatarModel>,
    private var context: Context,
    private var onTap: (AvatarModel) -> Unit
) :
    RecyclerView.Adapter<AvatarsListAdapter.MyViewHolder>(), Filterable {
    var itemsFiltered: ArrayList<AvatarModel> = avatarsList

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llyAvatarItemList: LinearLayout = view.findViewById(R.id.llyAvatarItemList)
        val name: TextView = view.findViewById(R.id.tvName)
        val image: ImageView = view.findViewById(R.id.ivImage)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.avatar_item_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val avatar = itemsFiltered[position]
        holder.name.text = avatar.getName()
        Glide.with(context).load(Uri.parse(avatar.getImagePath())).into(holder.image)
        holder.llyAvatarItemList.setOnClickListener {
            onTap(avatar)
        }
    }

    override fun getItemCount(): Int {
        return itemsFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) itemsFiltered = avatarsList else {
                    val filteredList = ArrayList<AvatarModel>()
                    avatarsList
                        .filter { (it.getName()?.lowercase()!!.contains(constraint!!)) }
                        .forEach { filteredList.add(it) }
                    itemsFiltered = filteredList

                }
                return FilterResults().apply { values = itemsFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<AvatarModel>
                notifyDataSetChanged()
            }
        }
    }
}