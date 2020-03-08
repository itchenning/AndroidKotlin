package common.feedback


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import common.base.BaseActivity
import common.base.R

class FeedbackActivity : BaseActivity() , View.OnClickListener {
    private var etContent : EditText? = null
    private var etContact : EditText? = null

    internal inner class ContentOnFocusListener : View.OnFocusChangeListener {

        override fun onFocusChange(view : View , z : Boolean) {
            val findViewById = findViewById<View>(R.id.imageViewFocused)
            val findViewById2 = findViewById<View>(R.id.imageViewNotFocused)
            if (findViewById != null && findViewById2 != null) {
                if (z) {
                    findViewById.visibility = View.VISIBLE
                    findViewById2.visibility = View.INVISIBLE
                    return
                }
                findViewById.visibility = View.INVISIBLE
                findViewById2.visibility = View.VISIBLE
            }
        }
    }

    internal inner class ContactOnFocusListener : View.OnFocusChangeListener {

        override fun onFocusChange(view : View , z : Boolean) {
            val findViewById = findViewById<View>(R.id.imageViewEmailFocused)
            val findViewById2 = findViewById<View>(R.id.imageViewEmailNotFocused)
            if (findViewById != null && findViewById2 != null) {
                if (z) {
                    findViewById.visibility = View.VISIBLE
                    findViewById2.visibility = View.INVISIBLE
                    return
                }
                findViewById.visibility = View.INVISIBLE
                findViewById2.visibility = View.VISIBLE
            }
        }
    }

    internal fun init() {
        initTitleBar(window.decorView , R.id.titlebar , R.string.feedback_title)
        val btnSubmitEnable = findViewById<View>(R.id.feedback_submitEnable) as Button
        val btnSubmitDisable = findViewById<View>(R.id.feedback_submitDisable) as Button
        btnSubmitEnable.setOnClickListener(this)
        btnSubmitDisable.setOnClickListener(this)
        btnSubmitEnable.isEnabled = false
        btnSubmitEnable.visibility = View.INVISIBLE
        btnSubmitDisable.visibility = View.VISIBLE
        val layoutParams = btnSubmitEnable.layoutParams
        layoutParams.width = Utils.getWidth(applicationContext) - Utils.dp2Pix(applicationContext , 24.0f) * 2 + Utils.dp2Pix(applicationContext , 38.0f)
        btnSubmitEnable.layoutParams = layoutParams
        this.etContact = findViewById<View>(R.id.feedback_contact) as EditText
        this.etContact !!.inputType = 32
        this.etContent = findViewById<View>(R.id.feedback_content) as EditText
        if (this.etContent !!.text != null && this.etContent !!.text.length > 2) {
            btnSubmitEnable.isEnabled = true
            btnSubmitEnable.visibility = View.VISIBLE
            btnSubmitDisable.visibility = View.INVISIBLE
        }
        this.etContent !!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(editable : Editable) {
                if (etContent !!.text == null || etContent !!.text.length <= 2 || etContact !!.text == null || etContact !!.text.length <= 0) {
                    btnSubmitEnable.isEnabled = false
                    btnSubmitEnable.visibility = View.INVISIBLE
                    btnSubmitDisable.visibility = View.VISIBLE
                    return
                }
                btnSubmitEnable.isEnabled = true
                btnSubmitEnable.visibility = View.VISIBLE
                btnSubmitDisable.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(charSequence : CharSequence , i : Int , i2 : Int , i3 : Int) {}

            override fun onTextChanged(charSequence : CharSequence , i : Int , i2 : Int , i3 : Int) {}
        })
        this.etContent !!.clearFocus()
        this.etContent !!.onFocusChangeListener = ContentOnFocusListener()
        this.etContact !!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable : Editable) {
                if (etContent !!.text == null || etContent !!.text.length <= 2 || etContact !!.text == null || etContact !!.text.length <= 0) {
                    btnSubmitEnable.isEnabled = false
                    btnSubmitEnable.visibility = View.INVISIBLE
                    btnSubmitDisable.visibility = View.VISIBLE
                    return
                }
                btnSubmitEnable.isEnabled = true
                btnSubmitEnable.visibility = View.VISIBLE
                btnSubmitDisable.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(charSequence : CharSequence , i : Int , i2 : Int , i3 : Int) {}

            override fun onTextChanged(charSequence : CharSequence , i : Int , i2 : Int , i3 : Int) {}
        })
        this.etContact !!.clearFocus()
        this.etContact !!.onFocusChangeListener = ContactOnFocusListener()
    }

    fun initTitleBar(view : View , titleBarId : Int , titleStrId : Int) {
        val findViewById = view.findViewById<View>(titleBarId)
        val imageView = findViewById.findViewById<View>(R.id.logo) as ImageView
        if (titleStrId != - 1) {
            val textView = findViewById.findViewById<View>(R.id.title_text) as TextView
            textView.setText(titleStrId)
            textView.visibility = View.VISIBLE
        }
        imageView.setOnClickListener { finish() }
        imageView.isFocusable = true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onClick(view : View) {
        val obj : String
        val obj2 : String
        when (view.id) {
            R.id.feedback_submitDisable /*2131230834*/ -> {
                obj = this.etContent !!.text.toString()
                obj2 = this.etContact !!.text.toString()
                if (obj.length <= 2 && obj2.length > 0) {
                    Toast.makeText(this , resources.getString(R.string.no_feedback_content_input) , LENGTH_SHORT).show()
                    return
                } else if (obj.length <= 2 || obj2.length > 0) {
                    Toast.makeText(this , resources.getString(R.string.no_feedback_content_input) , LENGTH_SHORT).show()
                    return
                } else {
                    Toast.makeText(this , resources.getString(R.string.no_feedback_email_input) , LENGTH_SHORT).show()
                    return
                }
            }
            R.id.feedback_submitEnable /*2131230835*/ -> {
                obj = this.etContent !!.text.toString()
                obj2 = this.etContact !!.text.toString()
                if (obj.length > 2 && obj2.length > 0) {
                    val stringBuilder = StringBuilder()
                    stringBuilder.append(obj)
                    stringBuilder.append(" ")
                    stringBuilder.append("contact:")
                    stringBuilder.append(obj2)
                }
                Toast.makeText(this , resources.getString(R.string.post_feedback_success) , LENGTH_SHORT).show()
                finish()
                return
            }
            else -> return
        }
    }

    override fun onCreate(bundle : Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.fl_activity_feedback)
        init()
    }
}