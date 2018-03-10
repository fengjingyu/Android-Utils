package com.jingyu.android.aidl.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import com.jingyu.android.basictools.log.Logger;
import com.jingyu.android.basictools.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {

    private List<String> list = new ArrayList<>();

    private final IRemoteService.Stub aidlBinder = new IRemoteService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getInfo() throws RemoteException {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getClass().getName());
            sb.append(", 线程名=" + Thread.currentThread().getName());
            sb.append(", pid = " + Process.myPid());
            sb.append(", 进程名= " + SystemUtil.getProcessName(getApplicationContext()));
            return sb.toString();
        }

        @Override
        public List<String> getNames() throws RemoteException {
            try {
                // IPC调用是同步的(虽然方法是在binder的线程池里执行的)。如果你知道一个IPC服务需要超过几毫秒的时间才能完成地话，你应该避免在Activity的主线程中调用。也就是IPC调用会挂起应用程序导致界面失去响应，这种情况应该考虑单独开启一个线程来处理。
                // 即aidl的接口方法是在binder的线程池中进行的,但是如果aidl的接口方法是在ui线程中调用,且方法的执行耗时,则会卡ui线程
                // 即在ui线程中调用的接口方法在binder的线程池里执行,但是ui线程会等binder的线程池返回后在往下接着执行,所以如果耗时会anr
                // Thread.sleep(10000);
                // Thread.sleep(10);
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        public AIDLBean getBeanIn(AIDLBean bean) throws RemoteException {
            Logger.d("getBeanIn(),in修饰符,服务端接收到" + bean);
            if (bean == null) {
                bean = new AIDLBean();
                Logger.d("getBeanIn(),in修饰符,服务端接收到null,new AIDLBean()");
            }
            bean.setName("service_in");
            Logger.d("getBeanIn(),in修饰符,服务端修改返回" + bean);
            return bean;
        }

        @Override
        public AIDLBean getBeanOut(AIDLBean bean) throws RemoteException {
            Logger.d("getBeanOut(),out修饰符,服务端接收到" + bean);
            if (bean == null) {
                bean = new AIDLBean();
                Logger.d("getBeanOut(),out修饰符,服务端接收到null,new AIDLBean()");
            }
            bean.setName("service_out");
            Logger.d("getBeanOut(),out修饰符,服务端修改返回" + bean);
            return bean;
        }

        @Override
        public AIDLBean getBeanInOut(AIDLBean bean) throws RemoteException {
            Logger.d("getBeanInOut(),inout修饰符,服务端接收到" + bean);
            if (bean == null) {
                bean = new AIDLBean();
                Logger.d("getBeanInOut(),inout修饰符,服务端接收到null,new AIDLBean()");
            }
            bean.setName("service_inout");
            Logger.d("getBeanInOut(),inout修饰符,服务端修改返回" + bean);
            return bean;
        }

        @Override
        public void getBeanIn2(AIDLBean bean) throws RemoteException {
            Logger.d("getBeanIn()2,in修饰符,服务端接收到" + bean);
            if (bean == null) {
                bean = new AIDLBean();
                Logger.d("getBeanIn2(),in修饰符,服务端接收到null,new AIDLBean()");
            }
            bean.setName("service_in2");
            Logger.d("getBeanIn2(),in修饰符,服务端修改不返回" + bean);
        }

        @Override
        public void getBeanOut2(AIDLBean bean) throws RemoteException {
            Logger.d("getBeanOut()2,out修饰符,服务端接收到" + bean);
            if (bean == null) {
                bean = new AIDLBean();
                Logger.d("getBeanOut()2,out修饰符,服务端接收到null,new AIDLBean()");
            }
            bean.setName("service_out2");
            Logger.d("getBeanOut2(),out修饰符,服务端修改不返回" + bean);
        }

        @Override
        public void getBeanInOut2(AIDLBean bean) throws RemoteException {
            Logger.d("getBeanInOut()2,inout修饰符,服务端接收到" + bean);
            if (bean == null) {
                bean = new AIDLBean();
                Logger.d("getBeanInOut2(),inout修饰符,服务端接收到null,new AIDLBean()");
            }
            bean.setName("service_inout2");
            Logger.d("getBeanInOut2(),inout修饰符,服务端修改不返回" + bean);
        }

        @Override
        public String registerCallBack(AIDLCallBack callBack) throws RemoteException {
            if (callBack != null) {
                return callBack.onListener();
            }
            return null;
        }
    };

    @Override
    public void onCreate() {
        Logger.d(this + "--onCreate()");
        super.onCreate();

        list.add("a");
        list.add("b");
        list.add("c");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(this + "--onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.d(this + "--onBind()");
        return aidlBinder;
    }

    @Override
    public void onDestroy() {
        Logger.d(this + "--onDestroy()");
        super.onDestroy();
    }

    public static void actionStart(Context context) {
        context.startService(new Intent(context, AIDLService.class));
    }

    public static void actionStop(Context context) {
        context.stopService(new Intent(context, AIDLService.class));
    }
}
