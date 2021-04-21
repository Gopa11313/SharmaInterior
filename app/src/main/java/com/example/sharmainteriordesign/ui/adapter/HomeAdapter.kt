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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sharmainteriordesign.R
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.repository.AddFavrepository
import com.example.sharmainteriordesign.ui.model.AddFav
import com.example.sharmainteriordesign.ui.model.Home
import com.example.sharmainteriordesign.ui.model.Product
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeAdapter(
        val listpost:ArrayList<Product>,
        val context: Context): RecyclerView.Adapter<HomeAdapter.HomwviewHolder>() {
    class HomwviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fImage: ImageView;
        val f_area: TextView;
        val f_price: TextView;
        val location: TextView;
        val addFav: ImageView;
        init {
            fImage=view.findViewById(R.id.f_image)
            f_area=view.findViewById(R.id.f_area)
            location=view.findViewById(R.id.location)
            f_price=view.findViewById(R.id.f_price)
            addFav=view.findViewById(R.id.addFav)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomwviewHolder {
        val view= LayoutInflater.from(parent.context)
                .inflate(R.layout.for_home,parent,false)
        return HomeAdapter.HomwviewHolder(view)
    }
    override fun onBindViewHolder(holder: HomwviewHolder, position: Int) {
        val post=listpost[position]
        holder.f_area.text=post.area
        holder.f_price.text=post.price
        holder.location.text=post.location
        val id=post._id
        val imagePath = ServiceBuilder.loadImagePath() +post.image
        if (!post.image.equals("noimg")) {
            Glide.with(context)
                    .load(imagePath)
                    .into(holder.fImage)
        }
        holder.addFav.setOnClickListener() {
            val builder = AlertDialog.Builder(context);
            builder.setMessage("Do you want add this product to Fav.")
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                CoroutineScope(Dispatchers.IO).launch {
                    val repository = AddFavrepository()
                    val response = repository.AddFav(AddFav(userId = ServiceBuilder.id!!, productId = post._id))
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            val snack = Snackbar.make(it, "${response.msg}", Snackbar.LENGTH_SHORT)
                            snack.setAction("Ok") {
                                snack.dismiss()
                            }
                            snack.show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "error occur", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
            builder.setNegativeButton("No") { dialogInterface, which ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
            holder.addFav.setBackgroundColor(Color.RED)
        }
    }
    override fun getItemCount(): Int {
        return listpost.size
    }
}