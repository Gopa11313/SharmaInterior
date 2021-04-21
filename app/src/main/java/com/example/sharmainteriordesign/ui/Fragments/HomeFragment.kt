package com.example.sharmainteriordesign.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteasap.RoomDatabase.db.Db
import com.example.sharmainteriordesign.R
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.repository.ProductRepository
import com.example.sharmainteriordesign.ui.adapter.HomeAdapter
import com.example.sharmainteriordesign.ui.model.Home
import com.example.sharmainteriordesign.ui.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val listFurniture=ArrayList<Home>();
    private lateinit var recyclar: RecyclerView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclar=view.findViewById(R.id.recyclar);
        loadvlaue()

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    private fun loadvlaue(){
        CoroutineScope(Dispatchers.IO).launch{
            val repositrory=ProductRepository()
            val response=repositrory.getallProduct()
            if(response.success==true){
                val list=response.data
                Db.getInstance(requireContext()).getProductDao().dropTable()
                Db.getInstance(requireContext()).getProductDao().AddProdcut(list)
                val alllist=Db.getInstance(requireContext()).getProductDao().getproduct()
                withContext(Main){
                    val adpater= context?.let { HomeAdapter(alllist as ArrayList<Product>, it) }
                    recyclar.setHasFixedSize(true);
                    recyclar.layoutManager =LinearLayoutManager(activity)
                    recyclar.adapter=adpater;
                }
            }
        }
    }
}