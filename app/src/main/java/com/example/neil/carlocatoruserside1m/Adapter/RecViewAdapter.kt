package com.example.neil.carlocatoruserside1m.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.neil.carlocatoruserside1m.Adapter.ViewHolder.RecViewViewHolder
import com.example.neil.carlocatoruserside1m.R
import com.example.neil.carlocatoruserside1m.Room.UserData
import com.example.neil.carlocatoruserside1m.ViewModels.UserDataViewModel
import com.google.android.material.snackbar.Snackbar

class RecViewAdapter(val userList: MutableList<UserData>,
                     val viewModel: UserDataViewModel,
                     val navController: NavController,
): RecyclerView.Adapter<RecViewViewHolder>() {


    private lateinit var mCTX: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewViewHolder {
        mCTX = parent.context
        val v = LayoutInflater.from(mCTX).inflate(R.layout.listview_element_constraint,parent,false)
        return RecViewViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecViewViewHolder, position: Int) {
        val data = userList[position]


        holder.user_name.text = data.name
        holder.user_email.text = data.email
        holder.user_regid.text = data.regId
        holder.delBtn.setOnClickListener {
            Snackbar.make(holder.itemView,"Do you want to remove user from your watchlist",Snackbar.LENGTH_LONG)
                .setAction("Yes", View.OnClickListener {
                    viewModel.deleteUser(data)
                }).show()
        }

        if(data.logStat){
            holder.statIV.setImageDrawable(ContextCompat.getDrawable(mCTX,R.drawable.circle_green))
            holder.statTV.text = "Online"
        }else{
            holder.statIV.setImageDrawable(ContextCompat.getDrawable(mCTX,R.drawable.circle_red))
            holder.statTV.text = "Offline"
        }

        holder.itemView.setOnClickListener {
            val b = Bundle()
            b.putInt("id",data.id)
            navController.navigate(R.id.action_mainFragment_to_mapsFragment,b)
        }
    }

    override fun getItemCount(): Int = userList.size

    fun updateList(newList: List<UserData>){
        val diffCallback = RVDiffUtillCallback(oldList = userList, newList = newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)


        userList.clear()
        userList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)


    }
}