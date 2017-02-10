package com.waracle.androidtest.presenter;

import com.waracle.androidtest.CakeInteractor;

/**
 * Created by Ruairi on 10/02/2017.
 */

public class CakePresenter {

    CakeInteractor view;

    public CakePresenter(CakeInteractor view) {
        this.view = view;
    }
}
