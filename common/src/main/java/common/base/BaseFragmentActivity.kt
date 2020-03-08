package common.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.baidu.crabsdk.CrabSDK

/**
 * Author: Terry
 * Date: 2019-05-24 15:51
 * Comment:
 */
abstract class BaseFragmentActivity : FragmentActivity() {

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