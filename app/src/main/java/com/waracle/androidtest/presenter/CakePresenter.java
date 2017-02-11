package com.waracle.androidtest.presenter;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import com.waracle.androidtest.Interactors.CakeInteractor;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.model.network.HttpManager;
import com.waracle.androidtest.model.parser.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruairi on 10/02/2017.
 */

public class CakePresenter implements CakeInteractor.CakeModelInteractor{

    CakeInteractor.CakeViewInteractor view;
    List<Cake> cakeList = new ArrayList<>();

    public CakePresenter(CakeInteractor.CakeViewInteractor view) {
        this.view = view;
    }

    @Override
    public void onPreExecute() {
        view.showProgress();
    }

    @Override
    public void onProgressUpdate(int percent) {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute(List<Cake> cakes) {
        if (cakes != null){
            for(Cake cake : cakes){
                view.updateView(cake);

            }
        }
    }

    public static class WorkerFragment extends Fragment {

        /**
         * Callback interface through which the fragment will report the
         * task's progress and results back to the Activity.
         */
        private CakeInteractor.CakeModelInteractor callbacks;
        private FetchCakeDataTask task;

        /**
         * This method will only be called once when the retained
         * Fragment is first created.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Retain this fragment across config changes
            setRetainInstance(true);

            task = new FetchCakeDataTask();
            task.execute();
        }

        /**
         * Set the callback to null so we don't accidentally leak the
         * Activity instance.
         */
        @Override
        public void onAttach(Context activity) {
            super.onAttach(activity);
            callbacks = (CakeInteractor.CakeModelInteractor) activity;
        }

        @Override
        public void onDetach() {
            super.onDetach();
            callbacks = null;
        }

        /**
         * A work task that performs background fetching of cake data and
         * proxies progress updates and results back to the host Activity.
         * <p>
         * Note: that we need to check if the callbacks are null in each
         * method in case they are invoked after the Activity's and
         * Fragment's onDestroy() method have been called.
         */
        private class FetchCakeDataTask extends AsyncTask<String, Integer, List<Cake>> {

            @Override
            protected void onPreExecute() {
                if (callbacks != null) {
                    callbacks.onPreExecute();
                }
            }

            /**
             * Note that we do NOT call the callback object's methods
             * directly from the background thread, as this could result
             * in a race condition.
             */
            @Override
            protected List doInBackground(String... params) {
                String content = "";
                InputStream inputStream = null;
                Bitmap bitmap;
                try{
                    content = HttpManager.getData(params[0]);
                    List<Cake> cakes = JsonParser.parseCakeFeed(content);
                    if (cakes != null){
                        for (Cake cake : cakes){

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... percent) {
               if (callbacks != null){
                   callbacks.onProgressUpdate(percent[0]);
               }
            }

            @Override
            protected void onPostExecute(List<Cake> cakes) {
                if (callbacks != null){
                    callbacks.onPostExecute(cakes);
                }
            }
        }

    }
}

