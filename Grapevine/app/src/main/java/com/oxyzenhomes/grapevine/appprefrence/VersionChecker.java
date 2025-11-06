package com.oxyzenhomes.grapevine.appprefrence;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import com.oxyzenhomes.grapevine.oxyzenrental.HomeActivity;
import com.oxyzenhomes.grapevine.oxyzenrental.Main2Activity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class VersionChecker extends AsyncTask<Void, String, String> {

    Context mContext;
    AppPreferences appPreferences;
    public VersionChecker(Context context) {
        mContext = context;
    }

    @Override

    protected String doInBackground(Void... voids) {

        String newVersion = null;

        try {
            Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=com.oxyzenhomes.grapevine")
                    .timeout(5000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
            if (document != null) {
                Elements element = document.getElementsContainingOwnText("Current Version");
                for (Element ele : element) {
                    if (ele.siblingElements() != null) {
                        Elements sibElemets = ele.siblingElements();
                        for (Element sibElemet : sibElemets) {
                            newVersion = sibElemet.text();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newVersion;

    }


    @Override

    protected void onPostExecute(String onlineVersion) {

        super.onPostExecute(onlineVersion);
        appPreferences=new AppPreferences(mContext);
        if (onlineVersion != null && !onlineVersion.isEmpty()) {
            if (Float.valueOf("10.124") < Float.valueOf(onlineVersion)) {
                AlertDialog ad = new AlertDialog.Builder(mContext).create();
                //ad.setCancelable(false); // This blocks the 'BACK' button
                ad.setMessage("Latest update available, Upgrade now?.");
                ad.setButton(DialogInterface.BUTTON_NEUTRAL,  "Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.dismiss();
                        Intent j = new Intent(mContext, Main2Activity.class);
                        mContext.startActivity(j);

                    }
                });

                ad.setButton(DialogInterface.BUTTON_NEGATIVE,  "Later", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.dismiss();
                        appPreferences.setIsUPdateLater(true);
                        appPreferences.setUpdateLaterCount(0);
                        Intent j = new Intent(mContext, HomeActivity.class);
                        mContext.startActivity(j);

                    }
                });
                ad.show();
            }
        }
    }
}
