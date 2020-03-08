package com.terry.androidkotlin.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.terry.androidkotlin.R
import com.terry.androidkotlin.app.VApp
import com.terry.androidkotlin.helper.ViewHelper
import common.helper.SingleClickListener
import common.utils.base.DipPixelUtils
import common.utils.utilcode.util.IntentUtils

/**
 * Author: Terry
 * bilibili: 码农安小辰
 * Github: https://github.com/itchenning/AndroidKotlin
 * Date: 2020-03-08 16:47
 * Comment:
 */
class ViewAdapter : RecyclerView.Adapter<ViewAdapter.ViewHoler>() , SingleClickListener {
    private val mDatas = ViewHelper.getAll()
    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : ViewHoler {
        val view = View.inflate(VApp.get() , R.layout.item_view , null)
        val params = LinearLayout.LayoutParams(- 1 , DipPixelUtils.dip2px(48F))
        view.layoutParams = params
        return ViewHoler(view)
    }

    override fun getItemCount() : Int {
        return mDatas.size
    }

    override fun onBindViewHolder(holder : ViewHoler , position : Int) {
        val func = mDatas.get(position)
        holder.textView.setText(func.name)
        holder.textView.setTag(position)
        holder.textView.setOnClickListener(this)
    }


    class ViewHoler(view : View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.textView)
    }

    override fun onSingleClick(view : View) {
        val position = view.getTag().toString().toInt()
        val func = mDatas.get(position)
        IntentUtils.startActivity(VApp.get() , func.clazz)
    }
}