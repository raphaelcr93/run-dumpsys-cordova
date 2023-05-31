package com.ranieri.cordova.plugin;

// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.cordova.LOG;

//Java libraries
import java.io.InputStreamReader;
import java.io.BufferedReader;

//Android libraries
//import android.app.Activity;
import android.Manifest;
import android.text.TextUtils;
import android.view.WindowManager;




public class RunDumpsys extends CordovaPlugin {


  static RunDumpsys instance = null;
  private RunDumpsys mContext;
  private static final String LOG_TAG = "RunDumpsys";

  //protected final static String[] PERMISSIONS = {Manifest.permission.DUMP};
  
   @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        instance = this;
        //cordovaWebView = webView;
        //cordovaInterface = cordova;
    }

  @Override
  public boolean execute(String action, JSONArray args,
    final CallbackContext callbackContext) {
      
      mContext = this;
      // Verify that the user sent a 'run' action
      if (!action.equals("run")) {
        callbackContext.error("\"" + action + "\" is not a recognized action.");
        return false;
      }

      mContext.cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
            try{

                String command;
                String result;

                /*Not possible to ask for DUMP permission programatically dut to its protection level (android:protectionLevel="signatureOrSystem")
                ///Check permisisons to be able to access dump.
                if(!PermissionHelper.hasPermission(instance, PERMISSIONS[0])) {
                    LOG.d(LOG_TAG, "Requesting permission...");
                    PermissionHelper.requestPermissions(instance, 0, PERMISSIONS);
                }
                LOG.d(LOG_TAG, "Passed permission");*/

                //Get input parameters
                JSONObject options = args.getJSONObject(0);
                command = options.getString("command");

                String packageName = "com.ranieri.dumpsys";
                LOG.e(LOG_TAG, "Package name is "+packageName);
                String commandPerm = "pm grant "+packageName+" android.permission.DUMP";
                Process proc = Runtime.getRuntime().exec(commandPerm);
                proc.waitFor();
                LOG.e(LOG_TAG, "Ran permission command");
                proc = Runtime.getRuntime().exec(command);
                proc.waitFor();
                InputStreamReader is = new InputStreamReader(proc.getInputStream());
                BufferedReader reader = new BufferedReader(is);

                result = new String();
                for (String line; (line = reader.readLine()) != null; result += line);

                // Send a positive result to the callbackContext
                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK,result);
                callbackContext.sendPluginResult(pluginResult);
            } catch(Exception e){
                callbackContext.error(e.toString());
                LOG.e(LOG_TAG, "Error running run activity " + e.toString());
            }
        }
            });

            return true;


  }
}