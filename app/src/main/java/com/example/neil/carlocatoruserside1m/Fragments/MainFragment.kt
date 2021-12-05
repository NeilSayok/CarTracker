package com.example.neil.carlocatoruserside1m.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neil.carlocatoruserside1m.Adapter.RecViewAdapter
import com.example.neil.carlocatoruserside1m.R
import com.example.neil.carlocatoruserside1m.Room.UserData
import com.example.neil.carlocatoruserside1m.ViewModels.UserDataViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.DividerItemDecoration





class MainFragment: Fragment() {

    private lateinit var v: View

    private lateinit var mainRecView: RecyclerView
    private lateinit var nothingToShowTV: TextView
    private lateinit var addUserFab: FloatingActionButton

    private lateinit var viewModel : UserDataViewModel
    private lateinit var navController: NavController

    private lateinit var adapter: RecViewAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.activity_main, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainRecView = v.findViewById(R.id.mainRecView)
        nothingToShowTV = v.findViewById(R.id.emptyTXT)
        addUserFab = v.findViewById(R.id.adduserBTN)

        viewModel = ViewModelProvider(this)[UserDataViewModel::class.java]
        navController = findNavController()
        adapter = RecViewAdapter( mutableListOf<UserData>(),viewModel,navController)

        mainRecView.adapter = adapter

        val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        mainRecView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            mainRecView.context,
            layoutManager.orientation
        )

        mainRecView.addItemDecoration(dividerItemDecoration)



        viewModel.userData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()){
                nothingToShowTV.visibility = View.VISIBLE
            }else{
                nothingToShowTV.visibility = View.GONE
                adapter.updateList(it)
            }
        })

        addUserFab.setOnClickListener{
            navController.navigate(R.id.action_mainFragment_to_addUserFragment)
        }

        //TODO Setup RV
    }


}