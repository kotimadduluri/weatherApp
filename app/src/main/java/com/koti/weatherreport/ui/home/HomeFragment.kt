package com.koti.weatherreport.ui.home

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.koti.weatherreport.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), LocationsListAdapter.ItemClickListner {

    private val viewmodel by viewModels<HomeViewModel>()

    private val locationsAdapter:LocationsListAdapter by lazy {
        LocationsListAdapter(this@HomeFragment)
    }

    private lateinit var noDataHolder:TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        observeLocationUpdates()
     }

    private fun observeLocationUpdates() {
        viewmodel.getLocationBookmarks().observe(viewLifecycleOwner,{
           println("locations===>$it")
            locationsAdapter.submitList(it)
            noDataHolder.visibility=if(it.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    private fun initView(view: View) {
        with(view){
            noDataHolder=findViewById(R.id.nodata)
            findViewById<ImageButton>(R.id.addNewLocation).setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeToPlacePicker())

            }
            findViewById<RecyclerView>(R.id.locationsList).apply {
                adapter=locationsAdapter
            }
        }
    }

    override fun onItemClicked(location: Long?) {
        location?.let {
            findNavController().navigate(HomeFragmentDirections.actionHomeToCitySpace(it))
        }
    }

    override fun deleteBookmark(location: Long?) {
        location?.let {
            viewmodel.deleteLocationBookmarkById(it)
        }
    }
}