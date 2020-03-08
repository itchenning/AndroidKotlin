package common.base

import android.app.Activity
import android.os.Bundle
import com.baidu.crabsdk.CrabSDK


/**
 * Author: Terry
 * Date: 2019-05-24 15:51
 * Comment:
 */
abstract class BaseActivity : Activity() {


    override fun onCreate(savedInstanceState : Bundle?) {
        CommonSdk.fixOnCreate(this , savedInstanceState)
        super.onCreate(Bundle())
    }

    override fun onPause() {
        CrabSDK.onPause(this)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        CrabSDK.onResume(this)
    }
}
