package com.huzhipeng.coin.base

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.huzhipeng.coin.R
import com.huzhipeng.coin.utils.UIUtils
import com.jaeger.library.StatusBarUtil
import com.socks.library.KLog

/**
 * 作者：Android on 2017/8/1
 * 邮箱：365941593@qq.com
 * 描述：
 */

abstract class BaseActivity : AppCompatActivity(), ActivityDelegate {

    var toolbar: Toolbar? = null
    var needFront = false   //toolBar 是否需要显示在最上面的返还标题栏 true 不显示
    var rootLayout: RelativeLayout? = null
    lateinit var relativeLayout_root: RelativeLayout
    lateinit var view: View
    lateinit var title: TextView
    lateinit var sleepModel : Switch
    val point = Point()

    var inputMethodManager: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
//        initSwipeBackFinish()
        super.onCreate(savedInstanceState)
        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base)
        StatusBarUtil.setColor(this, resources.getColor(R.color.white), 0)
//        StatusBarUtil.setTranslucent(this, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//设置状态栏黑色字体
        }
//        window.navigationBarColor = resources.getColor(R.color.white)
        if (!isTaskRoot) {
            KLog.i("isTaskRoot")
            val intent = intent
            val action = intent.action
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action == Intent.ACTION_MAIN) {
                finish()
                return
            }
        }
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        initToolbar()
        setupActivityComponent()
        initView()
        initData()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        // cancel the notification
    }

    protected fun hideSoftKeyboard() {
        if (currentFocus != null)
            inputMethodManager?.hideSoftInputFromWindow(currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun setContentView(layoutId: Int) {
        setContentView(View.inflate(this, layoutId, null))
    }

    override fun setTitle(title: CharSequence?) {
        this.title.text = title.toString()
    }

    override fun setContentView(view: View) {
        rootLayout = findViewById(R.id.root_layout)
        relativeLayout_root = findViewById(R.id.root_rl)
        if (rootLayout == null) {
            return
        }
        if (needFront) {
            toolbar?.setBackgroundColor(resources.getColor(R.color.color_00000000))
            relativeLayout_root.setBackgroundColor(resources.getColor(R.color.color_00000000))
            val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            this.view = findViewById(R.id.view)
            toolbar = findViewById(R.id.toolbar)
            rootLayout?.addView(view, params)
            toolbar?.bringToFront()
            toolbar?.setVisibility(View.GONE)
        } else {
            val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            params.addRule(RelativeLayout.BELOW, R.id.root_rl)
            rootLayout?.addView(view, params)
            initToolbar()
        }
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        title = toolbar?.findViewById(R.id.title)!!
        title.setOnClickListener {
//            startActivity(Intent(this, LogActivity::class.java))
        }
        relativeLayout_root = findViewById<View>(R.id.root_rl) as RelativeLayout
        sleepModel = relativeLayout_root.findViewById(R.id.sleepModel)

        view = findViewById(R.id.view)
        view.setLayoutParams(RelativeLayout.LayoutParams(UIUtils.getDisplayWidth(this), UIUtils.getStatusBarHeight(this) as Int))
        //        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(UIUtils.getDisplayWidth(this), UIUtils.dip2px(getResources().getDimension(R.dimen.dp_69), this) - (UIUtils.getStatusBarHeight(this)));
        //        toolbar.setLayoutParams(rlp);
        toolbar?.setTitle("")
        if (toolbar != null) {
            setSupportActionBar(toolbar)
        }
    }

    fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.height
            val right = left + v.width
            return if (event.x > left && event.x < right
                    && event.y > top && event.y < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                false
            } else {
                true
            }
        }
        return false
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 初始化view
     */
    protected abstract fun initView()

    /**
     * 初始化dagger2
     */
    protected abstract fun setupActivityComponent()


    override fun destoryContainer() {
        finish()
    }

    override fun getContainerActivity(): BaseActivity {
        return this
    }


    override fun isContainerDead(): Boolean {
        return if (Build.VERSION.SDK_INT > 16) {
            this.isDestroyed
        } else {
            this.isFinishing
        }
    }

    fun setToorBar(isVisitiy: Boolean) {
        if (toolbar != null) {
            if (isVisitiy) {
                toolbar!!.visibility = View.VISIBLE
            } else {
                toolbar!!.visibility = View.GONE
            }
        }
    }

    /*override fun onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding) {
            return
        }
        mSwipeBackHelper.backward()
    }*/


//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        if (ev.action == MotionEvent.ACTION_DOWN) {
//            point.x = ev.rawX.toInt()
//            point.y = ev.rawY.toInt()
//            val v = currentFocus
//            if (isShouldHideInput(v, ev)) {
//
//                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm?.hideSoftInputFromWindow(v!!.windowToken, 0)
//                closeKeyboad()
//            }
//            return super.dispatchTouchEvent(ev)
//        }
//
//        // 必不可少，否则所有的组件都不会有TouchEvent了
//        return if (window.superDispatchTouchEvent(ev)) {
//            true
//        } else onTouchEvent(ev)
//    }

    open fun closeKeyboad() {

    }

}
