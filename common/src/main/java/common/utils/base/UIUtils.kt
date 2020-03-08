package common.utils.base

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import common.base.R

/**
 * Author: chenning
 * Date: 2017/9/1 11:26
 * Comment: UI相关的工具类。
 */

object UIUtils {

    fun setCommonStyle(activity : Activity) {
        setTransparentStatusBar(activity)
        setWhiteStatusBarTextColor(activity , true)
        setTransparentNavigationBar(activity)
    }

    //始终hide
    fun hideNavBarAlways(activity : Activity) {
        val params = activity.getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE;
        activity.getWindow().setAttributes(params);
    }

    //第一次hide,滑动后出现。
    fun hideNavBarFirst(activity : Activity) {
        val params = activity.getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        activity.getWindow().setAttributes(params);
    }

    //设置状态栏文字的color:白或黑。
    fun setWhiteStatusBarTextColor(activity : Activity , isWhite : Boolean) {
        if (! isWhite) {
            activity.window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        } else {
            activity.window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
    }

    private fun setDecorViewColor(activity : Activity , isWhite : Boolean) {
        var color = 0
        if (isWhite) {
            color = Color.parseColor("#000000")
        } else {
            color = Color.parseColor("#FFFFFF")
        }
        activity.window.decorView.setBackgroundColor(color)
    }

    fun setTransparentStatusBar(activity : Activity) {
        setStatusBarColor(activity , Color.parseColor("#00000000"))
    }

    fun setTransparentNavigationBar(activity : Activity) {
        setNavigationBarColor(activity , Color.parseColor("#00FFFFFF"))
    }

    private fun setNavigationBarColor(activity : Activity , color : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.navigationBarColor = color
        }
    }

    private fun setStatusBarColor(activity : Activity , color : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.statusBarColor = color
        }
    }

    //setBackground for press status
    fun setBgPressedSelector(context : Context , view : View , normal : Int , sel : Int) {
        val drawable = StateListDrawable()
        val selected = context.resources.getDrawable(sel)
        val unSelected = context.resources.getDrawable(normal)
        drawable.addState(intArrayOf(android.R.attr.state_pressed) , selected)
        drawable.addState(intArrayOf(- android.R.attr.state_pressed) , unSelected)
        view.background = drawable
    }

    //setBackground for select status
    fun setBgSelectedSelector(context : Context , view : View , normal : Int , sel : Int) {
        val drawable = StateListDrawable()
        val selected = context.resources.getDrawable(sel)
        val unSelected = context.resources.getDrawable(normal)
        drawable.addState(intArrayOf(android.R.attr.state_selected) , selected)
        drawable.addState(intArrayOf(- android.R.attr.state_selected) , unSelected)
        view.background = drawable
    }

    fun setBgSelectedAndPressedSelector(context : Context , view : View , normal : Int , sel : Int) {
        val drawable = StateListDrawable()
        val selected = context.resources.getDrawable(sel)
        val unSelected = context.resources.getDrawable(normal)
        drawable.addState(intArrayOf(android.R.attr.state_selected) , selected)
        drawable.addState(intArrayOf(android.R.attr.state_pressed) , selected)
        drawable.addState(intArrayOf(- android.R.attr.state_selected) , unSelected)
        view.background = drawable
    }

    fun setImageSelctedSelector(context : Context , iv : ImageView , normal : Int , sel : Int) {
        val drawable = StateListDrawable()
        val selected = context.resources.getDrawable(sel)
        val unSelected = context.resources.getDrawable(normal)
        drawable.addState(intArrayOf(android.R.attr.state_selected) , selected)
        drawable.addState(intArrayOf(- android.R.attr.state_selected) , unSelected)
        iv.setImageDrawable(drawable)
    }

    //setImageDrawable for click status
    fun setDrawableClickSelector(context : Context , iv : ImageView , normal : Int , sel : Int) {
        val drawable = StateListDrawable()
        val selected = context.resources.getDrawable(sel)
        val unSelected = context.resources.getDrawable(normal)
        drawable.addState(intArrayOf(android.R.attr.state_pressed) , selected)
        drawable.addState(intArrayOf(- android.R.attr.state_pressed) , unSelected)
        iv.setImageDrawable(drawable)
    }

    //setImageDrawable for select status
    fun setDrawableSelectedSelector(context : Context , iv : ImageView , normal : Int , sel : Int) {
        val drawable = StateListDrawable()
        val selected = context.resources.getDrawable(sel)
        val unSelected = context.resources.getDrawable(normal)
        drawable.addState(intArrayOf(android.R.attr.state_selected) , selected)
        drawable.addState(intArrayOf(- android.R.attr.state_selected) , unSelected)
        iv.setImageDrawable(drawable)
    }

    //设置字color
    fun setTextColorSelector(context : Context , tv : TextView , normal : Int , sel : Int) {
        val selected = context.resources.getColor(sel)
        val unSelected = context.resources.getColor(normal)
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_selected)
        states[1] = intArrayOf(- android.R.attr.state_selected)
        val drawable = ColorStateList(states , intArrayOf(selected , unSelected))
        tv.setTextColor(drawable)
    }

    fun setTextColorSelector(tv : TextView , nor : String , sel : String) {
        val unSelected = Color.parseColor(nor)
        val selected = Color.parseColor(sel)
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_selected)
        states[1] = intArrayOf(- android.R.attr.state_selected)
        val drawable = ColorStateList(states , intArrayOf(selected , unSelected))
        tv.setTextColor(drawable)
    }

    //设置字color
    fun setTextColorClickSelector(context : Context , tv : TextView , normal : Int , sel : Int) {
        val selected = context.resources.getColor(sel)
        val unSelected = context.resources.getColor(normal)
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_pressed)
        states[1] = intArrayOf(- android.R.attr.state_pressed)
        val drawable = ColorStateList(states , intArrayOf(selected , unSelected))
        tv.setTextColor(drawable)
    }

    fun setTextColor(root : View , id : Int , color : String) {
        val textView = root.findViewById<TextView>(id)
        textView.setTextColor(Color.parseColor(color))
    }

    fun setClickListener(listener : View.OnClickListener , vararg views : View) {
        for (view in views) {
            view.setOnClickListener(listener)
        }
    }

    fun editTextApplySkin(edit : EditText , res : Int , lightColor : Int) {
        try {
            val f = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            f.isAccessible = true
            f.set(edit , res)
            edit.highlightColor = lightColor
        } catch (e : Exception) {
        }
    }

    fun applyEditStyle(editText : EditText) {
        try {
            val f = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            f.isAccessible = true
            f.set(editText , R.drawable.shape_input_pointer_day)
            editText.highlightColor = Color.parseColor("#0080FF")
        } catch (e : Exception) {
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setClipViewCornerRadius(view : View , radius : Int) {
        view.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view : View , outline : Outline) {
                outline.setRoundRect(0 , 0 , view.width , view.height , radius.toFloat())
            }
        }
        view.clipToOutline = true
    }
}