package com.huzhipeng.coin.ui.activity.main

import android.os.Bundle
import com.huzhipeng.coin.R

import com.huzhipeng.coin.application.AppConfig
import com.huzhipeng.coin.base.BaseActivity
import com.huzhipeng.coin.ui.activity.main.component.DaggerCourseComponent
import com.huzhipeng.coin.ui.activity.main.contract.CourseContract
import com.huzhipeng.coin.ui.activity.main.module.CourseModule
import com.huzhipeng.coin.ui.activity.main.presenter.CoursePresenter

import javax.inject.Inject;

/**
 * @author hzp
 * @Package com.huzhipeng.coin.ui.activity.main
 * @Description: $description
 * @date 2019/05/15 10:19:42
 */

class CourseActivity : BaseActivity(), CourseContract.View {

    @Inject
    internal lateinit var mPresenter: CoursePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setContentView(R.layout.activity_course)
    }
    override fun initData() {
        title.text = "韭菜心经"
    }

    override fun setupActivityComponent() {
       DaggerCourseComponent
               .builder()
               .appComponent((application as AppConfig).applicationComponent)
               .courseModule(CourseModule(this))
               .build()
               .inject(this)
    }
    override fun setPresenter(presenter: CourseContract.CourseContractPresenter) {
            mPresenter = presenter as CoursePresenter
        }

    override fun showProgressDialog() {
    }

    override fun closeProgressDialog() {
    }

}