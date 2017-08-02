package com.jingyu.android.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.middle.base.BaseActivity;


/**
 * 看看不同版本系统上的dialog显示效果
 */
public class DialogActivity extends BaseActivity {

    private Button originAlertDialog;
    private Button originProgressDialog;
    private Button originProgressDialog2;
    private Button originProgressDialog3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        originAlertDialog = getViewById(R.id.originAlertDialog);
        originProgressDialog = getViewById(R.id.originProgressDialog);
        originProgressDialog2 = getViewById(R.id.originProgressDialog2);
        originProgressDialog3 = getViewById(R.id.originProgressDialog3);


        originAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });

        originProgressDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog();
            }
        });

        originProgressDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog2();
            }
        });

        originProgressDialog3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog3();
            }
        });
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
        context.startActivity(new Intent(context, DialogActivity.class));
    }

}
