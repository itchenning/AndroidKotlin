package common.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import com.chillingvan.canvasgl.ICanvasGL
import com.chillingvan.canvasgl.glview.GLView
import common.base.VLog

/**
 * Author: Terry
 * Date: 2019-09-03 11:31
 * Comment: 一个高效的自定义View基类。在子线程画图，画好之后再post到主线程显示。
 *  设计背景：
 *          1.开始用普通的自定义View, 继承View在onDraw里面写的方式，每一次更新UI
 *          都会导致整个屏幕上的所有控件重绘；
 *          2.后来转用SurfaceView来实现，这样会用一个单独的线程来控制绘画我逻辑，
 *          且每次更新时只关心它的窗口的东西，相当于在屏幕上挖一个洞来做。
 *          但是绘图效率也不高，是调用CPU来绘制的；
 *          3.最后用开源项目GLView来做，这个在SurfaceView的基础上，调用了(OpenGl)GPU来绘图，
 *          会比普通的SurfaceView快很多，有时有两倍。
 */
abstract class BaseGlView constructor(context : Context , attrs : AttributeSet) : GLView(context , attrs) , SurfaceHolder.Callback {
    private var mDrawFlag = false
    private var mHolder : SurfaceHolder? = null
    private var mShouldRun : Boolean = false //是否在运行
    private var mThread : DrawThread? = null

    fun start() {
        mShouldRun = true
        mThread = DrawThread(mHolder !!) //创建一个绘图线程
        mThread !!.start()
    }

    fun stopAll() {
        mShouldRun = false
    }

    inner class DrawThread(private val holder : SurfaceHolder) : Thread() {
        override fun run() {
            while (mShouldRun) {
                onDraw()
            }
        }

        private fun onDraw() {
            synchronized(this) {
                if (! mDrawFlag) {
                    return
                }
                try {
                    val canvas = holder.lockCanvas()
                    drawSomeThing(canvas)
                    holder.unlockCanvasAndPost(canvas)
                    Thread.sleep(10)
                    postInvalidate()
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    abstract fun drawSomeThing(canvas : Canvas)

    override fun onGLDraw(canvas : ICanvasGL?) {
        //
    }

    override fun surfaceCreated(holder : SurfaceHolder) {
        this.mHolder = holder
        setZOrderOnTop(false)
        setZOrderMediaOverlay(false)
        synchronized(this) {
            mDrawFlag = true
        }
    }

    override fun surfaceChanged(holder : SurfaceHolder , format : Int , width : Int , height : Int) {
        VLog.i("ccc" , "surfaceChanged")
    }

    override fun surfaceDestroyed(holder : SurfaceHolder) {
        VLog.i("ccc" , "surfaceDestroyed")
        synchronized(this) {
            mDrawFlag = false
        }
    }
}