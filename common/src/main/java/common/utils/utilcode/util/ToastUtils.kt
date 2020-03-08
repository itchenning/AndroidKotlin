package common.utils.utilcode.util

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.app.NotificationManagerCompat
import common.base.CommonSdk
import common.utils.base.DeviceUtils
import common.utils.utilcode.util.Utils.OnActivityDestroyedListener

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : utils about toast
</pre> *
 */
class ToastUtils private constructor() {
    internal object ToastFactory {
        fun makeToast(context : Context , text : CharSequence , duration : Int) : IToast {
            return if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                SystemToast(makeNormalToast(context , text , duration))
            } else ToastWithoutNotification(makeNormalToast(context , text , duration))
        }

        fun newToast(context : Context?) : IToast {
            return if (NotificationManagerCompat.from(context !!).areNotificationsEnabled()) {
                SystemToast(Toast(context))
            } else ToastWithoutNotification(Toast(context))
        }

        private fun makeNormalToast(context : Context , text : CharSequence , duration : Int) : Toast {
            @SuppressLint("ShowToast") val toast = Toast.makeText(context , "" , duration)
            toast.setText(text)
            return toast
        }
    }

    internal class SystemToast(toast : Toast?) : AbsToast(toast) {
        override fun show() {
            mToast !!.show()
        }

        override fun cancel() {
            mToast !!.cancel()
        }

        internal class SafeHandler(private val impl : Handler) : Handler() {
            override fun handleMessage(msg : Message) {
                impl.handleMessage(msg)
            }

            override fun dispatchMessage(msg : Message) {
                try {
                    impl.dispatchMessage(msg)
                } catch (e : Exception) {
                    Log.e("ToastUtils" , e.toString())
                }
            }

        }

        init {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                try {
                    val mTNField = Toast::class.java.getDeclaredField("mTN")
                    mTNField.isAccessible = true
                    val mTN = mTNField[toast]
                    val mTNmHandlerField = mTNField.type.getDeclaredField("mHandler")
                    mTNmHandlerField.isAccessible = true
                    val tnHandler = mTNmHandlerField[mTN] as Handler
                    mTNmHandlerField[mTN] = SafeHandler(tnHandler)
                } catch (ignored : Exception) { /**/
                }
            }
        }
    }

    internal class ToastWithoutNotification(toast : Toast?) : AbsToast(toast) {
        private var mView : View? = null
        private var mWM : WindowManager? = null
        private val mParams = WindowManager.LayoutParams()
        override fun show() {
            Utils.runOnUiThreadDelayed({ realShow() } , 300)
        }

        private fun realShow() {
            if (mToast == null) return
            mView = mToast !!.view
            if (mView == null) return
            val context = mToast !!.view.context
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                mWM = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                mParams.type = WindowManager.LayoutParams.TYPE_TOAST
            } else {
                val topActivityOrApp = Utils.getTopActivityOrApp()
                if (topActivityOrApp !is Activity) {
                    Log.e("ToastUtils" , "Couldn't get top Activity.")
                    return
                }
                val topActivity = topActivityOrApp
                if (topActivity.isFinishing || topActivity.isDestroyed) {
                    Log.e("ToastUtils" , "$topActivity is useless")
                    return
                }
                mWM = topActivity.windowManager
                mParams.type = WindowManager.LayoutParams.LAST_APPLICATION_WINDOW
                Utils.getActivityLifecycle().addOnActivityDestroyedListener(topActivity , LISTENER)
            }
            mParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            mParams.width = WindowManager.LayoutParams.WRAP_CONTENT
            mParams.format = PixelFormat.TRANSLUCENT
            mParams.windowAnimations = R.style.Animation_Toast
            mParams.title = "ToastWithoutNotification"
            mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            mParams.packageName = Utils.getApp().packageName
            mParams.gravity = mToast !!.gravity
            if (mParams.gravity and Gravity.HORIZONTAL_GRAVITY_MASK == Gravity.FILL_HORIZONTAL) {
                mParams.horizontalWeight = 1.0f
            }
            if (mParams.gravity and Gravity.VERTICAL_GRAVITY_MASK == Gravity.FILL_VERTICAL) {
                mParams.verticalWeight = 1.0f
            }
            mParams.x = mToast !!.xOffset
            mParams.y = mToast !!.yOffset
            mParams.horizontalMargin = mToast !!.horizontalMargin
            mParams.verticalMargin = mToast !!.verticalMargin
            try {
                if (mWM != null) {
                    mWM !!.addView(mView , mParams)
                }
            } catch (ignored : Exception) { /**/
            }
            Utils.runOnUiThreadDelayed({ cancel() } , if (mToast !!.duration == Toast.LENGTH_SHORT) 2000 else 3500.toLong())
        }

        override fun cancel() {
            try {
                if (mWM != null) {
                    mWM !!.removeViewImmediate(mView)
                }
            } catch (ignored : Exception) { /**/
            }
            mView = null
            mWM = null
            mToast = null
        }

        companion object {
            private val LISTENER = OnActivityDestroyedListener { activity ->
                if (iToast == null) return@OnActivityDestroyedListener
                activity.window.decorView.visibility = View.GONE
                iToast !!.cancel()
            }
        }
    }

    internal abstract class AbsToast(var mToast : Toast?) : IToast {

        override var view : View?
            get() = mToast !!.view
            set(view) {
                mToast !!.view = view
            }

        override fun setDuration(duration : Int) {
            mToast !!.duration = duration
        }

        override fun setGravity(gravity : Int , xOffset : Int , yOffset : Int) {
            mToast !!.setGravity(gravity , xOffset , yOffset)
        }

        override fun setText(resId : Int) {
            mToast !!.setText(resId)
        }

        override fun setText(s : CharSequence?) {
            mToast !!.setText(s)
        }

    }

    internal interface IToast {
        fun show()
        fun cancel()
        var view : View?
        fun setDuration(duration : Int)
        fun setGravity(gravity : Int , xOffset : Int , yOffset : Int)
        fun setText(@StringRes resId : Int)
        fun setText(s : CharSequence?)
    }

    companion object {
        private const val COLOR_DEFAULT = - 0x1000001
        private const val NULL = "null"
        private var iToast : IToast? = null
        private var sGravity = - 1
        private var sXOffset = - 1
        private var sYOffset = - 1
        private var sBgColor = COLOR_DEFAULT
        private var sBgResource = - 1
        private var sMsgColor = COLOR_DEFAULT
        private var sMsgTextSize = - 1
        /**
         * Set the gravity.
         *
         * @param gravity The gravity.
         * @param xOffset X-axis offset, in pixel.
         * @param yOffset Y-axis offset, in pixel.
         */
        fun setGravity(gravity : Int , xOffset : Int , yOffset : Int) {
            sGravity = gravity
            sXOffset = xOffset
            sYOffset = yOffset
        }

        fun showToast(context : Context , content : String) {
            ThreadUtils.uiExcude(Runnable {
                val toast = Toast.makeText(context , content , Toast.LENGTH_LONG)
                val height = DeviceUtils.getScreenHeight(context)
                toast.setGravity(Gravity.TOP , 0 , (height / 3 * 1.8F).toInt());
                toast.show()
            })
        }

        fun showToast(context : Context , res : Int) {
            ThreadUtils.uiExcude(Runnable {
                val content = CommonSdk.getApp().getResources()?.getString(res)
                val toast = Toast.makeText(context , content , Toast.LENGTH_LONG)
                val height = DeviceUtils.getScreenHeight(context)
                toast.setGravity(Gravity.TOP , 0 , (height / 3 * 1.8F).toInt());
                toast.show()
            })
        }
        fun showToastAtCenter(content : String) {
            ThreadUtils.uiExcude({
                val context = Utils.getApp()
                val toast = Toast.makeText(context , content , Toast.LENGTH_LONG)
                val height = DeviceUtils.getScreenHeight(context)
                toast.setGravity(Gravity.TOP , 0 , (height / 3 * 1.8F).toInt());
                toast.show()
            })
        }

        fun showToastAtCenter(res : Int) {
            ThreadUtils.uiExcude({
                val context = Utils.getApp()
                val content = CommonSdk.getApp().getResources()?.getString(res)
                val toast = Toast.makeText(context , content , Toast.LENGTH_LONG)
                val height = DeviceUtils.getScreenHeight(context)
                toast.setGravity(Gravity.TOP , 0 , (height / 3 * 1.8F).toInt());
                toast.show()
            })
        }

        /**
         * Set the color of background.
         *
         * @param backgroundColor The color of background.
         */
        fun setBgColor(@ColorInt backgroundColor : Int) {
            sBgColor = backgroundColor
        }

        /**
         * Set the resource of background.
         *
         * @param bgResource The resource of background.
         */
        fun setBgResource(@DrawableRes bgResource : Int) {
            sBgResource = bgResource
        }

        /**
         * Set the color of message.
         *
         * @param msgColor The color of message.
         */
        fun setMsgColor(@ColorInt msgColor : Int) {
            sMsgColor = msgColor
        }

        /**
         * Set the text size of message.
         *
         * @param textSize The text size of message.
         */
        fun setMsgTextSize(textSize : Int) {
            sMsgTextSize = textSize
        }

        /**
         * Show the toast for a short period of time.
         *
         * @param text The text.
         */
        fun showShort(text : CharSequence?) {
            show(text ?: NULL , Toast.LENGTH_SHORT)
        }

        /**
         * Show the toast for a short period of time.
         *
         * @param resId The resource id for text.
         */
        fun showShort(@StringRes resId : Int) {
            show(resId , Toast.LENGTH_SHORT)
        }

        /**
         * Show the toast for a short period of time.
         *
         * @param resId The resource id for text.
         * @param args  The args.
         */
        fun showShort(@StringRes resId : Int , vararg args : Any?) {
            show(resId , Toast.LENGTH_SHORT , args)
        }

        /**
         * Show the toast for a short period of time.
         *
         * @param format The format.
         * @param args   The args.
         */
        fun showShort(format : String? , vararg args : Any?) {
            show(format , Toast.LENGTH_SHORT , args)
        }

        /**
         * Show the toast for a long period of time.
         *
         * @param text The text.
         */
        fun showLong(text : CharSequence?) {
            show(text ?: NULL , Toast.LENGTH_LONG)
        }

        /**
         * Show the toast for a long period of time.
         *
         * @param resId The resource id for text.
         */
        fun showLong(@StringRes resId : Int) {
            show(resId , Toast.LENGTH_LONG)
        }

        /**
         * Show the toast for a long period of time.
         *
         * @param resId The resource id for text.
         * @param args  The args.
         */
        fun showLong(@StringRes resId : Int , vararg args : Any?) {
            show(resId , Toast.LENGTH_LONG , args)
        }

        /**
         * Show the toast for a long period of time.
         *
         * @param format The format.
         * @param args   The args.
         */
        fun showLong(format : String? , vararg args : Any?) {
            show(format , Toast.LENGTH_LONG , args)
        }

        /**
         * Show custom toast for a short period of time.
         *
         * @param layoutId ID for an XML layout resource to load.
         */
        fun showCustomShort(@LayoutRes layoutId : Int) : View {
            return showCustomShort(getView(layoutId))
        }

        /**
         * Show custom toast for a short period of time.
         *
         * @param view The view of toast.
         */
        fun showCustomShort(view : View) : View {
            show(view , Toast.LENGTH_SHORT)
            return view
        }

        /**
         * Show custom toast for a long period of time.
         *
         * @param layoutId ID for an XML layout resource to load.
         */
        fun showCustomLong(@LayoutRes layoutId : Int) : View {
            return showCustomLong(getView(layoutId))
        }

        /**
         * Show custom toast for a long period of time.
         *
         * @param view The view of toast.
         */
        fun showCustomLong(view : View) : View {
            show(view , Toast.LENGTH_LONG)
            return view
        }

        /**
         * Cancel the toast.
         */
        fun cancel() {
            if (iToast != null) {
                iToast !!.cancel()
            }
        }

        private fun show(resId : Int , duration : Int) {
            try {
                val text = Utils.getApp().resources.getText(resId)
                show(text , duration)
            } catch (ignore : Exception) {
                show(resId.toString() , duration)
            }
        }

        private fun show(resId : Int , duration : Int , vararg args : Any) {
            try {
                val text = Utils.getApp().resources.getText(resId)
                val format = String.format(text.toString() , *args)
                show(format , duration)
            } catch (ignore : Exception) {
                show(resId.toString() , duration)
            }
        }

        private fun show(format : String? , duration : Int , args : Any) {
            var text : String
            if (format == null) {
                text = NULL
            } else {
                text = String.format(format , args)
                if (text == null) {
                    text = NULL
                }
            }
            show(text , duration)
        }

        private fun show(text : CharSequence , duration : Int) {
            Utils.runOnUiThread(Runnable {
                cancel()
                iToast = ToastFactory.makeToast(Utils.getApp() , text , duration)
                val toastView = iToast !!.view ?: return@Runnable
                val tvMessage = toastView.findViewById<TextView>(R.id.message)
                if (sMsgColor != COLOR_DEFAULT) {
                    tvMessage.setTextColor(sMsgColor)
                }
                if (sMsgTextSize != - 1) {
                    tvMessage.textSize = sMsgTextSize.toFloat()
                }
                if (sGravity != - 1 || sXOffset != - 1 || sYOffset != - 1) {
                    iToast !!.setGravity(sGravity , sXOffset , sYOffset)
                }
                setBg(tvMessage)
                iToast !!.show()
            })
        }

        private fun show(view : View , duration : Int) {
            Utils.runOnUiThread {
                cancel()
                iToast = ToastFactory.newToast(Utils.getApp())
                iToast !!.view = view
                iToast !!.setDuration(duration)
                if (sGravity != - 1 || sXOffset != - 1 || sYOffset != - 1) {
                    iToast !!.setGravity(sGravity , sXOffset , sYOffset)
                }
                setBg()
                iToast !!.show()
            }
        }

        private fun setBg() {
            if (sBgResource != - 1) {
                val toastView = iToast !!.view !!
                toastView.setBackgroundResource(sBgResource)
            } else if (sBgColor != COLOR_DEFAULT) {
                val toastView = iToast !!.view !!
                val background = toastView.background
                if (background != null) {
                    background.colorFilter = PorterDuffColorFilter(sBgColor , PorterDuff.Mode.SRC_IN)
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        toastView.background = ColorDrawable(sBgColor)
                    } else {
                        toastView.setBackgroundDrawable(ColorDrawable(sBgColor))
                    }
                }
            }
        }

        private fun setBg(tvMsg : TextView) {
            if (sBgResource != - 1) {
                val toastView = iToast !!.view !!
                toastView.setBackgroundResource(sBgResource)
                tvMsg.setBackgroundColor(Color.TRANSPARENT)
            } else if (sBgColor != COLOR_DEFAULT) {
                val toastView = iToast !!.view !!
                val tvBg = toastView.background
                val msgBg = tvMsg.background
                if (tvBg != null && msgBg != null) {
                    tvBg.colorFilter = PorterDuffColorFilter(sBgColor , PorterDuff.Mode.SRC_IN)
                    tvMsg.setBackgroundColor(Color.TRANSPARENT)
                } else if (tvBg != null) {
                    tvBg.colorFilter = PorterDuffColorFilter(sBgColor , PorterDuff.Mode.SRC_IN)
                } else if (msgBg != null) {
                    msgBg.colorFilter = PorterDuffColorFilter(sBgColor , PorterDuff.Mode.SRC_IN)
                } else {
                    toastView.setBackgroundColor(sBgColor)
                }
            }
        }

        private fun getView(@LayoutRes layoutId : Int) : View {
            val inflate = Utils.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            return inflate.inflate(layoutId , null)
        }
    }
    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}