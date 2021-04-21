package com.example.sharmainteriordesign.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sharmainteriordesign.R
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.repository.AddFavrepository
import com.example.sharmainteriordesign.ui.model.AddFav
import com.example.sharmainteriordesign.ui.model.OwnProduct
import com.example.sharmainteriordesign.ui.model.Product
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InteriorAdapter( val listpost:ArrayList<OwnProduct>,
val context: Context): RecyclerView.Adapter<InteriorAdapter.InteriorviewHolder>() {

    class InteriorviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val interio: ImageView
        init {
            interio= view.findViewById(R.id.inter);
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InteriorviewHolder {
        val view= LayoutInflater.from(parent.context)
                .inflate(R.layout.interior,parent,false)
        return InteriorAdapter.InteriorviewHolder(view)
    }
    override fun onBindViewHolder(holder: InteriorviewHolder, position: Int) {
        val post=listpost[position]
        val id=post._id
        val imagePath = ServiceBuilder.loadImagePath() +post.image
        if (!post.image.equals("noimg")) {
            Glide.with(context)
                    .load(imagePath)
                    .into(holder.interio)
        }

    }
    override fun getItemCount(): Int {
        return listpost.size
    }
}