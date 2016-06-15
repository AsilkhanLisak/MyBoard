package sadykov.asus.slidebar.JSONpackage;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;

import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.util.List;

import android.content.ContentValues;
import android.util.Log;
import android.widget.Toast;

public class JSONParser {
    final String TAG = "JsonParser.java";
    static InputStream inputStream = null;
    static JSONObject jObj = null;
    static JSONObject jObjError = null;
    static String json = "";
    static HttpURLConnection urlConnection = null;
    public JSONObject getJSONFromUrl(String urlSource, String method, String params) {
        //make HTTP request
        //Log.d("step 1"," to be continew");
        try{ jObjError = new JSONObject("{\"success\":404}");}
        catch (JSONException ee){ee.printStackTrace();}
        try {
            URL url = new URL(urlSource);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            //urlConnection.setRequestProperty("User-Agent",);
            //For POST only START
            try
            {
                OutputStream os = urlConnection.getOutputStream();
                os.write(params.getBytes());
                os.flush();
                os.close();
            }
            catch (ConnectException e){
                Log.d("Lisak","please read StackTrace information...");
                e.printStackTrace();
                return jObjError;
            }
            Log.d("Lisak","Checking exception...");
            //For POST only END
            try {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());

            } catch (ConnectException e){
                //Toast.makeText(getApplicatonContext(), "Отсутствует соединение с сервером",Toast.LENGTH_LONG).show();
                e.printStackTrace();
                Log.d("Lisak","Отсутствует соединение с сервером");
                return jObjError;
            }


            //Log.d("step 2"," to be continew");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return jObjError;
        } catch (IOException e) {
            e.printStackTrace();
            return jObjError;
        }

        //Read JSON data from inputStream
        try {
            //Log.d("step 3"," to be continew");

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            inputStream.close();

            //Log.d("step 4"," to be continew");
            //System.out.println("prints json string 1 step");
            json = sb.toString();
            //Log.d("step 5"," to be continew");
            //System.out.println("prints json string");
            System.out.println("json string"+json);
        } catch (Exception e) {
            Log.e(TAG, "Error converting result " + e.toString());
        }
        //urlConnection.disconnect();
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
            return jObj;
        }
        catch (JSONException e) {
            System.out.println("Error parsing data" + e.toString());
            Log.e(TAG, "Error parsing data " + e.toString());
            return jObjError;
        }

        //return jObjError;// return JSON String
    }
}

