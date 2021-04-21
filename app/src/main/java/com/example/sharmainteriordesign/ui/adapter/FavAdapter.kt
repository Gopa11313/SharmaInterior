package com.example.sharmainteriordesign.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sharmainteriordesign.R
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.repository.AddFavrepository
import com.example.sharmainteriordesign.ui.model.ForFavProduct
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavAdapter (val listpost:ArrayList<ForFavProduct>,
val context: Context): RecyclerView.Adapter<FavAdapter.FavviewHolder>() {
    class FavviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img:ImageView
        val removeFav:ImageView
        val area:TextView
        val price:TextView
        val location:TextView
        val contact:TextView
        init {
            img=view.findViewById(R.id.img)
            removeFav=view.findViewById(R.id.removeFav)
            area=view.findViewById(R.id.area)
            price=view.findViewById(R.id.price)
            location=view.findViewById(R.id.location)
            contact=view.findViewById(R.id.contact)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavviewHolder {
        val view= LayoutInflater.from(parent.context)
                .inflate(R.layout.favproduct,parent,false)
        return FavAdapter.FavviewHolder(view)
    }

    override fun onBindViewHolder(holder: FavviewHolder, position: Int) {
        val fav = listpost[position]
        holder.area.text="Area:"+fav.area
        holder.price.text="price:"+fav.price
        holder.location.text="location:"+fav.location
        holder.contact.text="Ph:"+fav.phNo
        val imagePath = ServiceBuilder.loadImagePath() + fav.image
        if (!fav.image.equals("noimg")) {
            Glide.with(context)
                    .load(imagePath)
                    .into(holder.img)
        }

        holder.removeFav.setOnClickListener(){
            val builder= AlertDialog.Builder(context);
            builder.setMessage("Do you want remove from fav")
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton("Yes"){dialogInterface,which->
                CoroutineScope(Dispatchers.IO).launch {
                    val repository=AddFavrepository()
                    val response=repository.deleteFavProduct(fav._id!!)
                    if(response.success==true){
                        withContext(Main){
                            listpost.removeAt(position)
                            notifyDataSetChanged()
                            val snack=  Snackbar.make(it,"${response.msg}.  remove from Fav", Snackbar.LENGTH_SHORT)
                            snack.setAction("Ok") {
                                snack.dismiss()
                            }
                            snack.show()

                        }
                    }
                }
            }
            builder.setNegativeButton("No"){
                dialogInterface,which->
            }
            builder.show()
        }
    }

    override fun getItemCount(): Int {
        return listpost.size
    }
}