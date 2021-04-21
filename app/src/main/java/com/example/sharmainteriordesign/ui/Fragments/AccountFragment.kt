package com.example.sharmainteriordesign.ui.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.noteasap.RoomDatabase.db.Db
import com.example.sharmainteriordesign.R
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.repository.ProductRepository
import com.example.sharmainteriordesign.repository.UserRepository
import com.example.sharmainteriordesign.ui.*
import com.example.sharmainteriordesign.ui.adapter.HomeAdapter
import com.example.sharmainteriordesign.ui.adapter.InteriorAdapter
import com.example.sharmainteriordesign.ui.model.OwnProduct
import com.example.sharmainteriordesign.ui.model.Product
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class AccountFragment : Fragment(),SensorEventListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var add: Button
    private lateinit var update: Button
    private lateinit var name: TextView
    private lateinit var circleImageView: CircleImageView
    private lateinit var recyclarForInterior: RecyclerView
    private lateinit var logout: ImageButton
    private lateinit var location: ImageView
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add = view.findViewById(R.id.add);
        update = view.findViewById(R.id.update);
        name = view.findViewById(R.id.textView2);
        logout = view.findViewById(R.id.logout);
        location = view.findViewById(R.id.location);
        circleImageView = view.findViewById(R.id.circleImageView)
        recyclarForInterior= view.findViewById(R.id.recyclarForInterior)
        add.setOnClickListener() {
            startActivity(Intent(context, AddProductActivity::class.java))
        }
        loadvlaue()
        location.setOnClickListener(){
    startActivity(Intent(activity,LocationActivity::class.java))
        }
        CoroutineScope(Dispatchers.IO).launch {
            val repository = UserRepository()
            val response = repository.getMe(ServiceBuilder.id!!)
            if (response.success == true) {
                val data = response.data
                val userData = data?.get(0)
                val image = userData!!.image
                val names = userData.name

                withContext(Main) {
                    name.setText(names)
                    val imagePath = ServiceBuilder.loadImagePath() + image
                    if (!image.equals("noimg")) {
                        Glide.with(requireActivity())
                            .load(imagePath)
                            .into(circleImageView)
                    }
                }
            }
        }
        update.setOnClickListener(){
            startActivity(Intent(activity,EditProfileActivity::class.java))
        }
        circleImageView.setOnClickListener() {
            popup()
        }
        sensorManager = activity?.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        if (!checkSensor())
            return
        else {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        logout.setOnClickListener(){
            val builder= AlertDialog.Builder(requireContext());
            builder.setMessage("Do you want logout")
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton("Yes"){dialogInterface,which->
                val sharedPref =requireActivity().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
                val editor=sharedPref.edit()
                editor.remove("email")
                editor.remove("password")
                editor.remove("_id")
                editor.remove("name")
                        .apply()
                CoroutineScope(Dispatchers.IO).launch{
                    Db.getInstance(requireContext()).getUserDao().logout()
                    withContext(Main){
                        startActivity(Intent(context,LoginActivity::class.java))
                    }
                }
            }
            builder.setNegativeButton("No"){
                dialogInterface,which->
            }
            builder.show()
        }
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun popup() {
        val popupMenu = context?.let { PopupMenu(it, circleImageView) }
        popupMenu?.menuInflater?.inflate(R.menu.pmenu2, popupMenu?.menu)
        popupMenu?.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.update_image -> {
                    requireActivity().run {
                        startActivity(Intent(this, ImageUploadActivity::class.java))
                    }
                    true
                }
                else -> false
            }
        })
        popupMenu?.show()

    }

    private fun loadvlaue(){
        CoroutineScope(Dispatchers.IO).launch{
            val repositrory= ProductRepository()
            val response=repositrory.getinter(ServiceBuilder.id!!)
            if(response.success==true){
                val list=response.data
                Db.getInstance(requireContext()).getOwnproductDao().dropTable()
                Db.getInstance(requireContext()).getOwnproductDao().ownProduct(list)
                val alllist= Db.getInstance(requireContext()).getOwnproductDao().getproduct()
                withContext(Main){
                    val adpater= context?.let { InteriorAdapter(alllist as ArrayList<OwnProduct>, it) }
                    recyclarForInterior.setHasFixedSize(true);
                    recyclarForInterior.layoutManager = GridLayoutManager(activity,2)
                    recyclarForInterior.adapter=adpater;
                }
            }
        }
    }

    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null) {
            flag = false
        }
        return flag
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[0]
        if (values <=4) {
            val sharedPref = requireActivity().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.remove("email")
            editor.remove("password")
                    .apply()
            CoroutineScope(Dispatchers.IO).launch {
                Db.getInstance(requireContext()).getUserDao().logout()
                withContext(Main) {
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}