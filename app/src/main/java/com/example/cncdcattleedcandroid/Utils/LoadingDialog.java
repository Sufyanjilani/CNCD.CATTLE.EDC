package com.example.cncdcattleedcandroid.Utils;

import android.app.Activity;
import android.content.Context;

import com.basusingh.beautifulprogressdialog.BeautifulProgressDialog;
import com.example.cncdcattleedcandroid.R;

public class LoadingDialog {


    BeautifulProgressDialog progressDialog;
    Activity activity;

    Context ctx;
    public LoadingDialog(Context context, Activity activity){
        this.ctx = context;
        this.activity = activity;

    }
    public void ShowCustomLoadingDialog(){

        progressDialog = new BeautifulProgressDialog(activity, BeautifulProgressDialog.withImage, "Please wait");
        progressDialog.setImageLocation(ctx.getResources().getDrawable(R.drawable.cowimage));
        progressDialog.setLayoutColor(ctx.getResources().getColor(android.R.color.white));
        progressDialog.show();
    }

    public void dissmissDialog(){

        progressDialog.dismiss();
    }
}
