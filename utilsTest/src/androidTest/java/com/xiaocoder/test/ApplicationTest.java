package com.xiaocoder.test;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.xiaocoder.utils.io.XCLog;
import com.xiaocoder.utils.util.UtilScreen;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    public void testUtil() {
        int statusBarHeight = UtilScreen.getStatusBarHeight(getContext());
        XCLog.i(statusBarHeight);
    }

}