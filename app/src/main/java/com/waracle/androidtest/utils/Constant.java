package com.waracle.androidtest.utils;

import android.net.Uri;

/**
 * Created by Ruairi on 11/02/2017.
 */

public class Constant {

    // Http calls
    public static final String HTTP_GET = "GET";
    public static final String HTTP_PUT = "PUT";
    public static final String HTTP_POST = "POST";
    public static final String HTTP_DELETE = "DELETE";

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
