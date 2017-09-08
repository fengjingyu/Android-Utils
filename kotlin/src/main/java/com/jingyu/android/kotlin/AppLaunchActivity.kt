package com.jingyu.android.kotlin

import com.jingyu.android.init.LaunchActivity

class AppLaunchActivity : LaunchActivity() {

    override fun action() {
        AppMainActivity.actionStart(getActivity())
    }

}
