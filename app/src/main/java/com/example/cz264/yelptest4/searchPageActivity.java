package com.example.cz264.yelptest4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;
import java.util.Objects;
import java.text.DecimalFormat;
import java.math.RoundingMode;

public class searchPageActivity extends AppCompatActivity {

    //private TextView testTxt2;
    //private boolean isDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView business_TitleTv = (TextView)findViewById(R.id.business_Title);
        TextView dealTitleTv = (TextView)findViewById(R.id.dealTitle);
        TextView distTv = (TextView)findViewById(R.id.distTv);
        TextView dealInfoTv = (TextView)findViewById(R.id.dealInfo);
        TextView ratingTv = (TextView)findViewById(R.id.ratingTv);
        TextView dealLink = (TextView)findViewById(R.id.dealLink);
        dealLink.setClickable(true);
        dealLink.setMovementMethod(LinkMovementMethod.getInstance());
        String dealLinkTxt = "<a href='http://www.yelp.com/mobile?source=deal_marketing'>Get it NOW!</a>";
        dealLink.setText(Html.fromHtml(dealLinkTxt));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        final String imageUrl = intent.getStringExtra("imageUrl");
        String dealTitle = intent.getStringExtra("dealTitle");
        String dealInfo = intent.getStringExtra("dealInfo");
        String rating = intent.getStringExtra("rating");

        double userLatitude = Double.parseDouble(intent.getStringExtra("userLatitude"));
        double userLongitude = Double.parseDouble(intent.getStringExtra("userLongitude"));
        double busLatitude = Double.parseDouble(intent.getStringExtra("busLatitude"));
        double busLongitude = Double.parseDouble(intent.getStringExtra("busLongitude"));

        double dist = distance(userLatitude, userLongitude, busLatitude, busLongitude, "M");
        DecimalFormat df2 = new DecimalFormat("#.0");
        distTv.setText("Distance: " + df2.format(dist) + "m");

        String[] spliter = dealInfo.split("Print");

        business_TitleTv.setText(name);
        dealTitleTv.setText(dealTitle);
        dealInfoTv.setText(spliter[0]);

        ratingTv.setText("Rating: " + rating);

        final WebView business_ImageWv = (WebView)findViewById(R.id.business_Image);

        business_ImageWv.loadUrl(imageUrl);
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }


    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
