package com.fs.wegit.config

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule


@Excludes()
@GlideModule
class MyGlideModule: AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean {
        // 禁用Manifest解析  提升初始化速度
        return false
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {


        // 直接覆写缓存大小
        builder.setMemoryCache(LruResourceCache(10 * 1024 * 1024))
        // 外部磁盘缓存
        // builder.setDiskCache(DiskLruCacheFactory(Environment.getExternalStorageDirectory().absolutePath + "/Glide缓存", 1024 * 1024 * 10))

        builder.setDiskCache(DiskLruCacheFactory(context.externalCacheDir?.absolutePath + "/Glide缓存", 1024 * 1024 * 10))


        // 直接设置bitmap池的大小
        builder.setBitmapPool(LruBitmapPool(1024 * 1024 *10))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {

    }
}
