package sadykov.asus.slidebar;

import android.app.Fragment;
import android.app.FragmentManager;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;

//import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

//import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.annotation.StringRes;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sadykov.asus.slidebar.JSONpackage.JSONParser;
import sadykov.asus.slidebar.adapters.ExpandableListAdapter;
import sadykov.asus.slidebar.fragments.TablesFragment;
import sadykov.asus.slidebar.fragments.PlotFragment;
import sadykov.asus.slidebar.activities.testCallingActivity;

import static sadykov.asus.slidebar.R.layout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //private FragmentManager myFragmentManager ;//= getSupportFragmentManager();//14.06.2016 14.01
    //private Fragment curFragment;

    ExpandableListAdapter listAdapter;
    ExpandableListView explistView;
    SwipeRefreshLayout mySwipeForExpandableListView;
    List<String[]> listDataHeader;
    HashMap<String,List<String[]>> listDataChild;

    int succes;

    List<String[]> listDataHeader1;
    HashMap<String,List<String[]>> listDataChild1;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";//=  String.getString(R.string.JSONsucces);
    private static final String TAG_BRANCHS  = "branchs";
    private static final String TAG_PID = "pid";
    private static final String TAG_BNAME  = "bname";
    private static final String TAG_BSALE  = "bsale";
    private static final String TAG_BSHOPS = "bshops";
    private static final String TAG_SHOPNAME = "shopname";
    private static final String TAG_SHOPSALE = "shopsale";


    // products JSONArray
    public static JSONArray branchs = null;

    private ProgressDialog pDialog;
    //fragments
    //private Fragment curFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //NEW BEST PRACTICE FROM ASILKHAN SMART WAY(I HOPE)GOD IS GREAT, SORRY ME

        FragmentManager myNewFragmentManager = getFragmentManager();
        myNewFragmentManager.beginTransaction().replace(R.id.content_main, new TablesFragment()).commit();
        Log.d("Lisak","----------newTablesFragment pasted into activity_main.xml layout");


        //OLD PRACTICE LOWER ROWS BE CAREFULL DURACHEK



    //    final SwipeRefreshLayout mySwipeForExpandableListView = (SwipeRefreshLayout) findViewById(R.id.mySwipeCanvasForExpandableListView);
