package common.utils.base

import android.view.ViewGroup
import jp.wasabeef.blurry.Blurry

/**
 * Author: Terry
 * Date: 2019-07-25 16:48
 * Comment:
 */
object BlurHelper {

    private val BLUR_TAG = "blur_once"
    private val BLUR_RADIUS = 20
    private val BLUR_SAMPLING = 5

    //所有子控件一起模糊。
    fun makeBlur(viewGroup : ViewGroup) {
        if (hasBlur(viewGroup)) {
            return
        }
        Blurry.with(viewGroup.context).radius(BLUR_RADIUS).sampling(BLUR_SAMPLING).onto(viewGroup)
        markBlurTag(viewGroup)
    }

    private fun hasBlur(viewGroup : ViewGroup) : Boolean {
        val view = viewGroup.getChildAt(viewGroup.childCount - 1) ?: return false
        var tag = view.tag ?: return false
        tag = tag.toString()
        return if (BLUR_TAG.equals(tag , ignoreCase = true)) {
            true
        } else false
    }

    //标记模糊过的View.
    private fun markBlurTag(viewGroup : ViewGroup) {
        viewGroup.getChildAt(viewGroup.childCount - 1).tag = BLUR_TAG
    }

    //去掉模糊效果。
    fun blurReset(viewGroup : ViewGroup) {
        val view = viewGroup.getChildAt(viewGroup.childCount - 1) ?: return
        val tag = view.tag as String
        if (BLUR_TAG.equals(tag , ignoreCase = true)) {
            viewGroup.removeView(view)
        }
    }
}
