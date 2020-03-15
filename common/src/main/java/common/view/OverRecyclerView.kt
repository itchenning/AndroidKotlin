package common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * Author: Terry
 * Date: 2020-03-13 20:35
 * Comment:
 */
class OverRecyclerView(context : Context , attrs : AttributeSet) : RecyclerView(context , attrs) {
    override fun onFinishInflate() {
        super.onFinishInflate()
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        overScrollMode = View.OVER_SCROLL_NEVER
    }

    override fun setLayoutManager(layout : LayoutManager?) {
        super.setLayoutManager(layout)
        if (layout is LinearLayoutManager) {
            val orientation = layout.orientation
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                OverScrollDecoratorHelper.setUpOverScroll(this , OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
            } else {
                OverScrollDecoratorHelper.setUpOverScroll(this , OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
            }
        }
    }
}