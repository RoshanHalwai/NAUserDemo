package com.kirtanlabs.nammaapartments.nammaapartmentsservices.digitalgate.mydailyservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kirtanlabs.nammaapartments.BaseActivity;
import com.kirtanlabs.nammaapartments.Constants;
import com.kirtanlabs.nammaapartments.R;

import java.util.ArrayList;


public class DailyServicesHome extends BaseActivity {

    private FloatingActionButton fab;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private AlertDialog dialog;
    private Animation rotate_clockwise, rotate_anticlockwise;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_daily_services;
    }

    @Override
    protected int getActivityTitle() {
        return R.string.my_daily_services;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*We need Info Button in this screen*/
        showInfoButton();

        /*Getting Id's for all the views*/
        fab = findViewById(R.id.fab);
        rotate_clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        rotate_anticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        /*Setting event for Floating action button*/
        fab.setOnClickListener(view -> {

            /*Rotating Fab button clockwise*/
            fab.startAnimation(rotate_clockwise);

            /*Custom DialogBox with list of all daily services*/
            AlertDialog.Builder dailyServicesDialog = new AlertDialog.Builder(DailyServicesHome.this);
            View listDailyServices = getLayoutInflater().inflate(R.layout.list_daily_services, null);

            /*Getting Id's for all the listviews*/
            listView = listDailyServices.findViewById(R.id.listViewDailyServices);
            ArrayList<String> servicesList = new ArrayList<>();

            /*Adding daily services to the list*/
            servicesList.add(getString(R.string.cook));
            servicesList.add(getString(R.string.maid));
            servicesList.add(getString(R.string.car_bike_cleaning));
            servicesList.add(getString(R.string.child_day_care));
            servicesList.add(getString(R.string.daily_newspaper));
            servicesList.add(getString(R.string.milk_man));
            servicesList.add(getString(R.string.laundry));
            servicesList.add(getString(R.string.driver));

            /*Creating the Adapter*/
            adapter = new ArrayAdapter<>(DailyServicesHome.this, android.R.layout.simple_list_item_1, servicesList);

            /*Attaching adapter to the listView*/
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            /*Setting event for list view items*/
            listView.setOnItemClickListener((parent, view1, position, id) -> {
                Intent intent = new Intent(DailyServicesHome.this, AddDailyService.class);
                switch (position) {
                    case 0:
                        intent.putExtra(Constants.SERVICE_TYPE, (getString(R.string.cook)));
                        break;
                    case 1:
                        intent.putExtra(Constants.SERVICE_TYPE, (getString(R.string.maid)));
                        break;
                    case 2:
                        intent.putExtra(Constants.SERVICE_TYPE, (getString(R.string.car_bike_cleaner)));
                        break;
                    case 3:
                        intent.putExtra(Constants.SERVICE_TYPE, (getString(R.string.child_care_taker)));
                        break;
                    case 4:
                        intent.putExtra(Constants.SERVICE_TYPE, (getString(R.string.newspaper_paper_man)));
                        break;
                    case 5:
                        intent.putExtra(Constants.SERVICE_TYPE, (getString(R.string.milk_man)));
                        break;
                    case 6:
                        intent.putExtra(Constants.SERVICE_TYPE, (getString(R.string.laundry_man)));
                        break;
                    case 7:
                        intent.putExtra(Constants.SERVICE_TYPE, (getString(R.string.driver)));
                        break;
                }
                startActivity(intent);
                dialog.cancel();
            });
            dailyServicesDialog.setView(listDailyServices);
            dialog = dailyServicesDialog.create();
            dialog.show();

            /*Rotating Fab button to anti-clockwise on canceling my service list*/
            dialog.setOnCancelListener(dialog1 -> fab.startAnimation(rotate_anticlockwise));
        });
    }

}
