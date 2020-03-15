package common.utils.base

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import common.base.CommonSdk

/**
 * Author: Terry
 * Date: 2020-01-10 17:34
 * Comment:
 */
object GlideUtils {

    fun showImage(requestManager : RequestManager , imageView : ImageView , path : String , transform : BitmapTransformation? = null , lowQuality : Boolean = false) {
        var builder = requestManager.load(path)
        if (transform != null) {
            val options = RequestOptions.bitmapTransform(transform)
            builder = builder.apply(options)
        }
        if (lowQuality) {
            builder = builder.format(DecodeFormat.PREFER_RGB_565)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showImage(requestManager : RequestManager , imageView : ImageView , res : Int , transform : BitmapTransformation? = null , lowQuality : Boolean = false) {
        var builder = requestManager.load(res)
        if (transform != null) {
            val options = RequestOptions.bitmapTransform(transform)
            builder = builder.apply(options)
        }
        if (lowQuality) {
            builder = builder.format(DecodeFormat.PREFER_RGB_565)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showImage(requestManager : RequestManager , imageView : ImageView , drawable : Drawable , transform : BitmapTransformation? = null , lowQuality : Boolean = false) {
        var builder = requestManager.load(drawable)
        if (transform != null) {
            val options = RequestOptions.bitmapTransform(transform)
            builder = builder.apply(options)
        }
        if (lowQuality) {
            builder = builder.format(DecodeFormat.PREFER_RGB_565)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showImage(requestManager : RequestManager , imageView : ImageView , uri : Uri , transform : BitmapTransformation? = null , lowQuality : Boolean = false) {
        var builder = requestManager.load(uri)
        if (transform != null) {
            val options = RequestOptions.bitmapTransform(transform)
            builder = builder.apply(options)
        }
        if (lowQuality) {
            builder = builder.format(DecodeFormat.PREFER_RGB_565)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showImage(requestManager : RequestManager , imageView : ImageView , bitmap : Bitmap , transform : BitmapTransformation? = null , lowQuality : Boolean = false) {
        var builder = requestManager.load(bitmap)
        if (transform != null) {
            val options = RequestOptions.bitmapTransform(transform)
            builder = builder.apply(options)
        }
        if (lowQuality) {
            builder = builder.format(DecodeFormat.PREFER_RGB_565)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showGif(requestManager : RequestManager , imageView : ImageView , path : String , transform : BitmapTransformation? = null , lowQuality : Boolean = false) {
        var builder = requestManager.asGif().load(path)
        if (transform != null) {
            val options = RequestOptions.bitmapTransform(transform)
            builder = builder.apply(options)
        }
        if (lowQuality) {
            builder = builder.format(DecodeFormat.PREFER_RGB_565)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showGif(requestManager : RequestManager , imageView : ImageView , res : Int , transform : BitmapTransformation? = null , lowQuality : Boolean = false) {
        var builder = requestManager.asGif().load(res)
        if (transform != null) {
            val options = RequestOptions.bitmapTransform(transform)
            builder = builder.apply(options)
        }
        if (lowQuality) {
            builder = builder.format(DecodeFormat.PREFER_RGB_565)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showGif(requestManager : RequestManager , imageView : ImageView , drawable : Drawable , transform : BitmapTransformation? = null , lowQuality : Boolean = false) {
        var builder = requestManager.asGif().load(drawable)
        if (transform != null) {
            val options = RequestOptions.bitmapTransform(transform)
            builder = builder.apply(options)
        }
        if (lowQuality) {
            builder = builder.format(DecodeFormat.PREFER_RGB_565)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showGif(requestManager : RequestManager , imageView : ImageView , uri : Uri , transform : BitmapTransformation? = null , lowQuality : Boolean = false) {
        var builder = requestManager.asGif().load(uri)
        if (transform != null) {
            val options = RequestOptions.bitmapTransform(transform)
            builder = builder.apply(options)
        }
        if (lowQuality) {
            builder = builder.format(DecodeFormat.PREFER_RGB_565)
        }
        builder.skipMemoryCache(true).into(imageView)
    }

    fun showGif(requestManager : RequestManager , imageView : ImageView , bitmap : Bitmap , transform : BitmapTransformation? = null , lowQuality : Boolean = false) {
        var builder = requestManager.asGif().load(bitmap)
        if (transform != null) {
            val options = RequestOptions.bitmapTransform(transform)
            builder = builder.apply(options)
        }
        if (lowQuality) {
            builder = builder.format(DecodeFormat.PREFER_RGB_565)
        }
        builder.skipMemoryCache(true).into(imageView)
    }


    fun showGifFirstFrame(requestManager : RequestManager , imageView : ImageView , path : String) {
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

    fun clearMemory(glide : Glide) {
        glide.clearMemory()
    }

    fun pauseRequets(requestManager : RequestManager) {
        requestManager.pauseRequests()
    }
}