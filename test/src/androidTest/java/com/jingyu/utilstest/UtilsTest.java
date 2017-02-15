package com.jingyu.utilstest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jingyu.utils.util.UtilIoAndr;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UtilsTest {

    public Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testFileSave() {
        UtilIoAndr.write2Inside(getContext(), "testFile", "abcdefg123", Context.MODE_PRIVATE);
        assertEquals(UtilIoAndr.readFromInside(getContext(), "testFile"), "abcdefg123");
    }

    @Test
    public void testSDCardSave() {
        UtilIoAndr.write2SDCard("aa/bb", "1.txt", "android123");
        assertEquals(UtilIoAndr.readFromSDCard("aa/bb", "1.txt"), "android123");
    }

}
