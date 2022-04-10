package com.example.thecatapimultipart.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapimultipart.R
import com.example.thecatapimultipart.model.Cat
import com.squareup.picasso.Picasso

class CatAdapter(val cats: ArrayList<Cat>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cats, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cat = cats[position]
        if (holder is CatViewHolder) {
            holder.apply {
                Picasso.get().load(cat.url).placeholder(R.drawable.loading).into(catImage)
            }
        }
    }

    override fun getItemCount(): Int = cats.size

    class CatViewHolder(view: View): RecyclerView.ViewHolder(view){
        val catImage = view.findViewById<ImageView>(R.id.image_cat)
    }

}