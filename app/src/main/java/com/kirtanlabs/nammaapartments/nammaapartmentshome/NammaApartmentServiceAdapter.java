package com.kirtanlabs.nammaapartments.nammaapartmentshome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirtanlabs.nammaapartments.R;

import java.util.List;

class NammaApartmentServiceAdapter extends ArrayAdapter<NammaApartmentService> {

    /* ------------------------------------------------------------- *
     * Private Members
     * ------------------------------------------------------------- */

    private final List<NammaApartmentService> servicesList;
    private final Context context;
    private final int resource;

    /* ------------------------------------------------------------- *
     * Constructors
     * ------------------------------------------------------------- */

    NammaApartmentServiceAdapter(@NonNull Context context, List<NammaApartmentService> servicesList) {
        super(context, R.layout.list_services);
        this.servicesList = servicesList;
        this.context = context;
        this.resource = R.layout.list_services;
    }

    /* ------------------------------------------------------------- *
     * Overriding ArrayAdapter Objects
     * ------------------------------------------------------------- */

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource, parent, false);

            ImageView imageServiceIcon = convertView.findViewById(R.id.imageServiceIcon);
            TextView textServiceName = convertView.findViewById(R.id.textServiceName);
            NammaApartmentService nammaApartmentService = servicesList.get(position);
            imageServiceIcon.setImageDrawable(context.getResources().getDrawable(nammaApartmentService.getServiceImage()));
            textServiceName.setText(nammaApartmentService.getServiceName());
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return servicesList.size();
    }

}