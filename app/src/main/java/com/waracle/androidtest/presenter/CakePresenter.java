package com.waracle.androidtest.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.waracle.androidtest.Interactors.CakeInteractor;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.model.network.HttpManager;
import com.waracle.androidtest.model.parser.JsonParser;
import com.waracle.androidtest.utils.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruairi on 10/02/2017.
 */

public class CakePresenter implements CakeInteractor.CakeModelInteractor {

    CakeInteractor.CakeViewInteractor view;
    CakePresenter.WorkerFragment worker;
    public static final String TAG = CakePresenter.class.getSimpleName();

    public CakePresenter(CakeInteractor.CakeViewInteractor view) {
        this.view = view;
    }

    public void executeTask() {
        WorkerFragment.fetchTask(Constant.buildJSONURL());
    }

    @Override
    public void onPreExecute() {
        view.showProgress();
    }

    @Override
    public void onProgressUpdate(int percent) {

    }

    @Override
    public void onPostExecute(List<Cake> cakes) {
        if (cakes != null) {
            view.hideProgress();
            for (Cake cake : cakes) {
                view.updateView(cake);
                Log.d(TAG, "doInBackground: Retrieved list: " + cakes.toString() + "\n");
            }
        }
    }

    public static class WorkerFragment extends Fragment {

        List<Cake> cakes = new ArrayList<>();
        CakePresenter presenter;
        /**
         * Callback interface through which the fragment will report the
         * task's progress and results back to the Activity.
         */
        private CakeInteractor.CakeModelInteractor callbacks;
        private static FetchCakeDataTask task;

        /**
         * This method will only be called once when the retained
         * Fragment is first created.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Retain this fragment across config changes
            setRetainInstance(true);
            initCakeTask();
            presenter = new CakePresenter((CakeInteractor.CakeViewInteractor) getActivity());
            Toast.makeText(getActivity(), "Worker Fragment Created", Toast.LENGTH_SHORT).show();
        }

        public void initCakeTask() {
            task = new FetchCakeDataTask();
            Toast.makeText(getActivity(), "Cake task created", Toast.LENGTH_SHORT).show();
        }

        public static void fetchTask(String url) {
            task.executeTask(url);
            Log.d(TAG, "fetchTask: EXECUTED!");

        }

        /**
         * Set the callback to null so we don't accidentally leak the
         * Activity instance.
         */
        @Override
        public void onAttach(Context activity) {
            super.onAttach(activity);
            callbacks = (CakeInteractor.CakeModelInteractor) getView();
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

            public final String TAG = WorkerFragment.class.getSimpleName();

            public void executeTask(String url) {
                new FetchCakeDataTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
            }

            @Override
            protected void onPreExecute() {
                if (presenter != null) {
//                    callbacks.onPreExecute();
                    presenter.onPreExecute();
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
                try {
                    content = HttpManager.getData(params[0]);
                    cakes = JsonParser.parseCakeFeed(content);

//                    todo - handle caching of bitmaps
//                    if (cakes != null){
//                        for (Cake cake : cakes){
//
//                        }
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return cakes;
            }

            @Override
            protected void onProgressUpdate(Integer... percent) {
                if (presenter != null) {
                    presenter.onProgressUpdate(percent[0]);
                }
            }

            @Override
            protected void onPostExecute(List<Cake> cakes) {
                if (presenter != null) {
                    presenter.onPostExecute(cakes);
                }
            }
        }

    }
}

