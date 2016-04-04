package com.example.cz264.yelptest4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Deal;
import com.yelp.clientlib.entities.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import retrofit.Call;
import retrofit.Response;

public class MainActivity extends AppCompatActivity {

    private TextView testTxt;
    private Button testBt;
    private int flag = 0;
    private boolean found = false;
    private Response<SearchResponse> response;
    private Object lock;
    private ArrayList<Business> business;
    private Business rmBusiness;
    private ArrayList<String> business_Name;
    private ArrayList<Double> business_Distance;
    private ArrayList<String> business_image;
    /**
     * for every business
     * ArrayList<Category> categories();
     * ArrayList<Deal> deals();
     * ArrayList<GiftCertificate> giftCertificates();
     * ArrayList<Review> reviews();
     */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lock = new Object();
        testTxt = (TextView) findViewById(R.id.testTxt2);

        testBt = (Button) findViewById(R.id.testBt);
        testBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            synchronized (lock){
                while (found == false){
                    try {
                        testTxt.setText("Loading...");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //startActivity(new Intent(getApplicationContext(), searchPageActivity.class));
                //testTxt.setText(response.body().businesses().toString());
                business = response.body().businesses();
            }
                if (business != null){
                    Intent intent = new Intent(getApplicationContext(), searchPageActivity.class);
                    //intent.putExtra("business", business);

                    Random rm = new Random();
                    rmBusiness =  business.get(rm.nextInt(business.size()));
                    ArrayList<Deal> deals = rmBusiness.deals();
                    String dealTitle = deals.get(rm.nextInt(deals.size())).title();
                    String dealInfo = deals.get(rm.nextInt(deals.size())).whatYouGet();



                    intent.putExtra("name", rmBusiness.name());
                    intent.putExtra("imageUrl", rmBusiness.imageUrl());
                    intent.putExtra("dealTitle", dealTitle);
                    intent.putExtra("dealInfo", dealInfo);

                    startActivity(intent);
                }


            }
        });

        new Thread(new Runnable(){

            @Override
            public void run() {
                testTxt.setText("Running");
                YelpAPIFactory apiFactory = new YelpAPIFactory("xK9j0pwr7D41XNvbLp6T-Q", "lMGEVb8SAgdDurSz93bTgfArM14",
                        "PVdMOZycbckRD5YZgcxITlbmek4uIo6Y", "GnuITPnq0sSZ4TRqbcAreYc81Oc");

                YelpAPI yelpAPI = apiFactory.createAPI();

                Map<String, String> params = new HashMap<String, String>();
                params.put("term", "food");
                //params.put("limit", "3");
                params.put("deals_filter", "true");

                Call<SearchResponse> call = yelpAPI.search("San Francisco", params);

                try {
                    response = call.execute();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                synchronized (lock){
                    found = true;
                    lock.notify();
                }


            }
        }).start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
