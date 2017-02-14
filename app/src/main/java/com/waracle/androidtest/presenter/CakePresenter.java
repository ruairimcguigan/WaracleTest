package com.waracle.androidtest.presenter;

import android.util.Log;

import com.waracle.androidtest.ImageCache;
import com.waracle.androidtest.Interactors.CakeInteractor;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.utils.Constant;

import java.util.List;

/**
 * Created by Ruairi on 10/02/2017.
 */

public class CakePresenter implements CakeInteractor.CakeModelInteractor {

    public static final String TAG = CakePresenter.class.getSimpleName();
    CakeInteractor.CakeViewInteractor view;

    public CakePresenter(CakeInteractor.CakeViewInteractor view) {
        this.view = view;
    }

    public void executeFetchCakeTask() {
        WorkerFragment.fetchTask(Constant.buildJSONURL());
    }

    // Worker Fragment Callbacks
    @Override
    public void onPreExecute() {
        view.showProgress();
    }

    @Override
    public void onPostExecute(List<Cake> cakes) {
        if (cakes != null) {
            view.hideProgress();
                view.populateAdapter(cakes);
                Log.d(TAG, "doInBackground: Retrieved list: " + cakes.toString() + "\n");
            }
        }
    }

