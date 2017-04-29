package com.jingyu.tooltest.db;

import android.os.Bundle;

import com.jingyu.middle.base.BaseActivity;

import java.util.ArrayList;

/**
 *  测试
 */
public class DbActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void db() {

        ModelDb instance = ModelDb.getInstance(getApplicationContext());

        instance.queryCount();

        instance.queryAllByIdAsc();

        Model model = new Model("test", "test", "test", "test", "test", "test");

        instance.insert(model);

        instance.inserts(getData());

        instance.updateThree(model, model.getName());

        instance.deleteTwo(model.getName());
    }

    private ArrayList<Model> getData() {
        ArrayList<Model> list = new ArrayList<>();
        list.add(new Model("10abcdefghijklmn", "小明10", "0", "70", "92", "篮球"));
        list.add(new Model("3abcdefghijklmn", "小红3", "1", "35", "70", "篮球"));
        list.add(new Model("9abcdefghijklmn", "小红9", "1", "65", "66", "篮球"));
        list.add(new Model("0abcdefghijklmn", "小明0", "0", "18", "50", "篮球"));
        list.add(new Model("1abcdefghijklmn", "小红1", "1", "25", "60", "篮球"));
        list.add(new Model("4abcdefghijklmn", "小明4", "0", "40", "97", "篮球"));
        list.add(new Model("14abcdefghijklmn", "小明14", "0", "90", "34", "篮球"));
        list.add(new Model("5abcdefghijklmn", "小红5", "1", "45", "40", "排球"));
        list.add(new Model("2abcdefghijklmn", "小明2", "0", "30", "90", "足球"));
        list.add(new Model("6abcdefghijklmn", "小明6", "0", "50", "20", "网球"));
        list.add(new Model("12abcdefghijklmn", "小明12", "0", "80", "77", "台球"));
        list.add(new Model("8abcdefghijklmn", "小明8", "0", "60", "86", "排球"));
        list.add(new Model("13abcdefghijklmn", "小红13", "1", "85", "57", "台球"));
        list.add(new Model("11abcdefghijklmn", "小红11", "1", "75", "99", "足球"));
        list.add(new Model("7abcdefghijklmn", "小红7", "1", "55", "67", "网球"));
        return list;
    }
}

