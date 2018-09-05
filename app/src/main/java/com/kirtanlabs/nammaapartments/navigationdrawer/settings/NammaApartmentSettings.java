package com.kirtanlabs.nammaapartments.navigationdrawer.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kirtanlabs.nammaapartments.BaseActivity;
import com.kirtanlabs.nammaapartments.NammaApartmentsGlobal;
import com.kirtanlabs.nammaapartments.R;
import com.kirtanlabs.nammaapartments.onboarding.login.SignIn;

import java.util.Objects;

import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_NOTIFICATION_SOUND;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_NOTIFICATION_SOUND_CAB;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_NOTIFICATION_SOUND_DAILYSERVICE;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_NOTIFICATION_SOUND_EINTERCOM;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_NOTIFICATION_SOUND_GUEST;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_NOTIFICATION_SOUND_PACKAGE;
import static com.kirtanlabs.nammaapartments.utilities.Constants.FIREBASE_CHILD_OTHER_DETAILS;
import static com.kirtanlabs.nammaapartments.utilities.Constants.LOGGED_IN;
import static com.kirtanlabs.nammaapartments.utilities.Constants.NAMMA_APARTMENTS_PREFERENCE;
import static com.kirtanlabs.nammaapartments.utilities.Constants.PRIVATE_USERS_REFERENCE;
import static com.kirtanlabs.nammaapartments.utilities.Constants.USER_UID;
import static com.kirtanlabs.nammaapartments.utilities.Constants.setLatoBoldFont;
import static com.kirtanlabs.nammaapartments.utilities.Constants.setLatoRegularFont;

public class NammaApartmentSettings extends BaseActivity implements View.OnClickListener {

    /* ------------------------------------------------------------- *
     * Private Members
     * ------------------------------------------------------------- */

    private Switch eIntercomNotifications, switchGuestNotification, switchDailyServiceNotification, switchCabNotification, switchPackageNotification;
    private String userUID;
    private DatabaseReference userNotificationSoundReference;

