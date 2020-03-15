package common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * Author: Terry
 * Date: 2020-03-13 20:35
 * Comment:
 */
class OverScrollView(context : Context , attrs : AttributeSet) : ScrollView(context , attrs) {
    override fun onFinishInflate() {
        super.onFinishInflate()
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        overScrollMode = View.OVER_SCROLL_NEVER
        OverScrollDecoratorHelper.setUpOverScroll(this);
    }
}