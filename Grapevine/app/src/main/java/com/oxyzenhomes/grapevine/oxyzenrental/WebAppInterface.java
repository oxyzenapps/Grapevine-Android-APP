package com.oxyzenhomes.grapevine.oxyzenrental;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class WebAppInterface {
    private Context context;
    private Activity activity;

    public WebAppInterface(Activity activity) {
        this.context = activity;
        this.activity = activity;
    }

    // Expose method to JavaScript - can be called as AndroidBridge.showToast("message")
    @JavascriptInterface
    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // Called from JavaScript to request camera
    @JavascriptInterface
    public void openCamera(String options) {
        // Handle camera opening logic
    }

    // Called from JavaScript to get device info
    @JavascriptInterface
    public String getDeviceInfo() {
        JSONObject info = new JSONObject();
        try {
            info.put("model", Build.MODEL);
            info.put("manufacturer", Build.MANUFACTURER);
            info.put("version", Build.VERSION.RELEASE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info.toString();
    }

    // Called from JavaScript to check if running in native app
    @JavascriptInterface
    public boolean isNativeApp() {
        return true;
    }
}

