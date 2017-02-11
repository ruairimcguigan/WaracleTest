package com.waracle.androidtest.Interactors;

import com.waracle.androidtest.model.Cake;

import java.util.List;

/**
 * Created by Ruairi on 10/02/2017.
 */

public interface CakeInteractor {

     interface CakeModelInteractor {
        void onPreExecute();
        void onProgressUpdate(int percent);
        void onCancelled();
        void onPostExecute(List<Cake> data);
    }

     interface CakeViewInteractor {
        void showProgress();
        void hideProgress();
        void executeFetchData(String url);
        void updateView(Cake cake);
    }
}
