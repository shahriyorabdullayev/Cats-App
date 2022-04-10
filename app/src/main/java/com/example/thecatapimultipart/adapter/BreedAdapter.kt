package com.example.thecatapimultipart.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapimultipart.R
import com.example.thecatapimultipart.model.Breed

class BreedAdapter(val breeds: ArrayList<Breed>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemClick:((breed: Breed) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_breeds, parent, false)
        return BreedViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val breed = breeds[position]
        if (holder is BreedViewHolder) {
            holder.apply {
                tvBreed.text = breed.name

                if (breed.temprament != null){

                    tvTemprament.text = "Temperament: ${breed.temprament}"
                }
                if (breed.description != null) {
                    val desc = "Description: ${breed.description}"
                    val spannableString = SpannableString(desc)
                    val foregroundSpan = ForegroundColorSpan(Color.GREEN)
                    spannableString.setSpan(foregroundSpan, 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    tvDescripyion.text = spannableString
                }
                tvBreed.setOnClickListener {
                    itemClick!!.invoke(breed)
                }

            }
            if (breed.expand) {
                holder.llExpandable.visibility = View.VISIBLE
                holder.btnPlusMinus.setImageResource(R.drawable.ic_minus)
            } else {
                holder.llExpandable.visibility = View.GONE
                holder.btnPlusMinus.setImageResource(R.drawable.ic_plus)
            }
        }
    }

    override fun getItemCount(): Int = breeds.size

    inner class BreedViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tvBreed = view.findViewById<TextView>(R.id.tv_breed_name)
        val btnPlusMinus = view.findViewById<ImageView>(R.id.btn_plus_minus)
        val tvTemprament = view.findViewById<TextView>(R.id.tv_temprament)
        val tvDescripyion = view.findViewById<TextView>(R.id.tv_description)
        val llExpandable = view.findViewById<LinearLayout>(R.id.ll_Expandable)

        init {
            btnPlusMinus.setOnClickListener {
                val breed = breeds[adapterPosition]
                breed.expand = !breed.expand
                notifyItemChanged(adapterPosition)
            }
        }
    }
}