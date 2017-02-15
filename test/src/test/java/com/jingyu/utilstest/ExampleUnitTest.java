package com.jingyu.utilstest;

import org.junit.Test;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.assertEquals;

public class ExampleUnitTest {


    @Test
    public void testSub() {
        assertEquals(2, 3 - 2);
    }

    @Test
    public void testAdd() {
        assertEquals(2, 1 + 1);
    }

    public static void main(String[] args) {
        Class[] clazzs = new Class[]{ExampleUnitTest2.class, ExampleUnitTest.class};
        JUnitCore.runClasses(clazzs);
        // new JUnitCore().run(Request.method(ExampleUnitTest.class, "testAdd"));
    }

}
