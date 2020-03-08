package common.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import common.utils.base.FontHelper


//加粗字体 对应：Roboto Medium
class MediumTextView : AppCompatTextView {

    constructor(context : Context) : super(context) {}

    constructor(context : Context , attrs : AttributeSet) : super(context , attrs) {}

    override fun setTypeface(tf : Typeface?) {
        super.setTypeface(FontHelper.getMediumTypeFace())
    }
}
