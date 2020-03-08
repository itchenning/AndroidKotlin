package common.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import common.utils.base.FontHelper

//通用字体 对应：Roboto Regular
class RegularEditText : AppCompatEditText {

    constructor(context : Context) : super(context) {}

    constructor(context : Context , attrs : AttributeSet) : super(context , attrs) {}

    override fun setTypeface(tf : Typeface?) {
        super.setTypeface(FontHelper.getRegularTypeFace())
    }
}
