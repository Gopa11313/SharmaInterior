package com.example.sharmainteriordesign.ui.Fragments

import android.app.UiModeManager.MODE_NIGHT_NO
import android.app.UiModeManager.MODE_NIGHT_YES
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteasap.RoomDatabase.db.Db
import com.example.sharmainteriordesign.R
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.repository.AddFavrepository
import com.example.sharmainteriordesign.repository.ProductRepository
import com.example.sharmainteriordesign.ui.adapter.FavAdapter
import com.example.sharmainteriordesign.ui.model.ForFavProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var favRecycle: RecyclerView;

class FavorateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


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
        return inflater.inflate(R.layout.fragment_favorate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favRecycle = view.findViewById(R.id.favRecycle);
        loadvlaue()

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FavorateFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    private fun loadvlaue() {
        var listNotes: List<ForFavProduct>?
        CoroutineScope(Dispatchers.IO).launch {
            //-------- getting noteid---------------//
            val repository = AddFavrepository()
            val response = repository.getallFavProdcut(ServiceBuilder.id!!)
            if (response.success == true) {
                val data = response.data
                var allnoteid: String? = null
                //------drop table-----------//
                Db.getInstance(requireContext()).getFavDao().dropTable()
                for (i in data!!.indices) {
                    allnoteid = data[i].productId
                    //----------getting note from noteid------------/////////
                    val noteRepository = ProductRepository()
                    val noteResponse = noteRepository.getallProduct(allnoteid!!)
                    if (noteResponse.success == true) {
                        //--------insert into table-----------------//
                        Db.getInstance(requireContext()).getFavDao().AddProdcut(noteResponse.data)
                        // listNotes=(noteResponse.data)
                    }
                }
                //----------getting note from roomdatabase--------------//
                val bookmarkedList = Db.getInstance(requireContext()).getFavDao().getproduct()
                withContext(Main) {
                    val adpater = context?.let { FavAdapter(bookmarkedList as ArrayList<ForFavProduct>, it) }
                    favRecycle.setHasFixedSize(true);
                    favRecycle.layoutManager = LinearLayoutManager(activity)
                    favRecycle.adapter = adpater;
                }
            }
        }

    }
}