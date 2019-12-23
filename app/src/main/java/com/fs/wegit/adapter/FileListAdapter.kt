package com.fs.wegit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fs.wegit.R
import com.fs.wegit.model.FileItemEntity
import kotlin.math.pow

class FileListAdapter(val context: Context, private val fileList: List<FileItemEntity>) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
        var holder: ViewHolder? = null

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.file_list_layout, parent, false)
            holder = ViewHolder()
            holder.fileNameText = view.findViewById(R.id.file_name_tv)
            holder.fileSizeText = view.findViewById(R.id.file_size_tv)
            holder.fileIcon = view.findViewById(R.id.file_img)
            view.tag = holder
        } else {
            holder = convertView?.tag as ViewHolder
        }
        holder.fileNameText.text = fileList[position].name
        holder.fileIcon.let {
            if (fileList[position].type == "dir") {
                it.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_folder))
                holder.fileSizeText.text = ""
            }
            else {
                it.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_file))
                holder.fileSizeText.text = calFileSize(fileList[position].size)
            }
        }

        return view!!
    }

    override fun getItem(position: Int) = fileList[position]

    override fun getItemId(position: Int): Long = position.toLong()
    override fun getCount() = fileList.size

    private fun calFileSize(size: Int): String {

        return when {
            size >= 10.0.pow(6.0) -> "${size / (10.0.pow(6.0))}MB"
            size >= 1000 -> "${(size / 1000.0).toFloat()}KB"
            else -> "$size B"
        }
    }

    class ViewHolder {
        lateinit var fileNameText: TextView
        lateinit var fileSizeText: TextView
        lateinit var fileIcon: ImageView
    }
}