package com.fs.lib.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.fs.lib.R
import com.fs.lib.state.Status


class StatusLayoutManager(private val builder: Builder) {


    private var loadingLayoutId = 0
    private var errorLayoutId = 0
    private var emptyLayoutId = 0

    private var loadingView: View? = null
    private var errorView: View? = null
    private var emptyView: View? = null
    private lateinit var contentView: View
    private lateinit var parentView: ViewGroup
    private lateinit var currentView: ViewGroup

    val context: Context by lazy { contentView.context }

    init {
        this.contentView = builder.contentView
        this.loadingLayoutId = builder.loadingLayoutId
        this.errorLayoutId = builder.errorLayoutId
        this.emptyLayoutId = builder.emptyLayoutId
        this.currentView = builder.contentView
    }


    private fun inflate(@LayoutRes layout: Int) = LayoutInflater.from(context).inflate(layout, null)


    private fun showLoadingLayout() {

        if (loadingView == null) {
            loadingView = inflate(loadingLayoutId)
            builder.initLoadingLayout?.invoke(loadingView)
        }

        replaceLayout(loadingView)
    }

    private fun showErrorLayout() {

        if (errorView == null) {
            errorView = inflate(errorLayoutId)
            builder.initErrorLayout?.invoke(errorView)
        }
        replaceLayout(errorView)
    }

    private fun showContentLayout() {

        replaceLayout(contentView)
    }

    fun setStatus(status: Status) {

        when (status) {

            Status.ERROR -> {
                showErrorLayout()
            }
            Status.EMPTY -> {
            }
            Status.LOADING -> {
                showLoadingLayout()
            }

            Status.SUCCESS -> {
                showContentLayout()
            }
        }
    }


    class Builder(val contentView: ViewGroup) {

        internal var loadingLayoutId = R.layout.status_loading
        internal var errorLayoutId = R.layout.status_error
        internal var emptyLayoutId = R.layout.status_empty


        internal var initLoadingLayout: ((View?) -> Unit)? = null
        internal var initErrorLayout: ((View?) -> Unit)? = null
        internal var initEmptyLayout: ((View?) -> Unit)? = null


        fun loadingLayout(@LayoutRes loadingLayoutId: Int, block: (View?) -> Unit): Builder {

            this.loadingLayoutId = loadingLayoutId
            this.initLoadingLayout = block
            return this
        }

        fun errorLayout(@LayoutRes errorLayoutId: Int, block: (View?) -> Unit): Builder {

            this.errorLayoutId = errorLayoutId
            this.initErrorLayout = block
            return this
        }

        fun emptyLayout(@LayoutRes emptyLayoutId: Int, block: (View?) -> Unit): Builder {


            this.emptyLayoutId = emptyLayoutId
            this.initEmptyLayout = block
            return this
        }

        fun setLoadingLayout(@LayoutRes loadingLayoutId: Int): Builder {
            this.loadingLayoutId = loadingLayoutId

            return this
        }

        fun setErrorLayout(@LayoutRes errorLayoutId: Int): Builder {
            this.errorLayoutId = errorLayoutId
            return this
        }

        fun setEmptyLayout(@LayoutRes emptyLayoutId: Int): Builder {
            this.emptyLayoutId = emptyLayoutId
            return this
        }

        fun build(): StatusLayoutManager {
            return StatusLayoutManager(this)
        }
    }


    private fun replaceLayout(view: View?) {

        if (view == currentView)
            return

        parentView = currentView.parent as ViewGroup
        val index = parentView.indexOfChild(currentView)
        val param = currentView.layoutParams
        parentView.removeView(currentView)
        currentView = view as ViewGroup
        parentView.addView(view, index, param)
    }
}