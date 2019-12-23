package com.fs.wegit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.fs.wegit.helper.GlideHelper
import com.fs.wegit.R
import com.fs.wegit.model.RepoItemEntity
import com.fs.wegit.util.TimeUtil
import java.util.*
import kotlin.collections.ArrayList

class RepoListAdapter(private val context: Context) :
    RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    lateinit var dataList: ArrayList<RepoItemEntity>


    lateinit var itemListener: (position: Int) -> Unit
    lateinit var userImgListener: (position: Int) -> Unit
    lateinit var userNameListener: (position: Int) -> Unit


    constructor(context: Context, dataList: ArrayList<RepoItemEntity>) : this(context) {
        this.dataList = dataList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(context).inflate(R.layout.repo_item, parent, false)
        v.setOnClickListener {

        }

        return ViewHolder(v)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        GlideHelper.loadToView(context, dataList[position].owner.avatar_url, holder.userImage)
        holder.userName.text = dataList[position].owner.login
        dataList[position].language?.let {
            holder.point.visibility = View.VISIBLE
            holder.language.text = it
        }

        holder.fullName.text = dataList[position].full_name
        dataList[position].description?.let { holder.repoDescription.text = it }
        holder.startCount.text = dataList[position].stargazers_count.toString()
        holder.branchCount.text = dataList[position].forks_count.toString()
        val time = dataList[position].updated_at
        holder.pushTime.text = simplifyTime(time)

        holder.repoItem.setOnClickListener {
            itemListener(position)
        }

        holder.userImage.setOnClickListener {
            userImgListener(position)
        }


        holder.userName.setOnClickListener {
            userNameListener.invoke(position)
        }

    }

    private fun simplifyTime(time: String): String {

        val calendar = TimeUtil.utc2cst(time)
        val monthNum = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val year = calendar.get(Calendar.YEAR)

        val month = when (monthNum) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> ""
        }

        return "$day $month $year"
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val repoItem: ViewGroup = itemView.findViewById(R.id.repo_item)
        val userImage: ImageView = itemView.findViewById(R.id.user_img)
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val language: TextView = bindView(R.id.language)
        val fullName: TextView = bindView(R.id.full_name)
        val repoDescription: TextView = bindView(R.id.repo_des)
        val startCount: TextView = bindView(R.id.star_count)
        val branchCount: TextView = bindView(R.id.forks_count)
        val pushTime: TextView = bindView(R.id.update_time)
        val point: View = bindView(R.id.language_point)


        private fun <T : View> bindView(@IdRes viewId: Int) = itemView.findViewById<T>(viewId)
    }


}