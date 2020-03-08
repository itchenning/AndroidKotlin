package common.utils.base

import android.graphics.Typeface
import common.base.CommonSdk


object FontHelper {
    private var regularFont : Typeface? = null
    private var mediumFont : Typeface? = null

    fun getRegularTypeFace() : Typeface {
        if (regularFont == null) {
            regularFont = Typeface.createFromAsset(CommonSdk.getApp().resources.assets , "Roboto-Regular.ttf")
        }
        return regularFont !!
    }

    fun getMediumTypeFace() : Typeface {
        if (mediumFont == null) {
            mediumFont = Typeface.createFromAsset(CommonSdk.getApp().resources.assets , "Roboto-Medium.ttf")
        }
        return mediumFont !!
    }
}