    /* ------------------------------------------------------------- *
     * Overriding BaseActivity Objects
     * ------------------------------------------------------------- */

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_namma_apartment_settings;
    }

    @Override
    protected int getActivityTitle() {
        return R.string.action_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Getting Id's for all the views*/
        TextView textSoundSettings = findViewById(R.id.textSoundSettings);
        Button buttonSignOut = findViewById(R.id.buttonSignOut);
        eIntercomNotifications = findViewById(R.id.eIntercomNotifications);
        switchGuestNotification = findViewById(R.id.switchGuestNotification);
        switchDailyServiceNotification = findViewById(R.id.switchDailyServiceNotification);
        switchCabNotification = findViewById(R.id.switchCabNotification);
        switchPackageNotification = findViewById(R.id.switchPackageNotification);

        /*Setting font for all the views*/
        textSoundSettings.setTypeface(setLatoBoldFont(this));
        buttonSignOut.setTypeface(setLatoRegularFont(this));
        eIntercomNotifications.setTypeface(setLatoRegularFont(this));
        switchGuestNotification.setTypeface(setLatoRegularFont(this));
        switchDailyServiceNotification.setTypeface(setLatoRegularFont(this));
        switchCabNotification.setTypeface(setLatoRegularFont(this));
        switchPackageNotification.setTypeface(setLatoRegularFont(this));

        /*Settings On Click listeners to Views*/
        buttonSignOut.setOnClickListener(this);
        eIntercomNotifications.setOnClickListener(this);
        switchGuestNotification.setOnClickListener(this);
        switchDailyServiceNotification.setOnClickListener(this);
        switchCabNotification.setOnClickListener(this);
        switchPackageNotification.setOnClickListener(this);

        /*Getting the user id*/
        userUID = NammaApartmentsGlobal.userUID;

        /*This method retrieves the notification sound values*/
        retrieveNotificationSoundDetailsFromFireBase();

    }

    /* ------------------------------------------------------------- *
     * Overriding On Click Listener Method
     * ------------------------------------------------------------- */
    @Override
    public void onClick(View v) {
        userNotificationSoundReference = PRIVATE_USERS_REFERENCE.child(userUID).child(FIREBASE_CHILD_OTHER_DETAILS).child(FIREBASE_CHILD_NOTIFICATION_SOUND);
        switch (v.getId()) {
            case R.id.buttonSignOut:
                showLogOutDialog();
                break;
            case R.id.eIntercomNotifications:
                if (!eIntercomNotifications.isChecked()) {
                    userNotificationSoundReference.child(FIREBASE_CHILD_NOTIFICATION_SOUND_EINTERCOM).setValue(false);
                } else {
                    userNotificationSoundReference.child(FIREBASE_CHILD_NOTIFICATION_SOUND_EINTERCOM).setValue(true);
                }
                break;
            case R.id.switchGuestNotification:
                if (!switchGuestNotification.isChecked()) {
                    userNotificationSoundReference.child(FIREBASE_CHILD_NOTIFICATION_SOUND_GUEST).setValue(false);
                } else {
                    userNotificationSoundReference.child(FIREBASE_CHILD_NOTIFICATION_SOUND_GUEST).setValue(true);
                }
                break;
            case R.id.switchDailyServiceNotification:
                if (!switchDailyServiceNotification.isChecked()) {
                    userNotificationSoundReference.child(FIREBASE_CHILD_NOTIFICATION_SOUND_DAILYSERVICE).setValue(false);
                } else {
                    userNotificationSoundReference.child(FIREBASE_CHILD_NOTIFICATION_SOUND_DAILYSERVICE).setValue(true);
                }
                break;
            case R.id.switchCabNotification:
                if (!switchCabNotification.isChecked()) {
                    userNotificationSoundReference.child(FIREBASE_CHILD_NOTIFICATION_SOUND_CAB).setValue(false);
                } else {
                    userNotificationSoundReference.child(FIREBASE_CHILD_NOTIFICATION_SOUND_CAB).setValue(true);
                }
                break;
            case R.id.switchPackageNotification:
                if (!switchPackageNotification.isChecked()) {
                    userNotificationSoundReference.child(FIREBASE_CHILD_NOTIFICATION_SOUND_PACKAGE).setValue(false);
                } else {
                    userNotificationSoundReference.child(FIREBASE_CHILD_NOTIFICATION_SOUND_PACKAGE).setValue(true);
                }
                break;
        }
    }
    /* ------------------------------------------------------------- *
     * Private Methods
     * ------------------------------------------------------------- */

    /**
     * This dialog gets invoked when user clicks on Logout button.
     */
    private void showLogOutDialog() {
        Runnable logoutUser = () ->
        {
            SharedPreferences sharedPreferences;
            SharedPreferences.Editor editor;
            sharedPreferences = getSharedPreferences(NAMMA_APARTMENTS_PREFERENCE, MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putBoolean(LOGGED_IN, false);
            editor.putString(USER_UID, null);
            editor.apply();

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(NammaApartmentSettings.this, SignIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        };
        String confirmDialogTitle = getString(R.string.logout_dialog_title);
        String confirmDialogMessage = getString(R.string.logout_question);
        showConfirmDialog(confirmDialogTitle, confirmDialogMessage, logoutUser);
    }

    /**
     * This method gets invoked based on the firebase values for notification sounds and will display
     * appropriate switch checked values.
     */
    private void retrieveNotificationSoundDetailsFromFireBase() {
        userNotificationSoundReference = PRIVATE_USERS_REFERENCE.child(userUID).child(FIREBASE_CHILD_OTHER_DETAILS).child(FIREBASE_CHILD_NOTIFICATION_SOUND);
        userNotificationSoundReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cabValue = Objects.requireNonNull(dataSnapshot.child(FIREBASE_CHILD_NOTIFICATION_SOUND_CAB).getValue()).toString();
                String dailyServiceValue = Objects.requireNonNull(dataSnapshot.child(FIREBASE_CHILD_NOTIFICATION_SOUND_DAILYSERVICE).getValue()).toString();
                String eIntercomValue = Objects.requireNonNull(dataSnapshot.child(FIREBASE_CHILD_NOTIFICATION_SOUND_EINTERCOM).getValue()).toString();
                String guestValue = Objects.requireNonNull(dataSnapshot.child(FIREBASE_CHILD_NOTIFICATION_SOUND_GUEST).getValue()).toString();
                String packageValue = Objects.requireNonNull(dataSnapshot.child(FIREBASE_CHILD_NOTIFICATION_SOUND_PACKAGE).getValue()).toString();
                /*Based on the values we are differentiating switch values*/
                if (cabValue.equals(getString(R.string.notification_sound_value))) {
                    switchCabNotification.setChecked(true);
                } else {
                    switchCabNotification.setChecked(false);
                }
                if (dailyServiceValue.equals(getString(R.string.notification_sound_value))) {
                    switchDailyServiceNotification.setChecked(true);
                } else {
                    switchDailyServiceNotification.setChecked(false);
                }
                if (eIntercomValue.equals(getString(R.string.notification_sound_value))) {
                    eIntercomNotifications.setChecked(true);
                } else {
                    eIntercomNotifications.setChecked(false);
                }
                if (guestValue.equals(getString(R.string.notification_sound_value))) {
                    switchGuestNotification.setChecked(true);
                } else {
                    switchGuestNotification.setChecked(false);
                }
                if (packageValue.equals(getString(R.string.notification_sound_value))) {
                    switchPackageNotification.setChecked(true);
                } else {
                    switchPackageNotification.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
