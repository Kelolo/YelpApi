package com.example.cz264.yelptest4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;
import java.util.Objects;

public class searchPageActivity extends AppCompatActivity {

    private TextView testTxt2;
    private boolean isDone = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView business_TitleTv = (TextView)findViewById(R.id.business_Title);
        TextView dealTitleTv = (TextView)findViewById(R.id.dealTitle);
        TextView dealInfoTv = (TextView)findViewById(R.id.dealInfo);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //testTxt2 = (TextView)findViewById(R.id.testTxt2);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        final String imageUrl = intent.getStringExtra("imageUrl");
        String dealTitle = intent.getStringExtra("dealTitle");
        String dealInfo = intent.getStringExtra("dealInfo");

        business_TitleTv.setText(name);
        dealTitleTv.setText(dealTitle);
        dealInfoTv.setText(dealInfo);

        final WebView business_ImageWv = (WebView)findViewById(R.id.business_Image);

        business_ImageWv.loadUrl(imageUrl);



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
