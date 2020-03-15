package common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import common.utils.base.GlideUtils

/**
 * Author: Terry
 * Date: 2019-05-24 10:39
 * Comment:
 */
abstract class BaseFragment : Fragment() {

    lateinit var requestManager : RequestManager

    protected var mRootView : View? = null

    protected abstract val layoutId : Int

    override fun onCreateView(inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?) : View? {
        mRootView = View.inflate(context , layoutId , null)
        requestManager = Glide.with(this)
        return mRootView
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        initData()
    }

    protected abstract fun initData()

    protected fun findViewById(id : Int) : View? {
        return if (mRootView == null) {
            null
        } else mRootView !!.findViewById(id)
    }

    override fun onDestroy() {
        super.onDestroy()
        GlideUtils.pauseRequets(requestManager)
    }
}
