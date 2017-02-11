package com.waracle.androidtest.presenter;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.waracle.androidtest.model.Cake;

import java.util.List;

/**
 * This Fragment manages a single background task and retains
 * itself across configuration changes.
 */

public class WorkerFragment extends Fragment {

    /**
     * Callback interface through which the fragment will report the
     * task's progress and results back to the Activity.
     */

    interface WorkerFragmentCallbacks{
        void onPreExecute();
        void onProgressUpdate(int percent);
        void onCancelled();
        void onPostExecute();
    }

    private WorkerFragmentCallbacks callbacks;
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
        callbacks = (WorkerFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    /**
     * A work task that performs background fetching of cake data and
     * proxies progress updates and results back to the host Activity.
     *
     * Note: that we need to check if the callbacks are null in each
     * method in case they are invoked after the Activity's and
     * Fragment's onDestroy() method have been called.
     */
    private class FetchCakeDataTask extends AsyncTask<String, String, List<Cake>>{

        @Override
        protected void onPreExecute() {
            if (callbacks != null){
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
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Cake> cakes) {
            super.onPostExecute(cakes);
        }
    }
}
