package common.base

import android.app.Activity
import android.os.Bundle
import com.baidu.crabsdk.CrabSDK
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import common.utils.base.GlideUtils


/**
 * Author: Terry
 * Date: 2019-05-24 15:51
 * Comment:
 */
abstract class BaseActivity : Activity() {

    lateinit var requestManager : RequestManager

    override fun onCreate(savedInstanceState : Bundle?) {
        CommonSdk.fixOnCreate(this , savedInstanceState)
        super.onCreate(Bundle())
        requestManager = Glide.with(this)
    }

    override fun onPause() {
        CrabSDK.onPause(this)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        CrabSDK.onResume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        GlideUtils.pauseRequets(requestManager)
        GlideUtils.clearMemory(Glide.get(this))
    }
}
