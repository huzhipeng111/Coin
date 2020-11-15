package com.huzhipeng.coin.ui.activity.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.TextView
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.LayoutInflaterFactory
import com.huzhipeng.coin.R

import com.huzhipeng.coin.application.AppConfig
import com.huzhipeng.coin.base.BaseActivity
import com.huzhipeng.coin.ui.activity.main.component.DaggerLogComponent
import com.huzhipeng.coin.ui.activity.main.contract.LogContract
import com.huzhipeng.coin.ui.activity.main.module.LogModule
import com.huzhipeng.coin.ui.activity.main.presenter.LogPresenter
import com.huzhipeng.coin.ui.adapter.LogAdapter
import com.huzhipeng.coin.utils.LogUtil
import com.pawegio.kandroid.toast
import com.socks.library.KLog
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_log.*
import kotlinx.android.synthetic.main.ease_search_bar.*
import java.util.concurrent.TimeUnit

import javax.inject.Inject;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: $description
 * @date 2019/05/24 21:46:46
 */

class LogActivity : BaseActivity(), LogContract.View, LogUtil.OnLogListener {

    @Inject
    internal lateinit var mPresenter: LogPresenter

    override fun onLog(string: String) {
        runOnUiThread {
            logAdapter?.addData(string)
        }
    }

    var logAdapter : LogAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), LayoutInflaterFactory { parent, name, context, attrs ->
            if (name.equals("com.android.internal.view.menu.IconMenuItemView", ignoreCase = true) || name.equals("com.android.internal.view.menu.ActionMenuItemView", ignoreCase = true) || name.equals("android.support.v7.view.menu.ActionMenuItemView", ignoreCase = true)) {
                try {
                    val f = layoutInflater
                    val view = f.createView(name, null, attrs)
                    if (view is TextView) {
                        view.setTextColor(resources.getColor(R.color.mainColor))
                        view.isAllCaps = false
                    }
                    return@LayoutInflaterFactory view
                } catch (e: InflateException) {
                    e.printStackTrace()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }

            }
            null
        })
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_log)
        LogUtil.onLogListener = this
        title.text = "Log"
        if (intent.getStringExtra("type").equals("history")) {
            query.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    fiter(s.toString())
                    if (s.length > 0) {
                        search_clear.setVisibility(View.VISIBLE)
                    } else {
                        search_clear.setVisibility(View.INVISIBLE)
                        logAdapter?.setNewData(LogUtil.logList)
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun afterTextChanged(s: Editable) {

                }
            })
            search_clear.setOnClickListener(View.OnClickListener {
                query.getText().clear()
                logAdapter?.setNewData(LogUtil.logList)
            })
        }
    }

    fun fiter(key:String)
    {
        var contactListTemp:ArrayList<String> = arrayListOf<String>()
        for (i in LogUtil.logList) {
            if(i.toLowerCase().contains(key))
            {
                contactListTemp.add(i)
            }
        }
        logAdapter?.setNewData(contactListTemp)
    }

    override fun onDestroy() {
        LogUtil.onLogListener = null
        super.onDestroy()
    }
    override fun initData() {
        logAdapter = LogAdapter(arrayListOf())
        if (intent.getStringExtra("type").equals("history")) {
            var list = mutableListOf<String>()
            AppConfig.instance.daoSsesion.alarmRecordDao.loadAll().forEach {
                list.add(it.toString())
            }
            list.reverse()
            logAdapter?.setNewData(list)
            recyclerView.adapter = logAdapter
//            recyclerView.scrollToPosition(logAdapter!!.data.size)
        } else if (intent.getStringExtra("type").equals("vum")) {
            var list = mutableListOf<String>()
            logAdapter?.addData(list)
            recyclerView.adapter = logAdapter
        }

        logAdapter?.setOnItemClickListener { adapter, view, position ->
            val myClipboard: ClipboardManager
            myClipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val myClip: ClipData
            myClip = ClipData.newPlainText("text", logAdapter?.getItem(position)!!)
            myClipboard.setPrimaryClip(myClip)
            toast("copy success")
        }
    }

    override fun setupActivityComponent() {
        DaggerLogComponent
                .builder()
                .appComponent((application as AppConfig).applicationComponent)
                .logModule(LogModule(this))
                .build()
                .inject(this)
    }
    override fun setPresenter(presenter: LogContract.LogContractPresenter) {
        mPresenter = presenter as LogPresenter
    }

    override fun showProgressDialog() {
    }

    override fun closeProgressDialog() {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.clear_log, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear -> {
                LogUtil.logList.clear()
                logAdapter?.setNewData(LogUtil.logList)
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }
}