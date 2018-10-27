package com.kirtanlabs.nammaapartments.services.societyservices.othersocietyservices.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kirtanlabs.nammaapartments.BaseActivity;
import com.kirtanlabs.nammaapartments.R;
import com.kirtanlabs.nammaapartments.home.activities.NammaApartmentsHome;
import com.kirtanlabs.nammaapartments.services.societyservices.othersocietyservices.pojo.NammaApartmentSocietyServices;


import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.kirtanlabs.nammaapartments.utilities.Constants.ALL_SOCIETYSERVICENOTIFICATION_REFERENCE;
import static com.kirtanlabs.nammaapartments.utilities.Constants.CARPENTER;
import static com.kirtanlabs.nammaapartments.utilities.Constants.COMPLETED;
import static com.kirtanlabs.nammaapartments.utilities.Constants.DECLINED;
import static com.kirtanlabs.nammaapartments.utilities.Constants.ELECTRICIAN;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_ACCEPTED;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CANCELLED;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_DATA;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_FULLNAME;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_FUTURE;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_HISTORY;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_MOBILE_NUMBER;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_NOTIFICATIONS;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_PRIVATE;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_SERVING;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_STATUS;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_TAKENBY;
import static com.kirtanlabs.nammaapartments.utilities.Constants.GARBAGE_COLLECTION;
import static com.kirtanlabs.nammaapartments.utilities.Constants.IN_PROGRESS;
import static com.kirtanlabs.nammaapartments.utilities.Constants.NOTIFICATION_UID;
import static com.kirtanlabs.nammaapartments.utilities.Constants.PLUMBER;
import static com.kirtanlabs.nammaapartments.utilities.Constants.RATING;
import static com.kirtanlabs.nammaapartments.utilities.Constants.SCREEN_TITLE;
import static com.kirtanlabs.nammaapartments.utilities.Constants.SOCIETY_ADMIN_DETAILS_REFERENCE;
import static com.kirtanlabs.nammaapartments.utilities.Constants.SOCIETY_SERVICES_REFERENCE;
import static com.kirtanlabs.nammaapartments.utilities.Constants.SOCIETY_SERVICE_TYPE;
import static com.kirtanlabs.nammaapartments.utilities.Constants.setLatoBoldFont;
import static com.kirtanlabs.nammaapartments.utilities.Constants.setLatoLightFont;
import static com.kirtanlabs.nammaapartments.utilities.Constants.setLatoRegularFont;

/**
 * KirtanLabs Pvt. Ltd.
 * Created by Ashish Jha on 7/26/2018
 */

public class AwaitingResponse extends BaseActivity {

    /*----------------------------------------------
     *Private Members
     *-----------------------------------------------*/

    private LinearLayout layoutAwaitingResponse, layoutAcceptedResponse, layoutRequestDeclined;
    private TextView textSocietyServiceNameValue, textMobileNumberValue, textEndOTPValue;
    private DatabaseReference societyServiceNotificationReference;
    private String notificationUID, societyServiceType, societyServiceUID;
    private Button buttonCallService, buttonCancelService;

