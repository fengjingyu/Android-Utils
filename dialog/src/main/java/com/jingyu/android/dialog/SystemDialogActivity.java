package com.jingyu.android.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.jingyu.android.middle.base.BaseActivity;


/**
 *  看看不同版本系统上的dialog显示效果
 */
public class SystemDialogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_dialog);
    }

    @Override
    public void hiddenTitleActionBar() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_item1:
                alertDialog();
                break;
            case R.id.toolbar_item2:
                progressDialog();
                break;
            case R.id.toolbar_item3:
                progressDialog2();
                break;
            case R.id.toolbar_item4:
                progressDialog3();
        }
        return true;
    }

    private void progressDialog3() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        new Thread(new Runnable() {
            int progress = 21;

            @Override
            public void run() {
                while (progress < 100) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progress = progress + 1;
                    dialog.setProgress(progress);
                    dialog.setSecondaryProgress(progress - 20);
                }
            }
        }).start();

        dialog.show();
    }

    private void progressDialog2() {
        ProgressDialog.show(getActivity(), "", "  加载中..", true, true);
    }

    private void progressDialog() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("title");
        dialog.setMessage("content");
        dialog.show();
    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("title");
        dialog.setMessage("content");
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
