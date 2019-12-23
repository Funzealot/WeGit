package com.fs.lib.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T>(val context: Context, var dataList: ArrayList<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val mLayoutInflater = LayoutInflater.from(context)


    class ViewHoder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun <T : View> bindView(viewId: Int) = itemView.findViewById<T>(viewId)

    }
}