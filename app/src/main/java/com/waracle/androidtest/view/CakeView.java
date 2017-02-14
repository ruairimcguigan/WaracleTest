package com.waracle.androidtest.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.waracle.androidtest.Interactors.CakeInteractor;
import com.waracle.androidtest.R;
import com.waracle.androidtest.list.CakeAdapter;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.presenter.CakePresenter;
import com.waracle.androidtest.presenter.WorkerFragment;
import com.waracle.androidtest.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.R.attr.dividerVertical;
import static android.support.design.R.attr.layoutManager;

public class CakeView extends AppCompatActivity implements CakeInteractor.CakeViewInteractor {

    public static final String TAG = CakeView.class.getSimpleName();

    DividerItemDecoration dividerItemDecoration;
    RecyclerView listView;
    CakePresenter presenter;
    Toolbar toolbar;
    WorkerFragment worker;
    ProgressBar progress;
    CakeAdapter adapter;
    List<Cake> cakeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        attachPresenter();
        createCakeList();
        createRetainedStateWorkerClass();
    }

    private void createCakeList() {
        listView.setHasFixedSize(true);
        adapter = new CakeAdapter(cakeList);
        listView.setAdapter(getAdapter());
    }

    private void createRetainedStateWorkerClass() {
        FragmentManager manager = getSupportFragmentManager();
        // check if created
        worker = (WorkerFragment) manager.findFragmentByTag(Constant.TAG_WORKER_FRAGMENT);

        // if not - create one
        if (worker == null) {
            worker = new WorkerFragment();
            manager.beginTransaction().add(worker, Constant.TAG_WORKER_FRAGMENT).commit();
        }
    }

    private void attachPresenter() {
        presenter = new CakePresenter(this);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        listView = (RecyclerView) findViewById(R.id.cake_list_view);
        listView.setLayoutManager(new LinearLayoutManager(this));
        dividerItemDecoration = new DividerItemDecoration(listView.getContext(), DividerItemDecoration.VERTICAL);
        listView.addItemDecoration(dividerItemDecoration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_fetch:
                // Execute background task
                presenter.executeFetchCakeTask();
                break;
        }
        return super.onOptionsItemSelected(item);
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
    public void populateAdapter(List<Cake> cakes) {
        adapter.populate(cakes);
        Log.d(TAG, "populateAdapter: Data received: " + cakes);
    }

    public CakeAdapter getAdapter() {
        return adapter = new CakeAdapter(cakeList);
    }
}