package com.kirtanlabs.nammaapartments.nammaapartmentsservices.digitalgate.notifydigitalgate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kirtanlabs.nammaapartments.BaseActivity;
import com.kirtanlabs.nammaapartments.R;

import static com.kirtanlabs.nammaapartments.Constants.HANDED_THINGS_TO;
import static com.kirtanlabs.nammaapartments.Constants.setLatoBoldFont;
import static com.kirtanlabs.nammaapartments.Constants.setLatoLightFont;
import static com.kirtanlabs.nammaapartments.Constants.setLatoRegularFont;

public class HandedThings extends BaseActivity implements View.OnClickListener {

    /* ------------------------------------------------------------- *
     * Private Members
     * ------------------------------------------------------------- */

    private int handed_Things_To;
    private TextView textVisitorAndServiceName;
    private TextView textInvitationDateAndRating;
    private TextView textInvitedByAndApartmentNo;
    private TextView textVisitorNameAndServiceNameValue;
    private TextView textVisitorAndServiceTypeValue;
    private TextView textInvitationDateAndRatingValue;
    private TextView textInvitedByAndApartmentNoValue;
    private TextView textDescription;
    private EditText editDescription;
    private Button buttonYes;
    private Button buttonNo;
    private Button buttonNotifyGate;
    private CardView cardViewVisitors;

