package com.example.neil.carlocatoruserside1m.Adapter.ViewHolder

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neil.carlocatoruserside1m.R

class RecViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    lateinit var user_name: TextView
    lateinit var user_email: TextView
    lateinit var user_regid: TextView
    lateinit var statTV: TextView
    lateinit var justTxt: TextView
    lateinit var delBtn: ImageButton
    lateinit var statIV: ImageView


    fun RecViewViewHolder(itemView: View) {
        user_name = itemView.findViewById(R.id.name)
        user_email = itemView.findViewById<TextView>(R.id.email)
        user_regid = itemView.findViewById<TextView>(R.id.vehid)
        delBtn = itemView.findViewById(R.id.delBtN)
        statIV = itemView.findViewById(R.id.statusIV)
        statTV = itemView.findViewById<TextView>(R.id.statusTV)
        justTxt = itemView.findViewById<TextView>(R.id.statusjustText)

    }

}