package common.feedback

import android.content.Context
import android.content.SharedPreferences

class FeedbackSharedPreference private constructor(context : Context) {
    private val sharedPreferences : SharedPreferences

    var lastShowTime : Long
        get() = sharedPreferences.getLong("rate_last_show_time" , 0)
        set(j) = sharedPreferences.edit().putLong("rate_last_show_time" , j).apply()

    val showCount : Int
        get() = sharedPreferences.getInt("rate_last_show_count" , 0)

    init {
        this.sharedPreferences = context.getSharedPreferences("feed_back" , 0)
    }

    fun hasShow() : Boolean {
        return sharedPreferences.getBoolean("rate_has_show" , false)
    }

    fun setHasShow(hasShow : Boolean) {
        sharedPreferences.edit().putBoolean("rate_has_show" , hasShow).apply()
    }

    fun addShowCount() {
        sharedPreferences.edit().putInt("rate_last_show_count" , showCount + 1).apply()
    }

    companion object {
        private var instance : FeedbackSharedPreference? = null


        fun getInstance(context : Context) : FeedbackSharedPreference {
            try {
                if (instance == null) {
                    instance = FeedbackSharedPreference(context.applicationContext)
                }
                return instance !!
            } finally {
            }
        }
    }
}
