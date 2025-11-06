package com.oxyzenhomes.grapevine.connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by satish on 12/23/2016.
 */

public class GetDataFromNetworkClass   extends AsyncTask<Void, String, Object> {
    private String URL;
    private String methodName;
    private Context context;
    private ProgressDialog progressDialog;
    private GetDataCallBack callBack;
    private String params;




    /**
     * Set default progress Bar properties. Internal function called from
     * setConfig
     *
     * @param progressBarStyle
     *            : Progress Dialog style (spinner or horizontal)
     * @param message
     *            : Message to be displayed on progressBar
     */
    protected void setProgresDialogProperties(int progressBarStyle, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(progressBarStyle);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
    }

    public GetDataFromNetworkClass(Context context, int progressBarStyle, String message, GetDataCallBack callBack) {
        this.callBack = callBack;
        this.context = context;
        setProgresDialogProperties(progressBarStyle, message);
    }

    /**
     * @param methodName
     *            :Name of function of the particular webservice
     * @param params

     */
    public void setConfig(String URL, String methodName, String params) {

        this.URL = URL;
        this.methodName = methodName;
        this.params = params;

        Log.i("Method Name", methodName);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Void... paramss) {
        Object result = null;

        InputStream inputStream = null;
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(URL+"/"+methodName);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(params);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        callBack.processResponse(result);

        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    private String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return stringBuilder.toString();
    }


}
