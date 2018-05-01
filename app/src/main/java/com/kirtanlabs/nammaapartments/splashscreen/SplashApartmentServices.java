package com.kirtanlabs.nammaapartments.splashscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kirtanlabs.nammaapartments.Constants;
import com.kirtanlabs.nammaapartments.R;

import java.util.Objects;

public class SplashApartmentServices extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.splash_apartment_services, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textApartmentServices = view.findViewById(R.id.textApartmentServices);
        TextView textApartmentServicesDesc = view.findViewById(R.id.textApartmentServicesDesc);
        Button buttonGetStarted = view.findViewById(R.id.buttonGetStarted);
        textApartmentServices.setTypeface(Constants.setLatoBoldFont(Objects.requireNonNull(this.getActivity())));
        textApartmentServicesDesc.setTypeface(Constants.setLatoRegularFont(Objects.requireNonNull(this.getActivity())));
        buttonGetStarted.setTypeface(Constants.setLatoLightFont(this.getActivity()));
    }
}
