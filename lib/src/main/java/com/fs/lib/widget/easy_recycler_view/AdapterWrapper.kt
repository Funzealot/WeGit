package com.fs.lib.widget.easy_recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView

class AdapterWrapper(val context: Context, private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val baseItemTypeHeader = 100000
    private val baseItemTypeFooter = 200000

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mHeaderViews = SparseArrayCompat<View>()
    private val mFooterViews = SparseArrayCompat<View>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        var contentView = mHeaderViews.get(viewType)
        if (contentView != null)
            return ViewHolder(contentView)

        contentView = mFooterViews.get(viewType)
        if (contentView != null) {
            return ViewHolder(contentView)
        }

        return adapter.onCreateViewHolder(parent, viewType)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int = adapter.itemCount + getHeaderCount() + getFooterCount()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (isHeaderOrFooter(position)) return
        adapter.onBindViewHolder(holder, position - getHeaderCount())
    }


    override fun getItemViewType(position: Int): Int {


        if (isHeader(position)) {
            return mHeaderViews.keyAt(position)
        } else if (isFooter(position)) {
            return mFooterViews.keyAt(position - (getHeaderCount()) - adapter.itemCount)
        }
        return adapter.getItemViewType(position - (getHeaderCount()))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        adapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        adapter.onViewAttachedToWindow(holder)
    }


    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (!isHeaderOrFooter(holder)) adapter.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        if (!isHeaderOrFooter(holder)) return adapter.onFailedToRecycleView(holder)
        return false
    }

    override fun getItemId(position: Int): Long {

        if (isHeaderOrFooter(position))
            return (-position - 1).toLong()

        return adapter.getItemId(position - (getHeaderCount()))
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        if (!isHeaderOrFooter(holder)) adapter.onViewDetachedFromWindow(holder)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        adapter.onDetachedFromRecyclerView(recyclerView)
    }

    private fun isHeaderOrFooter(holder: RecyclerView.ViewHolder): Boolean {
        if (holder is ViewHolder) return true
        return isHeaderOrFooter(holder.adapterPosition)
    }

    val isHeader: (Int) -> Boolean = { position -> position >= 0 && position < getHeaderCount() }
    val isFooter: (Int) -> Boolean = { position -> position >= adapter.itemCount + getHeaderCount() }
    val isHeaderOrFooter: (Int) -> Boolean = { position -> isHeader(position) || isFooter(position) }


    fun getOriginAdapter() = adapter
    fun getHeaderCount() = mHeaderViews.size()
    fun getFooterCount() = mFooterViews.size()

    fun addHeaderView(view: View) = mHeaderViews.put(getHeaderCount()  + baseItemTypeHeader, view)

    fun addFooterView(view: View) = mFooterViews.put((getFooterCount()) + baseItemTypeFooter, view)

    fun addHeaderAndNotify(view: View) {
        mHeaderViews.put(getHeaderCount() + baseItemTypeHeader, view)
        notifyItemInserted(getHeaderCount() - 1)
    }

    fun removeHeaderViewAndNotify(view: View?) {

        val index = mHeaderViews.indexOfValue(view)
        if (index == -1) return
        mHeaderViews.remove(index)
        notifyItemRemoved(index)
    }

    fun addFooterViewAndNotify(view: View) {
        mFooterViews.put((getFooterCount()) + baseItemTypeFooter, view)
        notifyItemInserted(getHeaderCount()  + adapter.itemCount + (getFooterCount()) - 1)
    }


    fun removeFooterViewAndNotify(view: View) {

        val index = mFooterViews.indexOfValue(view)
        if (index == -1) return
        mFooterViews.removeAt(index)
        notifyItemRemoved(getHeaderCount()  + adapter.itemCount + index)
    }
}

