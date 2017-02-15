package com.waracle.androidtest.utils;

import android.net.Uri;

/**
 * Created by Ruairi on 11/02/2017.
 */

public class Constant {

    // Http calls
    public static final String HTTP_GET = "GET";
    public static final String HTTP_PUT = "PUT";

    public static final String TAG_WORKER_FRAGMENT = "worker_fragment";
    public static final String LIST_STATE = "save_list_state";

    public static String buildJSONURL(){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("gist.githubusercontent.com")
                .appendPath("hart88")
                .appendPath("198f29ec5114a3ec3460")
                .appendPath("raw")
                .appendPath("8dd19a88f9b8d24c23d9960f3300d0c917a4f07c")
                .appendPath("cake.json");
        return builder.build().toString();
    }

}
