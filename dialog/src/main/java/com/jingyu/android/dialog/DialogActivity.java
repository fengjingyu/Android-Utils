package com.jingyu.android.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.utils.function.Logger;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;


/**
 * 看看不同版本系统上的dialog显示效果
 */
public class DialogActivity extends BaseActivity {

    private Button originAlertDialog;
    private Button originProgressDialog;
    private Button originProgressDialog2;
    private Button originProgressDialog3;
    private Button rotateImgDialog;
    private Button sweetDialog;
    //private Button addressDialog;
    private Button plusHolderDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        originAlertDialog = getViewById(R.id.originAlertDialog);
        originProgressDialog = getViewById(R.id.originProgressDialog);
        originProgressDialog2 = getViewById(R.id.originProgressDialog2);
        originProgressDialog3 = getViewById(R.id.originProgressDialog3);
        rotateImgDialog = getViewById(R.id.rotateImgDialog);
        sweetDialog = getViewById(R.id.sweetDialog);
        //addressDialog = getViewById(R.id.addressDialog);
        plusHolderDialog = getViewById(R.id.plusHolderDialog);


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
                progressDialogPercent();
            }
        });

        rotateImgDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateDialog dialog = new RotateDialog(getActivity());
                dialog.show();
            }
        });

        sweetDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetDialog dialog = new SweetDialog(getActivity());
                dialog.show();
            }
        });

//        addressDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AddressDialog dialog = new AddressDialog(getActivity());
//                dialog.show();
//            }
//        });

        plusHolderDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://github.com/orhanobut/dialogplus
                DialogPlus dialogPlus = DialogPlus.newDialog(getActivity())
                        .setAdapter(new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_list_item_1, new String[]{"asdfa", "123", "234", "345", "abc", "2", "a", "c", "d"}))//如果是listHolder,gridHolder这setAdapter一定要调用
                        //.setContentHolder(new ListHolder())//默认是ListHolder; 如果是setConentHolder(new ViewHolder()),则setAdapter不需要调用
                        //.setHeader(View)
                        //.setFooter(View)
                        .setContentBackgroundResource(R.color.colorAccent)
                        //.setOverlayBackgroundResource(android.R.color.transparent)
                        //.setMargin
                        //.setContentHeight(300)
                        .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setCancelable(true)
                        .setExpanded(false)
                        .setGravity(Gravity.TOP)
                        .setOnDismissListener(new OnDismissListener() {
                            @Override
                            public void onDismiss(DialogPlus dialog) {

                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel(DialogPlus dialog) {

                            }
                        })
                        .setOnBackPressListener(new OnBackPressListener() {
                            @Override
                            public void onBackPressed(DialogPlus dialogPlus) {

                            }
                        }).setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                                Logger.shortToast(item);
                            }
                        })
                        .create();

                dialogPlus.show();
            }
        });
    }

    private void progressDialogPercent() {
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
