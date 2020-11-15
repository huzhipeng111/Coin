package com.huzhipeng.coin.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * @ClassName: BaseFragment 
 * @Description: 基础Fragment�?
 * @author wwx
 * @date 2015�?1�?6�?下午1:38:32 
 */
@SuppressLint("ShowToast")
public abstract class BaseFragment extends Fragment {
	/**
	 * 根布局
	 */
	protected View rootView;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		//在自己的应用初始Activity中加入如下两行代码
		if (savedInstanceState != null) {
			//取出保存在savedInstanceState中
		}

		setupFragmentComponent();

	}

	public Activity getContainerActivity() {
		return getActivity();
	}
	
	protected abstract void setupFragmentComponent();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		initView(inflater, container);
		initDataFromLocal();
		return rootView;
	}

	/**
	 * 加载界面控件
	 */
	protected void initView(LayoutInflater inflater, ViewGroup container) {

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}


	/**
	 * 获取本地数据
	 * 
	 * @return
	 */
	protected abstract void initDataFromLocal();

	/**
	 * 启动Activity
	 * 
	 * @param <T>
	 * @param clazz
	 */
	protected <T> void startActivity(Class<T> clazz) {
		Intent intent = new Intent(getActivity(), clazz);
		try {
			startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(getActivity(), "敬请期待！"+ clazz.getSimpleName()
					+ "未注册！", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 启动Activity
	 * 
	 * @param clazz
	 */
	protected <T> void startActivity(Class<T> clazz, Bundle bundle) {
		Intent intent = new Intent(getActivity(), clazz);
		intent.putExtras(bundle);
		try {
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), "敬请期待！"+ clazz.getSimpleName()
					+ "未注册！", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 启动Activity
	 * 
	 * @param clazz
	 */
	protected <T> void startActivity4Result(Class<T> clazz, Bundle bundle,
                                            int requestCode) {
		Intent intent = new Intent(getActivity(), clazz);
		intent.putExtras(bundle);
		try {
			getActivity().startActivityForResult(intent, requestCode);
		} catch (Exception e) {
			Toast.makeText(getActivity(), "敬请期待！"+ clazz.getSimpleName()
					+ "未注册！", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 启动Activity
	 * 
	 * @param clazz
	 */
	protected <T> void startActivity4Result(Class<T> clazz, int requestCode) {
		Intent intent = new Intent(getActivity(), clazz);
		try {
			getActivity().startActivityForResult(intent, requestCode);
		} catch (Exception e) {
			Toast.makeText(getActivity(), "敬请期待！"+ clazz.getSimpleName()
					+ "未注册！", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		try {
			//取出保存在savedInstanceState的值
			if (savedInstanceState != null) {
			}
			super.onViewStateRestored(savedInstanceState);
		} catch (Exception e) {
		}
	}

	/** 是否已经加载过初始数据, 在页面初始数据加载成功之后请置为false */
	protected boolean isLoaded = false;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if(this != null){
			super.setUserVisibleHint(isVisibleToUser);
			if (isVisibleToUser) {
				if(!isLoaded){
				}
			}
		}
	}
}
