package com.jingyu.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.middle.http.SystemDialog;

/**
 * @description 看看不同版本系统上的dialog显示效果
 */
public class SystemDialogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_dialog);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            //supportActionBar.hide();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item1:
                alertDialog();
                break;
            case R.id.menu_item2:
                progressDialog();
                break;
            case R.id.menu_item3:
                progressBar();
        }
        return true;
    }

    private void progressBar() {
        SystemDialog dialog = new SystemDialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void progressDialog() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("title");
        dialog.setMessage("test dialog");
        dialog.show();
    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
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

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, SystemDialogActivity.class));
    }

}
