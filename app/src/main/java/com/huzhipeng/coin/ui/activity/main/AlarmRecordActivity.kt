package com.huzhipeng.coin.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import android.support.v4.view.LayoutInflaterFactory
import android.view.InflateException
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.huzhipeng.coin.R

import com.huzhipeng.coin.application.AppConfig
import com.huzhipeng.coin.base.BaseActivity
import com.huzhipeng.coin.ui.activity.main.component.DaggerAlarmRecordComponent
import com.huzhipeng.coin.ui.activity.main.contract.AlarmRecordContract
import com.huzhipeng.coin.ui.activity.main.module.AlarmRecordModule
import com.huzhipeng.coin.ui.activity.main.presenter.AlarmRecordPresenter
import com.huzhipeng.coin.ui.adapter.AlarmRecordAdapter
import com.socks.library.KLog
import kotlinx.android.synthetic.main.activity_alarm_record.*

import javax.inject.Inject;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: $description
 * @date 2019/05/13 12:55:42
 */

class AlarmRecordActivity : BaseActivity(), AlarmRecordContract.View {

    @Inject
    internal lateinit var mPresenter: AlarmRecordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), LayoutInflaterFactory { parent, name, context, attrs ->
            if (name.equals("com.android.internal.view.menu.IconMenuItemView", ignoreCase = true) || name.equals("com.android.internal.view.menu.ActionMenuItemView", ignoreCase = true) || name.equals("android.support.v7.view.menu.ActionMenuItemView", ignoreCase = true)) {
                try {
                    val f = layoutInflater
                    val view = f.createView(name, null, attrs)
                    if (view is TextView) {
                        view.setTextColor(resources.getColor(R.color.mainColor))
                        view.setAllCaps(false)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.clear, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear1 -> {
                AppConfig.instance.daoSsesion.alarmRecordDao.deleteAll()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initView() {
        setContentView(R.layout.activity_alarm_record)
        title.setOnClickListener {
            startActivity(Intent(this, LogActivity::class.java).putExtra("type", "history"))
        }
    }
    override fun initData() {
        title.text = "历史记录"
        var alarmRecordList = AppConfig.instance.daoSsesion.alarmRecordDao.loadAll()
//        alarmRecordList.forEach {
//            KLog.i(it)
//        }
        alarmRecordList.sortByDescending { it.alarmTime }
        recyclerview.adapter = AlarmRecordAdapter(alarmRecordList)
    }

    override fun setupActivityComponent() {
       DaggerAlarmRecordComponent
               .builder()
               .appComponent((application as AppConfig).applicationComponent)
               .alarmRecordModule(AlarmRecordModule(this))
               .build()
               .inject(this)
    }
    override fun setPresenter(presenter: AlarmRecordContract.AlarmRecordContractPresenter) {
            mPresenter = presenter as AlarmRecordPresenter
        }

    override fun showProgressDialog() {
    }

    override fun closeProgressDialog() {
    }

}