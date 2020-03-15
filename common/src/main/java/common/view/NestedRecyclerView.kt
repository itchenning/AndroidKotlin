package common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Author: Terry
 * Date: 2020-03-13 17:42
 * Comment:
 */
class NestedRecyclerView : RecyclerView {
    constructor(context : Context) : super(context)
    constructor(context : Context , attrs : AttributeSet) : super(context , attrs)

    override fun onFinishInflate() {
        super.onFinishInflate()
        isNestedScrollingEnabled = false
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        overScrollMode = View.OVER_SCROLL_NEVER
    }
}