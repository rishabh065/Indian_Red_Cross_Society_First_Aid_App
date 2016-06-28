package bppc.com.firebasetest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import bppc.com.firebasetest.Data.Pojo;

public class FirstActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    ArrayList<Pojo> Category = new ArrayList<>();
    Firebase ref, ref1;
    private GoogleApiClient client;
    RecyclerView recyclerView;
    CategoryAdapter adapter1;
    RecyclerView.LayoutManager mLayoutManager;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onResume();
//        LoadImages loadImages=new LoadImages(FirstActivity.this);
//          loadImages.execute();
            if (!Firebase.getDefaultConfig().isPersistenceEnabled())
                Firebase.getDefaultConfig().setPersistenceEnabled(true);
            Firebase.setAndroidContext(this);
            setContentView(R.layout.activity_first);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            ref = new Firebase("https://project-7104573469224225532.firebaseio.com/");
            ref.keepSynced(true);
            adapter1 = new CategoryAdapter(this, Category);

            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mLayoutManager = new GridLayoutManager(FirstActivity.this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter1);
            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Category.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        final Pojo pojo = new Pojo();
                        pojo.setValue(data.getKey());
                        String string = "https://project-7104573469224225532.firebaseio.com/" + data.getKey() + "/url";
//                    System.out.println(string);
                        ref1 = new Firebase(string);
                        ref1.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Pojo pojo1 = new Pojo();
                                pojo1.setValue(pojo.getValue());
                                //System.out.println(pojo1.getValue());
                                pojo1.setUrl((String) dataSnapshot.getValue());
//                            System.out.println(pojo1.getUrl());
                                Category.add(pojo1);
                                adapter1.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }

                        });

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            PhoneCallListener phoneListener = new PhoneCallListener();
            TelephonyManager telephonyManager = (TelephonyManager) this
                    .getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            assert fab != null;
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:112"));
                    startActivity(callIntent);
                }
            });
            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView sv =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter1.getFilter().filter(query);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "First Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://bppc.com.firebasetest/http/host/path")
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
                "First Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://bppc.com.firebasetest/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


        private class PhoneCallListener extends PhoneStateListener
        {

            private boolean isPhoneCalling = false;

            // String LOG_TAG = "LOGGING 123";

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {

                if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                    // active
                    finish();
                    //Log.i(LOG_TAG, "OFFHOOK");

                    isPhoneCalling = true;
                }

                if (TelephonyManager.CALL_STATE_IDLE == state) {
                    if (isPhoneCalling) {
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage(
                                        getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                        isPhoneCalling = false;
                    }

                }
            }
        }
    }


