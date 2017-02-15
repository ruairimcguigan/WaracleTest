package com.waracle.androidtest.presenter.Interactors;

import com.waracle.androidtest.model.Cake;

import java.util.List;

/**
 * Created by Ruairi on 10/02/2017.
 */

public interface CakeInteractor {

    interface CakeModelInteractor {

        void onPreExecute();

        void onPostExecute(List<Cake> data);
    }

    interface CakeViewInteractor {

        void showProgress();

        void hideProgress();

        void populateAdapter(List<Cake> cakes);
    }
}
