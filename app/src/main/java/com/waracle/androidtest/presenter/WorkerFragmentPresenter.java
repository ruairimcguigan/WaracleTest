package com.waracle.androidtest.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.waracle.androidtest.model.cache.ImageCache;
import com.waracle.androidtest.presenter.Interactors.CakeInteractor;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.model.network.HttpManager;
import com.waracle.androidtest.model.parser.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public  class WorkerFragmentPresenter extends Fragment {

    List<Cake> cakes = new ArrayList<>();
    CakePresenter presenter;
    /**
     * Callback interface through which the fragment will report the
     * fetchCakeTask's progress and results back to the Activity.
     */
    private CakeInteractor.CakeModelInteractor callbacks;
    private static FetchCakeDataTask fetchCakeTask;

    /**
     * This method will only be called once when the retained
     * Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across config changes
        setRetainInstance(true);
        getCakeTask();
        getPresenterReference();
    }

    public void getPresenterReference() {
        presenter = new CakePresenter((CakeInteractor.CakeViewInteractor) getActivity());
    }

    public void getCakeTask() {
        fetchCakeTask = new FetchCakeDataTask();
    }

    public static void fetchTask(String url) {
        fetchCakeTask.executeTask(url);
    }

    /**
     * Set the callback to null so we don't accidentally leak the
     * Activity instance.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    /**
     * A work fetchCakeTask that performs background fetching of cake data and
     * proxies progress updates and results back to the host Activity.
     * <p>
     * Note: that we need to check if the callbacks are null in each
     * method in case they are invoked after the Activity's and
     * Fragment's onDestroy() method have been called.
     */
    private class FetchCakeDataTask extends AsyncTask<String, Integer, List<Cake>> {

        public final String TAG = WorkerFragmentPresenter.class.getSimpleName();

        public void executeTask(String url) {
            new FetchCakeDataTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        }

        @Override
        protected void onPreExecute() {
            if (presenter != null) {
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
            try {
                content = HttpManager.getData(params[0]);
                cakes = JsonParser.parseCakeFeed(content);
                // TODO: 15/02/2017 Spin up Asynctask for container for lazy loading of images
                ImageCache.getInstance().cacheImages(cakes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return cakes;
        }


        @Override
        protected void onPostExecute(List<Cake> cakes) {
            if (presenter != null) {
                    presenter.onPostExecute(cakes);
                }
            }
        }
    }

