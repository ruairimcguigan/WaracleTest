package com.waracle.androidtest.model.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.waracle.androidtest.utils.Constant.HTTP_GET;

/**
 * Created by Ruairi on 10/02/2017.
 */
// Will contain the raw JSON response as a string.
public class HttpManager {
    private static final String TAG = HttpManager.class.getSimpleName();

    public static String getData(String urlString) throws IOException {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonString = null;

        try {
            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(HTTP_GET);
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder sb = new StringBuilder();
            if (inputStream == null) {
                Log.d(TAG, "getData: inputStream is null");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // sb for debugging.
                sb.append(line).append("\n");
            }

            if (sb.length() == 0) {
                //Stream was empty. No need to parse
                Log.d(TAG, "getData: Stream was empty. No need to parse");
                return null;
            }
            jsonString = sb.toString();
            //Error handling
        } catch (IOException e) {
            Log.e(TAG, "getData: ", e);
            return null;

            //clean up
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "getData: Error closing stream", e);
                }
            }
        }
        return jsonString;
    }


}

