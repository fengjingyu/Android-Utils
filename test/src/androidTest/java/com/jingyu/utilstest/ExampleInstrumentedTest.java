package com.jingyu.utilstest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jingyu.utils.function.Logger;
import com.jingyu.utils.function.ThreadHelper;
import com.jingyu.utils.json.JsonBean;
import com.jingyu.utils.json.JsonParse;
import com.jingyu.utilstest.model.TestModel;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    public Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void useAppContext() throws Exception {
        assertEquals("com.jingyu.test", getContext().getPackageName());
    }

    @Test
    public void testRuntime() {
        Logger.i("Runtime.getRuntime().availableProcessors()--" + Runtime.getRuntime().availableProcessors());
        Logger.i("Runtime.getRuntime().maxMemory()--" + Runtime.getRuntime().maxMemory() / Math.pow(2, 30));
    }

    @Test
    public void testUUID() {
        Logger.i("UUID----" + UUID.randomUUID() + "----" + UUID.randomUUID().toString().length());
    }

    @Test
    public void testLinkedBlockQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        queue.add("d");
        //Logger.i(queue.take()); // a  //take 类似 poll remove
        //Logger.i(queue.take()); // b
        //Logger.i(queue.take()); // c
        //Logger.i(queue.take()); // d

        Logger.i(queue.peek()); //a  // peek 类似 element
        Logger.i(queue.peek()); //a
        Logger.i(queue.peek()); //a
        Logger.i(queue.peek()); //a
        queue.remove("a");
        Logger.i(queue.peek()); //b
        Logger.i(queue.peek()); //b
        Logger.i(queue.peek()); //b
    }

    @Test
    public void testJsonFormat() {
        String str2 = " {\"code\":0,\"msg\":\"成功\",\"data\":[[\"板蓝根\",\"白云山\"]]}";

        JsonBean bean = JsonParse.getJsonParseData(str2, JsonBean.class);
        List beans = bean.getListList("data", new ArrayList<ArrayList>());

        try {
            Logger.i(beans.toString());
            Logger.i(beans.get(0).toString());
            if (beans.get(0) instanceof List) {
                Logger.i("List");
            } else if (beans.get(0) instanceof String[]) {
                Logger.i("string[]");
            } else {
                Logger.i(beans.get(0).getClass().toString());

                JSONArray array = (JSONArray) beans.get(0);
                int count = array.length();
                for (int i = 0; i < count; i++) {
                    Logger.i(array.get(i));
                }
            }
        } catch (Exception e) {
            Logger.e(this, e);
        }
    }

    @Test
    public void testSubList() {
        List<TestModel> list = new ArrayList<>();
        TestModel model = new TestModel();
        TestModel model2 = new TestModel();
        TestModel model3 = new TestModel();
        TestModel model4 = new TestModel();

        list.add(model);
        list.add(model2);
        list.add(model3);
        list.add(model4);

        List<TestModel> sub = list.subList(0, 2);

        Logger.i("list", list.size());//4
        Logger.i("list", sub.size());//2
        Logger.i("list", list.get(0));//相同@1234567
        Logger.i("list", sub.get(0));//相同@1234567
        Logger.i("list", list.get(1));//相同@0123456
        Logger.i("list", sub.get(1));//相同@0123456
    }

    @Test
    public void testExecutor() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        assertSame(executorService, executorService2);

//        ExecutorService executorService3 = Executors.newSingleThreadExecutor();
//        ExecutorService executorService4 = Executors.newSingleThreadExecutor();
//        assertSame(executorService3,executorService4);
    }

    @Test
    public void testExecutorManager() {
        assertSame(ThreadHelper.getCache(), ThreadHelper.getCache());
    }

}
