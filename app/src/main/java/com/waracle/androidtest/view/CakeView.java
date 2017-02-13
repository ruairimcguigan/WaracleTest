package com.waracle.androidtest.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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

import static android.R.attr.fragment;

public class CakeView extends AppCompatActivity implements CakeInteractor.CakeViewInteractor {

    public static final String TAG = CakeView.class.getSimpleName();
    Toolbar toolbar;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        createCakeListView(savedInstanceState);
    }

    private void createCakeListView(Bundle savedInstanceState) {
        // First time init, create the UI.
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,
                    new CakeListView(), "listFrag").commit();
        }
    }

    private void initViews() {
        progress = (ProgressBar)findViewById(R.id.progressBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                executeCakeTask();
                break;
            case R.id.action_refresh:
                // refresh list
        }
        return super.onOptionsItemSelected(item);
    }

    public void executeCakeTask() {
        Fragment cakeListView = getSupportFragmentManager().findFragmentByTag("listFrag");
        if (cakeListView instanceof CakeListView) {
            ((CakeListView) cakeListView).executeTask();
        }
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

    }
}
