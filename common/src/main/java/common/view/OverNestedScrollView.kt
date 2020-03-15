package common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator
import me.everything.android.ui.overscroll.adapters.IOverScrollDecoratorAdapter


/**
 * Author: Terry
 * Date: 2020-03-13 20:35
 * Comment:
 */
class OverNestedScrollView(context : Context , attrs : AttributeSet) : NestedScrollView(context , attrs) , IOverScrollDecoratorAdapter {

    override fun onFinishInflate() {
        super.onFinishInflate()
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        overScrollMode = View.OVER_SCROLL_NEVER
        setOverStatus()
    }

    private fun setOverStatus() {
        VerticalOverScrollBounceEffectDecorator(this)
    }

    override fun isInAbsoluteStart() : Boolean {
        return ! canScrollVertically(- 1)
    }

    override fun isInAbsoluteEnd() : Boolean {
        return ! canScrollVertically(1)
    }

    override fun getView() : View {
        return this
    }
}