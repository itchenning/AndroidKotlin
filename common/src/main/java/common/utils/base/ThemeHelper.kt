package common.utils.base

import android.app.Activity
import android.graphics.Color
import android.os.Build


/**
 * Author: Terry
 * Date: 2019-11-23 16:04
 * Comment:
 */
object ThemeHelper {

    fun setTheme(activity : Activity , isWhite : Boolean) {
        if (isWhite) {
            setLightTheme(activity)
        } else {
            setDarkTheme(activity)
        }
    }

    /**
     * @see Theme.Builder
     */
    fun setTheme(activity : Activity , theme : Theme) {
        if (theme.alwaysHideNaviBar) {
            UIUtils.hideNavBarAlways(activity)
        }
        if (theme.naviBarIsWhite) {
            setNavigationBarColor(activity , Color.WHITE)
        } else {
            setNavigationBarColor(activity , Color.BLACK)
        }
        UIUtils.setWhiteStatusBarTextColor(activity , theme.statusBarIsWhite)
    }

    private fun setDarkTheme(activity : Activity) {
        UIUtils.setTransparentStatusBar(activity)
        UIUtils.setWhiteStatusBarTextColor(activity , false)
        setNavigationBarColor(activity , Color.BLACK)
    }

    private fun setLightTheme(activity : Activity) {
        UIUtils.setTransparentStatusBar(activity)
        UIUtils.setWhiteStatusBarTextColor(activity , true)
        setNavigationBarColor(activity , Color.WHITE)
    }

    private fun setNavigationBarColor(activity : Activity , color : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.navigationBarColor = color
        }
    }

    data class Theme(val builer : Builder) {
        val statusBarIsWhite = builer.statusBarIsWhite
        val naviBarIsWhite = builer.naviBarIsWhite
        val alwaysHideNaviBar = builer.alwaysHideNaviBar

        class Builder {
            var statusBarIsWhite = true
            var naviBarIsWhite = true
            var alwaysHideNaviBar = false

            fun setWhiteStatusBar(statusBarIsWhite : Boolean) : Builder {
                this.statusBarIsWhite = statusBarIsWhite
                return this
            }

            fun setWhiteNaviBar(naviBarIsWhite : Boolean) : Builder {
                this.naviBarIsWhite = naviBarIsWhite
                return this
            }

            fun alwaysHideNaviBar(alwaysHideNaviBar : Boolean) : Builder {
                this.alwaysHideNaviBar = alwaysHideNaviBar
                return this
            }

            fun build() : Theme {
                return Theme(this)
            }
        }
    }
}