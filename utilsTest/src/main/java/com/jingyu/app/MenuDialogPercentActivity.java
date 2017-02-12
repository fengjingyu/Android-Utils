package com.jingyu.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jingyu.utilstest.R;

public class MenuDialogPercentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menudialogpercent);

        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            //supportActionBar.hide();
        }


        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MenuDialogPercentActivity.this);
                dialog.setTitle("title");
                dialog.setMessage("test dialog");
                dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                dialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = new ProgressDialog(MenuDialogPercentActivity.this);
                dialog.setTitle("title");
                dialog.setMessage("test dialog");
                dialog.show();
            }
        });

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("测试");
        dialog.setMessage("缓存清理中");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return true;
    }


    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, MenuDialogPercentActivity.class));
    }
}
