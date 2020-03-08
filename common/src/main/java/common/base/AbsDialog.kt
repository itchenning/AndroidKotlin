package common.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View

/**
 * Author: Terry
 * Date: 2017/10/17 15:28
 * Description: Dialog基类。
 */
abstract class AbsDialog : Dialog {

    protected var mRootView : View? = null
    /**
     * 点击外部时是否消失。
     *
     * @return
     */
    protected abstract val ifCancleWhenTouchOutside : Boolean

    constructor(context : Context) : super(context) {
        init(context)
    }

    constructor(context : Context , themeResId : Int) : super(context , themeResId) {
        init(context)
    }

    protected constructor(context : Context , cancelable : Boolean , cancelListener : DialogInterface.OnCancelListener) : super(context , cancelable , cancelListener) {
        init(context)
    }

    /**
     * 初始化。
     *
     * @param context
     */
    protected fun init(context : Context) {
        setCanceledOnTouchOutside(ifCancleWhenTouchOutside)
        onViewCreate()
    }

    override fun onStart() {
        super.onStart()
        oninitUI()
    }

    /**
     * 创建对话框
     */
    protected fun onViewCreate() {
        mRootView = LayoutInflater.from(context).inflate(onInflateLayoutId() , null)
        setContentView(mRootView !!)
        if (window != null) {
            window.decorView.setBackgroundResource(android.R.color.transparent)
        }
    }

    /**
     * ui初始化。
     */
    protected abstract fun oninitUI()

    /**
     * 填充的View id.
     *
     * @return
     */
    protected abstract fun onInflateLayoutId() : Int

    override fun <T : View?> findViewById(id : Int) : T {
        var view : View? = null
        if (mRootView != null) {
            view = mRootView !!.findViewById(id)
        }
        if (view == null) {
            view = ownerActivity?.findViewById(id)
        }
        return view as T
    }

    /**
     * 显示提示框
     */
    protected fun showDialog() : AbsDialog {
        this.show()
        return this
    }

    override fun onBackPressed() {
        if (canBack()) {
            super.onBackPressed()
        }
    }

    /**
     * 是否可以back.
     *
     * @return
     */
    protected abstract fun canBack() : Boolean

    interface DialogListener {
        fun onCancle()

        fun onConfirm()
    }
}