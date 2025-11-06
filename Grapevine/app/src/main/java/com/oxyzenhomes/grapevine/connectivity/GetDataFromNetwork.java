package com.oxyzenhomes.grapevine.connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.apache.http.client.utils.URLEncodedUtils.format;

/**
 * Created by satish on 12/23/2016.
 */

public class GetDataFromNetwork extends AsyncTask<Void, String, Object> {
    private String URL;
    private String methodName;
    private Context context;
    private ProgressDialog progressDialog;
    private GetDataCallBack callBack;
    private List<NameValuePair> params;


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

    public GetDataFromNetwork(Context context, int progressBarStyle, String message, GetDataCallBack callBack) {
        this.callBack = callBack;
        this.context = context;
        setProgresDialogProperties(progressBarStyle, message);
    }

    /**
     * @param methodName
     *            :Name of function of the particular webservice
     * @param params
     */
    public void setConfig( String URL,String methodName,List<NameValuePair> params) {

        this.URL = URL;
        this.methodName = methodName;
        this.params = params;
        Log.i("Method Name", methodName);
    }
/*
	@SuppressWarnings("rawtypes")
	public void sendData(HashMap<String, Object> requestParamater) {

		Iterator iterator = requestParamater.entrySet().iterator();
		while (iterator.hasNext()) {
			//Map.Entry entry = (Map.Entry) iterator.next();

			//	Log.i(entry.getKey().toString(), entry.getValue().toString());
		}
	}*/

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // progressDialog.show();
    }

    @Override
    protected Object doInBackground(Void... paramss) {
        Object result = null;

        try {
            String paramString = null;
            java.net.URL url;
            if(params==null)
            {
                url = new URL(URL+methodName);
            }
            else
            {
                paramString=format(params, "utf-8");
                url = new URL(URL+methodName+"?"+paramString);
            }

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
        return result;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        callBack.processResponse(result);

        try {
           // progressDialog.dismiss();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


}
