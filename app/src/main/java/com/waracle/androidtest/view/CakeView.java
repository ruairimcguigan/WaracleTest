package com.waracle.androidtest.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.waracle.androidtest.Interactors.CakeInteractor;
import com.waracle.androidtest.R;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.presenter.CakePresenter;


public class CakeView extends AppCompatActivity implements CakeInteractor.CakeViewInteractor{

    public static final String TAG = CakeView.class.getSimpleName();
    RecyclerView listView;
    CakePresenter presenter;
    Toolbar toolbar;
    CakePresenter.WorkerFragment worker;
    public static final String TAG_WORKER_FRAGMENT = "worker_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initMenuBar();
        attachPresenter();
        FragmentManager manager = getSupportFragmentManager();
        worker = (CakePresenter.WorkerFragment) manager.findFragmentByTag(TAG_WORKER_FRAGMENT);

        //if the fragment is non-null, then it is currently being retained across a config change
        if (worker == null){
            worker = new CakePresenter.WorkerFragment();
            Log.d(TAG, "initWorker: Worker Fragment created!");
            manager.beginTransaction().add(worker, TAG_WORKER_FRAGMENT).commit();
            Toast.makeText(this, "Worker Frag created!", Toast.LENGTH_SHORT).show();
        }


//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
    }

    private void initMenuBar() {
      toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void attachPresenter() {
        presenter = new CakePresenter(this);
    }

    private void initViews() {
        listView = (RecyclerView) findViewById(R.id.cake_list_view);
        listView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_fetch:
                // Execute background task
                presenter.executeTask();
                break;
            case R.id.action_refresh:
                // refresh list
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        Toast.makeText(this, "Fetching data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        Toast.makeText(this, "Data Received", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView(Cake cake) {
        Log.d(TAG, "updateView: Data received: " + cake);
        Toast.makeText(this, "Cake = " + cake, Toast.LENGTH_SHORT).show();
    }
//
//    /**
//     * Fragment is responsible for loading in some JSON and
//     * then displaying a list of cakes with images.
//     * Fix any crashes
//     * Improve any performance issues
//     * Use good coding practices to make code more secure
//     */
//    public static class PlaceholderFragment extends ListFragment {
//
//        private static final String TAG = PlaceholderFragment.class.getSimpleName();
//
//        private ListView mListView;
//        private MyAdapter mAdapter;
//
//        public PlaceholderFragment() { /**/ }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            mListView = (ListView) rootView.findViewById(R.id.cake_list_view);
//            return rootView;
//        }
//
//        @Override
//        public void onActivityCreated(Bundle savedInstanceState) {
//            super.onActivityCreated(savedInstanceState);
//
//            // Create and set the list adapter.
//            mAdapter = new MyAdapter();
//            mListView.setAdapter(mAdapter);
//
//            // Load data from net.
//            try {
//                JSONArray array = loadData();
//                mAdapter.setItems(array);
//            } catch (IOException | JSONException e) {
//                Log.e(TAG, e.getMessage());
//            }
//        }
//
//
//        private JSONArray loadData() throws IOException, JSONException {
//            URL url = new URL(Constant.buildJSONURL());
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            try {
//                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//
//                // Can you think of a way to improve the performance of loading data
//                // using HTTP headers???
//
//                // Also, Do you trust any utils thrown your way????
//
//                byte[] bytes = StreamUtils.readUnknownFully(in);
//
//                // Read in charset of HTTP content.
//                String charset = parseCharset(urlConnection.getRequestProperty("Content-Type"));
//
//                // Convert byte array to appropriate encoded string.
//                String jsonText = new String(bytes, charset);
//
//                // Read string as JSON.
//                return new JSONArray(jsonText);
//            } finally {
//                urlConnection.disconnect();
//            }
//        }
//
//        /**
//         * Returns the charset specified in the Content-Type of this header,
//         * or the HTTP default (ISO-8859-1) if none can be found.
//         */
//        public static String parseCharset(String contentType) {
//            if (contentType != null) {
//                String[] params = contentType.split(",");
//                for (int i = 1; i < params.length; i++) {
//                    String[] pair = params[i].trim().split("=");
//                    if (pair.length == 2) {
//                        if (pair[0].equals("charset")) {
//                            return pair[1];
//                        }
//                    }
//                }
//            }
//            return "UTF-8";
//        }
//
//        private class MyAdapter extends BaseAdapter {
//
//            // Can you think of a better way to represent these items???
//            private JSONArray mItems;
//            private ImageLoader mImageLoader;
//
//            public MyAdapter() {
//                this(new JSONArray());
//            }
//
//            public MyAdapter(JSONArray items) {
//                mItems = items;
//                mImageLoader = new ImageLoader();
//            }
//
//            @Override
//            public int getCount() {
//                return mItems.length();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                try {
//                    return mItems.getJSONObject(position);
//                } catch (JSONException e) {
//                    Log.e("", e.getMessage());
//                }
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @SuppressLint("ViewHolder")
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                LayoutInflater inflater = LayoutInflater.from(getActivity());
//                View root = inflater.inflate(R.layout.list_item_layout, parent, false);
//                if (root != null) {
//                    TextView title = (TextView) root.findViewById(R.id.title);
//                    TextView desc = (TextView) root.findViewById(R.id.desc);
//                    ImageView image = (ImageView) root.findViewById(R.id.image);
//                    try {
//                        JSONObject object = (JSONObject) getItem(position);
//                        title.setText(object.getString("title"));
//                        desc.setText(object.getString("desc"));
//                        mImageLoader.load(object.getString("image"), image);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                return root;
//            }
//
//            public void setItems(JSONArray items) {
//                mItems = items;
//            }
//        }
//    }
}
