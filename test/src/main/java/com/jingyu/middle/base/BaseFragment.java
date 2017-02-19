package com.jingyu.middle.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jingyu.utils.application.PlusFragment;
import com.jingyu.utils.function.Logger;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public abstract class BaseFragment extends PlusFragment {
    @Override
    public void onAttach(Context context) {
        Logger.i(this + "--onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.i(this + "--onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.i(this + "--onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View createView(LayoutInflater inflater, int layout_id, ViewGroup container) {
        Logger.i(this + "--onCreateView?.createView()");
        return super.createView(inflater, layout_id, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.i(this + "--onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Logger.i(this + "--onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Logger.i(this + "--onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Logger.i(this + "--onResume");
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Logger.i(this + "--onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        Logger.i(this + "--onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Logger.i(this + "--onStop");
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        Logger.i(this + "--onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        Logger.i(this + "--onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Logger.i(this + "--onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Logger.i(this + "--onDetach");
        super.onDetach();
    }

}
