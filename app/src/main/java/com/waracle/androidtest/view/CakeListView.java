package com.waracle.androidtest.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.waracle.androidtest.Interactors.CakeInteractor;
import com.waracle.androidtest.R;
import com.waracle.androidtest.list.CakeAdapter;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.presenter.CakePresenter;
import com.waracle.androidtest.presenter.WorkerFragment;
import com.waracle.androidtest.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a fragment containing List UI that will be updated from work carried out
 * in the Worker fragment.
 */

public class CakeListView extends Fragment implements CakeInteractor.CakeViewInteractor{

    public static final String TAG = CakeListView.class.getSimpleName();
    private RecyclerView listView;
    private CakeAdapter adapter;
    private List<Cake> cakeList = new ArrayList<>();
    private ProgressBar progress;
    private CakePresenter presenter;
    private WorkerFragment worker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(view);
        attachPresenter();
        createCakeList();
        return view;
    }

    private void attachPresenter() {
        presenter = new CakePresenter((CakeInteractor.CakeViewInteractor) getActivity());
    }

    private void initViews(View view) {
        progress = (ProgressBar)view.findViewById(R.id.progressBar);
        listView = (RecyclerView)view.findViewById(R.id.cake_list_view);
        listView = (RecyclerView) view.findViewById(R.id.cake_list_view);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    private void createCakeList() {
        listView.setHasFixedSize(true);
        adapter = new CakeAdapter(cakeList);
        listView.setAdapter(getAdapter());
    }

    public CakeAdapter getAdapter() {
        return adapter = new CakeAdapter(cakeList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createRetainedStateWorkerClass();
    }

    private void createRetainedStateWorkerClass() {
        FragmentManager manager = getFragmentManager();
        // check if worker created
        worker = (WorkerFragment) manager.findFragmentByTag(Constant.TAG_WORKER_FRAGMENT);

        // if not retained or is first time running - create one
        if (worker == null){
            worker = new WorkerFragment();
            // Tell it who it is working with.
            worker.setTargetFragment(this, 0);
            manager.beginTransaction().add(worker, Constant.TAG_WORKER_FRAGMENT).commit();
        }
    }

    public void executeTask(){
        presenter.executeFetchCakeTask();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void updateView(List<Cake> cakes) {
        adapter.populate(cakes);
        Log.d(TAG, "updateView: Data received: " + cakes);
    }
}
