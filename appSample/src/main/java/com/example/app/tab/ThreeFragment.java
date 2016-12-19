/**
 *
 */
package com.example.app.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;
import com.jingyu.utils.application.BFragment;

public class ThreeFragment extends BFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 设置布局
		return init(inflater, R.layout.tab_fragment_three);
	}

}