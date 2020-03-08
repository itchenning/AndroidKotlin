package common.utils.base

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import common.base.CommonSdk
import common.model.GlideRoundTransform

/**
 * Author: Terry
 * Date: 2020-01-10 17:34
 * Comment:
 */
object GlideUtils {

    fun showImage(imageView : ImageView , path : String , corner : Int = 0) {
        var builder = Glide.with(CommonSdk.getApp().applicationContext).load(path)
        if (corner > 0) {
            val options = RequestOptions.bitmapTransform(GlideRoundTransform(corner))
            builder = builder.apply(options)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showImage(imageView : ImageView , res : Int , corner : Int = 0) {
        var builder = Glide.with(CommonSdk.getApp().applicationContext).load(res)
        if (corner > 0) {
            val options = RequestOptions.bitmapTransform(GlideRoundTransform(corner))
            builder = builder.apply(options)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showImage(imageView : ImageView , drawable : Drawable , corner : Int = 0) {
        var builder = Glide.with(CommonSdk.getApp().applicationContext).load(drawable)
        if (corner > 0) {
            val options = RequestOptions.bitmapTransform(GlideRoundTransform(corner))
            builder = builder.apply(options)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showImage(imageView : ImageView , uri : Uri , corner : Int = 0) {
        var builder = Glide.with(CommonSdk.getApp().applicationContext).load(uri)
        if (corner > 0) {
            val options = RequestOptions.bitmapTransform(GlideRoundTransform(corner))
            builder = builder.apply(options)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showCircleImage(imageView : ImageView , path : String) {
        Glide.with(CommonSdk.getApp().applicationContext).load(path).circleCrop().skipMemoryCache(true).into(imageView)
    }

    fun showCircleImage(imageView : ImageView , res : Int) {
        Glide.with(CommonSdk.getApp().applicationContext).load(res).circleCrop().skipMemoryCache(true).into(imageView)
    }

    fun showCircleImage(imageView : ImageView , drawable : Drawable) {
        Glide.with(CommonSdk.getApp().applicationContext).load(drawable).circleCrop().skipMemoryCache(true).into(imageView)
    }

    fun showCircleImage(imageView : ImageView , uri : Uri) {
        Glide.with(CommonSdk.getApp().applicationContext).load(uri).circleCrop().skipMemoryCache(true).into(imageView)
    }

    fun showGif(gifView : ImageView , path : String , corner : Int = 0) {
        var builder = Glide.with(CommonSdk.getApp().applicationContext).asGif().load(path)
        if (corner > 0) {
            val options = RequestOptions.bitmapTransform(GlideRoundTransform(corner))
            builder = builder.apply(options)
        }
        builder.skipMemoryCache(true).into(gifView)
    }

    fun showGif(gifView : ImageView , res : Int , corner : Int = 0) {
        var builder = Glide.with(CommonSdk.getApp().applicationContext).asGif().load(res)
        if (corner > 0) {
            val options = RequestOptions.bitmapTransform(GlideRoundTransform(corner))
            builder = builder.apply(options)
        }
        builder.skipMemoryCache(true).into(gifView)
    }

    fun showGif(gifView : ImageView , drawable : Drawable , corner : Int = 0) {
        var builder = Glide.with(CommonSdk.getApp().applicationContext).asGif().load(drawable)
        if (corner > 0) {
            val options = RequestOptions.bitmapTransform(GlideRoundTransform(corner))
            builder = builder.apply(options)
        }
        builder.skipMemoryCache(true).into(gifView)
    }

    fun showGif(gifView : ImageView , uri : Uri , corner : Int = 0) {
        var builder = Glide.with(CommonSdk.getApp().applicationContext).asGif().load(uri)
        if (corner > 0) {
            val options = RequestOptions.bitmapTransform(GlideRoundTransform(corner))
            builder = builder.apply(options)
        }
        builder.skipMemoryCache(true).into(gifView)
    }

    fun showGifFirstFrame(imageView : ImageView , path : String) {
        val context = CommonSdk.getApp()
        Glide.with(context).load(path).into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(drawable : Drawable , transition : Transition<in Drawable>?) {
                if (drawable is GifDrawable) {
                    val firstFrame = drawable.firstFrame
                    imageView.setImageBitmap(firstFrame)
                }
            }
        })
    }
}