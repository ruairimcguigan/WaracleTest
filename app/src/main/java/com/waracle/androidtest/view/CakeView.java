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

import com.waracle.androidtest.presenter.Interactors.CakeInteractor;
import com.waracle.androidtest.R;
import com.waracle.androidtest.list.CakeAdapter;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.presenter.CakePresenter;
import com.waracle.androidtest.presenter.WorkerFragmentPresenter;
import com.waracle.androidtest.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import static com.waracle.androidtest.utils.Constant.LIST_STATE;

public class CakeView extends AppCompatActivity implements CakeInteractor.CakeViewInteractor {
    public static final String TAG = CakeView.class.getSimpleName();

    DividerItemDecoration dividerItemDecoration;
    RecyclerView listView;
    CakePresenter presenter;
    Toolbar toolbar;
    WorkerFragmentPresenter worker;
    ProgressBar progress;
    CakeAdapter adapter;
    ArrayList<Cake> cakeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        attachPresenter();
        createRetainedStateWorkerClass();
        initViews();
        if (savedInstanceState != null) {
            provideRetainedParcelData(savedInstanceState);
        }
        createCakeList();
    }

    private void provideRetainedParcelData(Bundle savedInstanceState) {
        // Retrieve list state and list/item positions
        ArrayList<Cake> retrievedList = savedInstanceState.getParcelableArrayList(LIST_STATE);
        if (retrievedList != null) {
            for (Cake cake : retrievedList) {
                cakeList.add(cake);
            }
        }
    }

    private void createCakeList() {
        listView = (RecyclerView) findViewById(R.id.cake_list_view);
        listView.setLayoutManager(new LinearLayoutManager(this));
        dividerItemDecoration = new DividerItemDecoration(listView.getContext(), DividerItemDecoration.VERTICAL);
        listView.addItemDecoration(dividerItemDecoration);
        listView.setHasFixedSize(true);
        adapter = new CakeAdapter(cakeList);
        listView.setAdapter(adapter);
    }

    private void createRetainedStateWorkerClass() {
        FragmentManager manager = getSupportFragmentManager();
        // check if created
        worker = (WorkerFragmentPresenter) manager.findFragmentByTag(Constant.TAG_WORKER_FRAGMENT);

        // if not - create one
        if (worker == null) {
            worker = new WorkerFragmentPresenter();
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
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelableArrayList(LIST_STATE, cakeList);
    }

    @Override
    public void populateAdapter(List<Cake> cakes) {
        adapter.populate(cakes);
        Log.d(TAG, "populateAdapter: Data received: " + cakes);
    }

}