    /* ------------------------------------------------------------- *
     * Overriding BaseActivity Methods
     * ------------------------------------------------------------- */

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_handed_things;
    }

    @Override
    protected int getActivityTitle() {
        /*We use a common class for Handed Things to my Guest and handed Things to my Daily Services, we set the title
         * based on the user click on NotifyGate Home screen*/
        if (getIntent().getIntExtra(HANDED_THINGS_TO, 0) == R.string.handed_things_to_my_guest) {
            handed_Things_To = R.string.handed_things_to_my_guest;
        } else {
            handed_Things_To = R.string.handed_things_to_my_daily_services;
        }
        return handed_Things_To;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*We need Info Button in this screen*/
        showInfoButton();

        /*We need Progress Indicator in this screen*/
        showProgressIndicator();

        //TODO: Write business logic to check if there are any visitors at resident house.
        /* If there are no visitors at resident house then we show
         * feature unavailable layout and pass some sensible message*/
        /*if (visitorCount == 0) {
            showFeatureUnavailableLayout(R.string.feature_unavailable_message);
        }*/

        /* We show current visitors list at resident house, so resident has
         * the ability to give things to their visitors and notify gate about it*/

        /*Initialising the CardView*/
        cardViewVisitors = findViewById(R.id.cardViewVisitors);

        /*Initialising all the views*/
        textVisitorAndServiceName = findViewById(R.id.textVisitorAndServiceName);
        TextView textVisitorAndServiceType = findViewById(R.id.textVisitorAndServiceType);
        textInvitationDateAndRating = findViewById(R.id.textInvitationDate);
        TextView textInvitationTime = findViewById(R.id.textInvitationTime);
        textInvitedByAndApartmentNo = findViewById(R.id.textInvitedByAndApartmentNo);
        textVisitorNameAndServiceNameValue = findViewById(R.id.textVisitorAndServiceNameValue);
        textVisitorAndServiceTypeValue = findViewById(R.id.textVisitorAndServiceTypeValue);
        textInvitationDateAndRatingValue = findViewById(R.id.textInvitationDateValue);
        textInvitedByAndApartmentNoValue = findViewById(R.id.textInvitedByAndApartmentNoValue);
        TextView textGivenThings = findViewById(R.id.textGivenThings);
        textDescription = findViewById(R.id.textDescription);
        editDescription = findViewById(R.id.editDescription);
        buttonNotifyGate = findViewById(R.id.buttonNotifyGate);
        buttonYes = findViewById(R.id.buttonYes);
        buttonNo = findViewById(R.id.buttonNo);

        /*Setting fonts to the views*/
        textVisitorAndServiceName.setTypeface(setLatoRegularFont(this));
        textVisitorAndServiceType.setTypeface(setLatoRegularFont(this));
        textInvitationDateAndRating.setTypeface(setLatoRegularFont(this));
        textInvitationTime.setTypeface(setLatoRegularFont(this));
        textInvitedByAndApartmentNo.setTypeface(setLatoRegularFont(this));

        textVisitorNameAndServiceNameValue.setTypeface(setLatoBoldFont(this));
        textVisitorAndServiceTypeValue.setTypeface(setLatoBoldFont(this));
        textInvitationDateAndRatingValue.setTypeface(setLatoBoldFont(this));
        textInvitedByAndApartmentNoValue.setTypeface(setLatoBoldFont(this));

        textGivenThings.setTypeface(setLatoBoldFont(this));
        textDescription.setTypeface(setLatoBoldFont(this));
        editDescription.setTypeface(setLatoRegularFont(this));
        buttonYes.setTypeface(setLatoRegularFont(this));
        buttonNo.setTypeface(setLatoRegularFont(this));
        buttonNotifyGate.setTypeface(setLatoLightFont(this));

        /*Setting events for views*/
        buttonYes.setOnClickListener(this);
        buttonNo.setOnClickListener(this);
        buttonNotifyGate.setOnClickListener(this);

        /*Since we are using same layout for handed things to my guest and handed things to my daily services we need to
         * change some Titles in layout*/
        changeTitles();
    }

    /* ------------------------------------------------------------- *
     * Overriding OnClick Listeners
     * ------------------------------------------------------------- */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonYes:
                textDescription.setVisibility(View.VISIBLE);
                editDescription.setVisibility(View.VISIBLE);
                buttonNotifyGate.setVisibility(View.VISIBLE);
                editDescription.requestFocus();
                hideKeyboard();
                buttonYes.setBackgroundResource(R.drawable.button_guest_selected);
                buttonNo.setBackgroundResource(R.drawable.button_guest_not_selected);
                buttonYes.setTextColor(Color.WHITE);
                buttonNo.setTextColor(Color.BLACK);
                break;
            case R.id.buttonNo:
                textDescription.setVisibility(View.GONE);
                editDescription.setVisibility(View.GONE);
                buttonNotifyGate.setVisibility(View.GONE);
                hideKeyboard();
                buttonYes.setBackgroundResource(R.drawable.button_guest_not_selected);
                buttonNo.setBackgroundResource(R.drawable.button_guest_selected);
                buttonYes.setTextColor(Color.BLACK);
                buttonNo.setTextColor(Color.WHITE);
                break;

            case R.id.buttonNotifyGate:
                if (isAllFieldsFilled(new EditText[]{editDescription})) {
                    createNotifyGateDialog();
                } else {
                    editDescription.setError(getString(R.string.please_fill_details));
                }
                break;
        }
    }

    /* ------------------------------------------------------------- *
     * Private Methods
     * ------------------------------------------------------------- */

    /**
     * Since we are using same layout for handed things to my guest and handed things to my daily services we need to
     * change some Titles in layout
     */
    private void changeTitles() {
        if (handed_Things_To == R.string.handed_things_to_my_daily_services) {
            hideProgressIndicator();
            cardViewVisitors.setVisibility(View.VISIBLE);
            String stringServiceName = getResources().getString(R.string.name) + ":";
            textVisitorAndServiceName.setText(stringServiceName);
            textVisitorNameAndServiceNameValue.setText("Ramesh");
            textVisitorAndServiceTypeValue.setText(R.string.cook);
            textInvitationDateAndRating.setText(R.string.rating);
            textInvitationDateAndRatingValue.setText("4.2");
            textInvitedByAndApartmentNo.setText(R.string.flats);
            textInvitedByAndApartmentNoValue.setText("3");
        }
    }

}