    /*----------------------------------------------------
     *  Overriding BaseActivity Objects
     *----------------------------------------------------*/

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_awaiting_response;
    }

    @Override
    protected int getActivityTitle() {
        return R.string.awaiting_response_title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Getting Id's for all the views*/
        layoutAwaitingResponse = findViewById(R.id.layoutAwaitingResponse);
        layoutAcceptedResponse = findViewById(R.id.layoutAcceptedResponse);
        layoutRequestDeclined = findViewById(R.id.layoutRequestDeclined);
        TextView textNotificationSent = findViewById(R.id.textNotificationSent);
        TextView textSocietyServiceAcceptedRequest = findViewById(R.id.textSocietyServiceAcceptedRequest);
        TextView textSocietyServiceNameAndEventTitle = findViewById(R.id.textSocietyServiceName);
        TextView textMobileNumberAndEventDate = findViewById(R.id.textMobileNumber);
        TextView textEndOTPAndTimeSlot = findViewById(R.id.textEndOTP);
        textSocietyServiceNameValue = findViewById(R.id.textSocietyServiceNameValue);
        textMobileNumberValue = findViewById(R.id.textMobileNumberValue);
        textEndOTPValue = findViewById(R.id.textEndOTPValue);
        TextView textSocietyServiceResponse = findViewById(R.id.textSocietyServiceResponse);
        buttonCallService = findViewById(R.id.buttonCallService);
        buttonCancelService = findViewById(R.id.buttonCancelService);

        /*Setting font for all the views*/
        textNotificationSent.setTypeface(setLatoBoldFont(this));
        textSocietyServiceNameAndEventTitle.setTypeface(setLatoRegularFont(this));
        textMobileNumberAndEventDate.setTypeface(setLatoRegularFont(this));
        textEndOTPAndTimeSlot.setTypeface(setLatoRegularFont(this));
        textSocietyServiceNameValue.setTypeface(setLatoBoldFont(this));
        textMobileNumberValue.setTypeface(setLatoBoldFont(this));
        textEndOTPValue.setTypeface(setLatoBoldFont(this));
        textSocietyServiceAcceptedRequest.setTypeface(setLatoBoldFont(this));
        textSocietyServiceResponse.setTypeface(setLatoBoldFont(this));
        buttonCallService.setTypeface(setLatoLightFont(this));
        buttonCancelService.setTypeface(setLatoLightFont(this));

        String societyServiceNameTitle = getString(R.string.name) + ":";
        textSocietyServiceNameAndEventTitle.setText(societyServiceNameTitle);
        String societyServiceMobileTitle = getString(R.string.mobile) + ":";
        textMobileNumberAndEventDate.setText(societyServiceMobileTitle);

        notificationUID = getIntent().getStringExtra(NOTIFICATION_UID);
        societyServiceType = getIntent().getStringExtra(SOCIETY_SERVICE_TYPE);
        societyServiceNotificationReference = ALL_SOCIETYSERVICENOTIFICATION_REFERENCE.child(notificationUID);

        /*Based On The Society Service Type we are differentiating appropriate string*/
        switch (societyServiceType) {
            case PLUMBER:
                textSocietyServiceResponse.setText(getString(R.string.no_response_plumber));
                break;
            case CARPENTER:
                textSocietyServiceResponse.setText(getString(R.string.no_response_carpenter));
                break;
            case ELECTRICIAN:
                textSocietyServiceResponse.setText(getString(R.string.no_response_electrician));
                break;
            case GARBAGE_COLLECTION:
                textSocietyServiceResponse.setText(getString(R.string.no_response_garbage));
                break;
        }
        showProgressIndicator();
        /*This method is used to check status of user's latest request of that particular Society Service*/
        checkUserRequestStatus();
    }

    /*----------------------------------------------
     *Private Methods
     *-----------------------------------------------*/

    /**
     * This method is invoked to check the status of user's latest request and taken action accordingly.
     */
    private void checkUserRequestStatus() {
        if (notificationUID != null) {
            DatabaseReference requestStatusReference = societyServiceNotificationReference.child(FIREBASE_CHILD_STATUS);
            requestStatusReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    /*Getting status of user's latest request*/
                    String status = dataSnapshot.getValue(String.class);
                    switch (Objects.requireNonNull(status)) {
                        case IN_PROGRESS:
                        case FIREBASE_ACCEPTED:
                            checkSocietyServiceResponse();
                            break;
                        case COMPLETED:
                            rateSocietyService();
                            break;
                        case DECLINED:
                            showNoSocietyServiceAvailableLayout(societyServiceType);
                            break;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    /**
     * This dialog gets invoked when user clicks on Cancel button.
     */
    private void showCancelDialog() {
        Runnable cancelService = this::cancelSocietyService;
        String confirmDialogTitle = getString(R.string.cancel_dialog_title);
        String confirmDialogMessage = getString(R.string.cancel_question);
        showConfirmDialog(confirmDialogTitle, confirmDialogMessage, cancelService);
    }

    /**
     * This method is invoked when user presses on OK in the dialog box to cancel the society service
     */
    private void cancelSocietyService() {
        /*Getting notificationUID reference under 'societyServiceNotifications'*/
        societyServiceNotificationReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String takenBy = dataSnapshot.child(FIREBASE_CHILD_TAKENBY).getValue(String.class);
                /*Getting reference till 'notification' key*/
                DatabaseReference societyServiceUIDReference = SOCIETY_SERVICES_REFERENCE.child(societyServiceType)
                        .child(FIREBASE_CHILD_PRIVATE).child(FIREBASE_CHILD_DATA).child(Objects.requireNonNull(takenBy)).child(FIREBASE_CHILD_NOTIFICATIONS);
                societyServiceUIDReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(FIREBASE_CHILD_SERVING).child(notificationUID).exists()) {
                            /*Removing data related to that particular notificationUID from 'serving' if user cancels a service*/
                            societyServiceUIDReference.child(FIREBASE_CHILD_SERVING).child(notificationUID)
                                    .removeValue().addOnCompleteListener(task -> societyServiceUIDReference.child(FIREBASE_CHILD_FUTURE)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @SuppressWarnings("unchecked")
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot1) {
                                            if (dataSnapshot1.exists()) {
                                                Map<String, String> futureUIDMap = (Map<String, String>) dataSnapshot1.getValue();
                                                Long lastIndex = dataSnapshot1.getChildrenCount() - 1;
                                                String futureServiceKey = (String) Objects.requireNonNull(futureUIDMap).keySet().toArray()[lastIndex.intValue()];
                                                String futureServiceUID = futureUIDMap.get(futureServiceKey);
                                                /*Moving a card from 'future' to 'serving'*/
                                                societyServiceUIDReference.child(FIREBASE_CHILD_SERVING).child(futureServiceUID).setValue(FIREBASE_ACCEPTED);
                                                /*Removing the UID from 'future' after it is placed in 'serving'*/
                                                societyServiceUIDReference.child(FIREBASE_CHILD_FUTURE).child(futureServiceKey).removeValue();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    }));

                        } else if (dataSnapshot.child(FIREBASE_CHILD_FUTURE).child(notificationUID).exists()) {
                            /*Removing data of the cancelled notification from 'future' after user cancels service*/
                            societyServiceUIDReference.child(FIREBASE_CHILD_FUTURE).child(notificationUID)
                                    .removeValue();
                        }
                        /*Moving the cancelled request to Society Service History*/
                        societyServiceUIDReference.child(FIREBASE_CHILD_HISTORY).child(notificationUID).setValue(FIREBASE_CANCELLED);
                        /*Setting 'Cancelled' value in the 'status' key inside societyServiceNotifications for the cancelled notification id*/
                        DatabaseReference statusReference = ALL_SOCIETYSERVICENOTIFICATION_REFERENCE
                                .child(notificationUID).child(FIREBASE_CHILD_STATUS);
                        statusReference.setValue(FIREBASE_CANCELLED);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*Navigating to Home screen once user cancels service*/
        startActivity(new Intent(AwaitingResponse.this, NammaApartmentsHome.class));
    }

    /**
     * This method is used to Retrieve the details of Society Society, if users request is accepted.
     */
    private void checkSocietyServiceResponse() {
        /*Getting the reference till 'notificationUID' in societyServices in Firebase*/
        societyServiceNotificationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NammaApartmentSocietyServices nammaApartmentSocietyServices = dataSnapshot.getValue(NammaApartmentSocietyServices.class);
                if (Objects.requireNonNull(nammaApartmentSocietyServices).getTakenBy() != null && nammaApartmentSocietyServices.getEndOTP() != null) {
                    societyServiceUID = nammaApartmentSocietyServices.getTakenBy();
                    String endOTP = nammaApartmentSocietyServices.getEndOTP();
                    DatabaseReference societyServiceDataReference = SOCIETY_SERVICES_REFERENCE
                            .child(societyServiceType)
                            .child(FIREBASE_CHILD_PRIVATE)
                            .child(FIREBASE_CHILD_DATA)
                            .child(Objects.requireNonNull(societyServiceUID));
                    societyServiceDataReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            hideProgressIndicator();
                            layoutAwaitingResponse.setVisibility(View.GONE);
                            layoutAcceptedResponse.setVisibility(View.VISIBLE);

                            String societyServiceName = dataSnapshot.child(FIREBASE_CHILD_FULLNAME).getValue(String.class);
                            String societyServiceMobileNumber = dataSnapshot.child(FIREBASE_CHILD_MOBILE_NUMBER).getValue(String.class);
                            textSocietyServiceNameValue.setText(societyServiceName);
                            textMobileNumberValue.setText(societyServiceMobileNumber);
                            textEndOTPValue.setText(endOTP);
                            /*User can call the Society Service to enquire about the status*/
                            buttonCallService.setOnClickListener(v -> makePhoneCall(societyServiceMobileNumber));
                            buttonCancelService.setOnClickListener(v -> showCancelDialog());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method is invoked to rate Society service just after user's request for is completed.
     */
    private void rateSocietyService() {
        DatabaseReference rateReference = societyServiceNotificationReference.child(RATING);
        rateReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    layoutAcceptedResponse.setVisibility(View.GONE);
                    openRateSocietyServiceDialog();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method is used to Open Dialog box which is used to rate society service and Store that rating in firebase which is given by user.
     */
    private void openRateSocietyServiceDialog() {
        View rateServiceDialog = View.inflate(this, R.layout.layout_rate_society_service, null);

        /*Getting Id's for all the views*/
        TextView textRateExperience = rateServiceDialog.findViewById(R.id.textRateExperience);
        TextView textRecentSocietyService = rateServiceDialog.findViewById(R.id.textRecentSocietyService);
        CircleImageView imageRecentSocietyService = rateServiceDialog.findViewById(R.id.imageRecentSocietyService);
        RatingBar ratingBarSocietyService = rateServiceDialog.findViewById(R.id.ratingBarSocietyService);
        Button buttonSubmit = rateServiceDialog.findViewById(R.id.buttonSubmit);

        /*Setting font for all the views*/
        textRateExperience.setTypeface(setLatoBoldFont(this));
        textRecentSocietyService.setTypeface(setLatoBoldFont(this));
        buttonSubmit.setTypeface(setLatoRegularFont(this));

        /*Setting SocietyService text and Image of societyServiceType according to user society service Request*/
        switch (societyServiceType) {
            case PLUMBER:
                imageRecentSocietyService.setImageResource(R.drawable.plumbers_na);
                textRecentSocietyService.setText(R.string.plumber);
                break;
            case CARPENTER:
                imageRecentSocietyService.setImageResource(R.drawable.carpenter_na);
                textRecentSocietyService.setText(R.string.carpenter);
                break;
            case ELECTRICIAN:
                imageRecentSocietyService.setImageResource(R.drawable.electrician_na);
                textRecentSocietyService.setText(R.string.electrician);
                break;
            case GARBAGE_COLLECTION:
                imageRecentSocietyService.setImageResource(R.drawable.garbage_collection_na);
                textRecentSocietyService.setText(R.string.garbage_collection);
                break;
        }

        AlertDialog.Builder alertRateServiceDialog = new AlertDialog.Builder(this);
        alertRateServiceDialog.setView(rateServiceDialog);
        AlertDialog dialog = alertRateServiceDialog.create();
        dialog.setCancelable(false);

        new Dialog(AwaitingResponse.this);
        if (!AwaitingResponse.this.isFinishing()) {
            dialog.show();
        }

        /*Setting onClickListener for view*/
        buttonSubmit.setOnClickListener(v -> {
            float rating = ratingBarSocietyService.getRating();
            /*Setting the rating given by the user in (societyServiceNotification->NotificationUID) in firebase*/
            societyServiceNotificationReference.child(RATING).setValue(rating);

            /*Setting the rating given by the user in (societyService->societyServiceType->societyServiceUID) in firebase*/
            DatabaseReference averageRatingReference = SOCIETY_SERVICES_REFERENCE.child(societyServiceType)
                    .child(FIREBASE_CHILD_PRIVATE)
                    .child(FIREBASE_CHILD_DATA)
                    .child(societyServiceUID);

            averageRatingReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int number = (int) dataSnapshot.child(FIREBASE_CHILD_NOTIFICATIONS).child(FIREBASE_CHILD_HISTORY).getChildrenCount();
                    float previousAverageRating = Objects.requireNonNull(dataSnapshot.child(RATING).getValue(Float.class));
                    float previousRatingValue = (previousAverageRating * (number - 1));
                    float newAverageRating = (rating + previousRatingValue) / number;
                    averageRatingReference.child(RATING).setValue(newAverageRating);
                    dialog.cancel();
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        });
    }

    /**
     * This method is invoked when the status of particular society service notification
     * changes to "Declined"
     *
     * @param societyServiceType - type of society service
     */
    private void showNoSocietyServiceAvailableLayout(final String societyServiceType) {
        /*Getting Id's for all the views*/
        TextView textNoSocietyServiceAvailable = findViewById(R.id.textNoSocietyServiceAvailable);
        Button buttonRequestAgain = findViewById(R.id.buttonRequestAgain);
        TextView textCallAssociation = findViewById(R.id.textCallAssociation);

        textCallAssociation.setPaintFlags(textCallAssociation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        /*Setting font for all the views*/
        textNoSocietyServiceAvailable.setTypeface(setLatoBoldFont(this));
        buttonRequestAgain.setTypeface(setLatoLightFont(this));
        textCallAssociation.setTypeface(setLatoBoldFont(this));

        /*Setting text to the view*/
        String serviceType;
        if (societyServiceType.equals(GARBAGE_COLLECTION)) {
            serviceType = getString(R.string.garbage_collection);
        } else {
            serviceType = societyServiceType.substring(0, 1).toUpperCase() + societyServiceType.substring(1);
        }
        String noSocietyServiceAvailable = getString(R.string.no_society_service_available).replace(getString(R.string.service), serviceType);
        textNoSocietyServiceAvailable.setText(noSocietyServiceAvailable);

        layoutAwaitingResponse.setVisibility(View.GONE);
        layoutAcceptedResponse.setVisibility(View.GONE);
        layoutRequestDeclined.setVisibility(View.VISIBLE);

        /*Setting on Click listeners to the view*/
        buttonRequestAgain.setOnClickListener(v -> openSocietyServiceHomeScreen());
        textCallAssociation.setOnClickListener(v -> {
            AwaitingResponse.this.showProgressDialog(AwaitingResponse.this, getString(R.string.retrieving_details), getString(R.string.please_wait_a_moment));
            retrieveSocietyAdminContactNumber();
        });
    }

    /**
     * This method is invoked to retrieve the contact number of Society admin and place to a call.
     */
    private void retrieveSocietyAdminContactNumber() {
        SOCIETY_ADMIN_DETAILS_REFERENCE.child(FIREBASE_CHILD_MOBILE_NUMBER).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String societyAdminContactNumber = dataSnapshot.getValue(String.class);
                hideProgressDialog();
                makePhoneCall(societyAdminContactNumber);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method is invoked to open Society Service Home Screen
     */
    private void openSocietyServiceHomeScreen() {
        int screenTitle = 0;
        /*Based On The Society Service Type we navigate user to particular society service screen*/
        switch (societyServiceType) {
            case PLUMBER:
                screenTitle = R.string.plumber;
                break;
            case CARPENTER:
                screenTitle = R.string.carpenter;
                break;
            case ELECTRICIAN:
                screenTitle = R.string.electrician;
                break;
            case GARBAGE_COLLECTION:
                screenTitle = R.string.garbage_collection;
                break;
        }
        Intent intent = new Intent(AwaitingResponse.this, SocietyServicesHome.class);
        intent.putExtra(SCREEN_TITLE, screenTitle);
        startActivity(intent);
        finish();
    }
}