//        final SwipeRefreshLayout mySwipeForExpandableListView = (SwipeRefreshLayout) newTableFragment.getView().findViewById(R.id.mySwipeCanvasForExpandableListView);
        //Log.d("Lisak","SwipeRefreshLayout created successfully");
  //      final ExpandableListView explistView = (ExpandableListView) findViewById(R.id.myExpandableListView);
        //final ExpandableListView explistView = (ExpandableListView) newTableFragment.getView().findViewById(R.id.myExpandableListView);
        //Log.d("Lisak","ExpandableListView created successfully");
        /*new JSONgetMyData().execute();
        mySwipeForExpandableListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new JSONgetMyData().execute();
                //((SwipeRefreshLayout) curFragment.getView().findViewById(R.id.mySwipeCanvasForExpandableListView)).setRefreshing(false);
                mySwipeForExpandableListView.setRefreshing(false);
            }
        });
*/

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void gettingJSONdata(){
        new JSONgetMyData().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager myFragmentManager = getFragmentManager();
        FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
        if (id == R.id.nav_camera) {
            //TablesFragment curFragment = new TablesFragment();

            //myFragmentManager.beginTransaction().replace(R.id.content_main, new TablesFragment()).commit();
            myFragmentTransaction.replace(R.id.content_main,new TablesFragment());
            myFragmentTransaction.commit();
            myFragmentManager.executePendingTransactions();
            showTablesFragment();
            // Handle the camera action
            //Toast.makeText(this,"Берега попутал", Toast.LENGTH_LONG).show();
            Log.d("Перед","объявляем curFragment");
            Log.d("После","curFragment создан");
            Log.d("Перед","объявляем myFragmentManager");
            //FragmentManager myFragmentManager = getSupportFragmentManager();
            Log.d("После","myFragmentManager создан");
            Log.d("Перед","делаем замену одного фрагмента другим");
            //new JSONgetMyData().execute();

            Log.d("После","замена одного фрагмента на другой завершена");


            /* //Использование механизма Activity
            //Intent myIntent = new Intent(this,testCallingActivity.class);
            //startActivity(myIntent);
            */

        } else if (id == R.id.nav_gallery) {

            PlotFragment curFragment = new PlotFragment();
            //FragmentManager myFragmentManager = getSupportFragmentManager();
            myFragmentManager.beginTransaction().replace(R.id.content_main, curFragment).commit();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Showing tables fragment module
        showTablesFragment();
        //Fragment gettedFragment = getFragmentManager().findFragmentById(R.id.content_main); ///getFragmentManager().findFragmentById(R.id.content_main);
        //String logString = (String) ((TextView) gettedFragment.getView().findViewById(R.id.textView2_table_fragment)).getText();
        //Log.d("Lisak","-----------" + logString);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://sadykov.asus.slidebar/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);


    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://sadykov.asus.slidebar/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    private void showTablesFragment(){
        Fragment gettedFragment = getFragmentManager().findFragmentById(R.id.content_main);
        explistView = (ExpandableListView) gettedFragment.getView().findViewById(R.id.myExpandableListView);
        Log.d("Lisak","----------" + explistView);
        mySwipeForExpandableListView = (SwipeRefreshLayout) gettedFragment.getView().findViewById(R.id.mySwipeCanvasForExpandableListView);
        Log.d("Lisak","-------- " + mySwipeForExpandableListView);
        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();
        //explistView.setIndicatorBounds(width-100,width);
        new JSONgetMyData().execute();
        mySwipeForExpandableListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new JSONgetMyData().execute();
                mySwipeForExpandableListView.setRefreshing(false);
            }
        });
    }
    class JSONgetMyData extends AsyncTask<Void,Void,Integer> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Идет загрузка данных...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Integer doInBackground(Void... args) {
            String JsonStringURL = "http://192.168.1.79:83/android_connect/1CTestSale.php";
            String params = "branch=all br";
            listDataHeader = new ArrayList<String[]>();
            listDataChild = new HashMap<String,List<String[]>>();

            JSONParser jParser = new JSONParser();
            JSONObject json = new JSONObject();
            // get json string from url
            try {
                json = (JSONObject) jParser.getJSONFromUrl(JsonStringURL,"GET",params);
                Log.d("all branchs",json.toString());
            }
            catch (Throwable e){
                Log.d("Error json s",e.toString());
                //Toast.makeText(getApplicationContext(),"Отсутствует соединение с сервером",Toast.LENGTH_LONG);
                return 0;
            }

            try {
                // Cheking for SUCCESS_TAG
                if (json == null) {
                    Toast.makeText(getApplicationContext(),"Отсутствует соединение с сервером",Toast.LENGTH_LONG);
                    return 0;
                }
                int success = json.getInt(TAG_SUCCESS);

                if (success==1){
                    //Getting Array of Branchs
                    branchs = json.getJSONArray(TAG_BRANCHS);
                    //looping through All Branchs
                    for (int i = 0; i< branchs.length();i++){
                        JSONObject br = branchs.getJSONObject(i);
                        JSONArray shops = br.getJSONArray(TAG_BSHOPS);
                        listDataHeader.add(new String[2]);
                        listDataHeader.get(i)[0] = br.getString(TAG_BNAME);
                        listDataHeader.get(i)[1] = br.getString(TAG_BSALE);
                        //        Log.d("Lisak parsing","Branch sale: "+br.getString(TAG_BNAME)+" = "+ br.getString(TAG_BSALE));
                        listDataChild.put(listDataHeader.get(i)[0],new ArrayList<String[]>());
                        for (int j = 0; j<shops.length();j++){
                            //List<String> itemShop = new ArrayList<String>();
                            //itemShop.add(String);
                            //itemShop.add(shops.getJSONObject(j).getString(TAG_SHOPNAME));
                            listDataChild.get(listDataHeader.get(i)[0]).add(new String[2]);
                            listDataChild.get(listDataHeader.get(i)[0]).get(j)[0] = shops.getJSONObject(j).getString(TAG_SHOPNAME);
                            listDataChild.get(listDataHeader.get(i)[0]).get(j)[1] = shops.getJSONObject(j).getString(TAG_SHOPSALE);
                            //listDataChild.get(listDataHeader.get(i)[0]).add(shops.getJSONObject(j).getString(TAG_SHOPNAME));

                            //          Log.d("Lisak","Shop sale: " + shops.getJSONObject(j).getString(TAG_SHOPNAME) + " = " + shops.getJSONObject(j).getString(TAG_SHOPSALE));
                        }

                        //JSONObject shops

                    }
                    return success;
                } else {
                    return success;
                }
            }
            catch (JSONException e){
                Log.d("Lisak ","JSON exception"+ e.toString());
            }
            return 0;
        }
        protected void onPostExecute(Integer result){
            //super.onPostExecute();
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            if (result == 0 || result == 404){
                Toast.makeText(getApplicationContext(),"Отсутствует соединение с сервером",Toast.LENGTH_LONG).show();
            }
            else {
                // updating UI from Background Thread
                runOnUiThread(new Runnable() {
                    public void run() {
                        /**
                         * Updating parsed JSON data into */
                        listAdapter = new ExpandableListAdapter(MainActivity.this, listDataHeader, listDataChild);
                        // setting list adapter
                        explistView.setAdapter(listAdapter);
                    }
                });
            }
        }
    }


}
