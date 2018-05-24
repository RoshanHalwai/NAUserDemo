package com.kirtanlabs.nammaapartments.nammaapartmentsservices.digitalgate.myvisitorslist;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.kirtanlabs.nammaapartments.BaseActivity;
import com.kirtanlabs.nammaapartments.Constants;
import com.kirtanlabs.nammaapartments.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/**
 * KirtanLabs Pvt. Ltd.
 * Created by Roshan Halwai on 5/5/2018
 */
public class VisitorsListAdapter extends RecyclerView.Adapter<VisitorsListAdapter.VisitorViewHolder> implements View.OnClickListener {

    /* ------------------------------------------------------------- *
     * Private Members
     * ------------------------------------------------------------- */

    private final Context mCtx;
    private final BaseActivity baseActivity;
    private View rescheduleDialog;
    private View cancelDialog;
    private EditText editPickDate;
    private EditText editPickTime;
    private String selectedDate = "";
    private String selectedTime = "";
    private AlertDialog dialog;
    private int count = 5;

    /* ------------------------------------------------------------- *
     * Constructor
     * ------------------------------------------------------------- */

    VisitorsListAdapter(Context mCtx) {
        this.mCtx = mCtx;
        baseActivity = (BaseActivity) mCtx;
    }

    /* ------------------------------------------------------------- *
     * Overriding RecyclerView.Adapter Objects
     * ------------------------------------------------------------- */

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_visitors_and_my_daily_services_list, parent, false);
        return new VisitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
        holder.textVisitorName.setTypeface(Constants.setLatoRegularFont(mCtx));
        holder.textVisitorType.setTypeface(Constants.setLatoRegularFont(mCtx));
        holder.textInvitationDateOrServiceRating.setTypeface(Constants.setLatoRegularFont(mCtx));
        holder.textInvitationTime.setTypeface(Constants.setLatoRegularFont(mCtx));
        holder.textInvitedByOrNumberOfFlats.setTypeface(Constants.setLatoRegularFont(mCtx));

        holder.textVisitorNameValue.setTypeface(Constants.setLatoBoldFont(mCtx));
        holder.textVisitorTypeValue.setTypeface(Constants.setLatoBoldFont(mCtx));
        holder.textInvitationDateOrServiceRatingValue.setTypeface(Constants.setLatoBoldFont(mCtx));
        holder.textInvitationTimeValue.setTypeface(Constants.setLatoBoldFont(mCtx));
        holder.textInvitedByOrNumberOfFlatsValue.setTypeface(Constants.setLatoBoldFont(mCtx));

        holder.textCall.setTypeface(Constants.setLatoRegularFont(mCtx));
        holder.textMessage.setTypeface(Constants.setLatoRegularFont(mCtx));
        holder.textReschedule.setTypeface(Constants.setLatoRegularFont(mCtx));
        holder.textCancel.setTypeface(Constants.setLatoRegularFont(mCtx));

        holder.textCall.setOnClickListener(this);
        holder.textMessage.setOnClickListener(this);
        holder.textReschedule.setOnClickListener(this);
        holder.textCancel.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        //TODO: To change the get item count here
        return count;
    }

    /* ------------------------------------------------------------- *
     * Visitor View Holder class
     * ------------------------------------------------------------- */
    class VisitorViewHolder extends RecyclerView.ViewHolder {

        /* ------------------------------------------------------------- *
         * Private Members
         * ------------------------------------------------------------- */

        private final TextView textVisitorName;
        private final TextView textVisitorNameValue;
        private final TextView textVisitorType;
        private final TextView textVisitorTypeValue;
        private final TextView textInvitationDateOrServiceRating;
        private final TextView textInvitationDateOrServiceRatingValue;
        private final TextView textInvitationTime;
        private final TextView textInvitationTimeValue;
        private final TextView textInvitedByOrNumberOfFlats;
        private final TextView textInvitedByOrNumberOfFlatsValue;

        private final TextView textCall;
        private final TextView textMessage;
        private final TextView textReschedule;
        private final TextView textCancel;

        /* ------------------------------------------------------------- *
         * Constructor
         * ------------------------------------------------------------- */

        VisitorViewHolder(View itemView) {
            super(itemView);
            textVisitorName = itemView.findViewById(R.id.textVisitorOrServiceName);
            textVisitorType = itemView.findViewById(R.id.textVisitorOrServiceType);
            textInvitationDateOrServiceRating = itemView.findViewById(R.id.textInvitationDateOrServiceRating);
            textInvitationTime = itemView.findViewById(R.id.textInvitationTime);
            textInvitedByOrNumberOfFlats = itemView.findViewById(R.id.textInvitedByOrNumberOfFlats);

            textVisitorNameValue = itemView.findViewById(R.id.textVisitorOrServiceNameValue);
            textVisitorTypeValue = itemView.findViewById(R.id.textVisitorOrServiceTypeValue);
            textInvitationDateOrServiceRatingValue = itemView.findViewById(R.id.textInvitationDateOrServiceRatingValue);
            textInvitationTimeValue = itemView.findViewById(R.id.textInvitationTimeValue);
            textInvitedByOrNumberOfFlatsValue = itemView.findViewById(R.id.textInvitedByOrNumberOfFlatsValue);

            textCall = itemView.findViewById(R.id.textCall);
            textMessage = itemView.findViewById(R.id.textMessage);
            textReschedule = itemView.findViewById(R.id.textRescheduleOrEdit);
            textCancel = itemView.findViewById(R.id.textCancel);
        }

    }
    /* ------------------------------------------------------------- *
     * Overriding OnClick Listeners
     * ------------------------------------------------------------- */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textCall:
                //TODO: Change Mobile Number here
                baseActivity.makePhoneCall("9885665744");
                break;
            case R.id.textMessage:
                //TODO: Change Mobile Number here
                baseActivity.sendTextMessage("9885665744");
                break;
            case R.id.textRescheduleOrEdit:
                openRescheduleDialog();
                break;
            case R.id.textCancel:
                openCancelDialog();
                break;
            case R.id.editPickDate:
                displayDate();
                break;
            case R.id.editPickTime:
                displayTime();
                break;
            case R.id.buttonReschedule:
                dialog.cancel();
                break;
            case R.id.buttonCancel:
                dialog.cancel();
                break;
        }
    }

    /*-------------------------------------------------------------------------------
     *Private Methods
     *-----------------------------------------------------------------------------*/
    /**
     * This method is invoked when user clicks on reschedule icon.
     */
    private void openRescheduleDialog() {
        rescheduleDialog = View.inflate(mCtx, R.layout.layout_dialog_reschedule, null);

        /*Getting Id's for all the views*/
        editPickDate = rescheduleDialog.findViewById(R.id.editPickDate);
        editPickTime = rescheduleDialog.findViewById(R.id.editPickTime);
        TextView textPickDate = rescheduleDialog.findViewById(R.id.textPickDate);
        TextView textPickTime = rescheduleDialog.findViewById(R.id.textPickTime);
        TextView buttonReschedule = rescheduleDialog.findViewById(R.id.buttonReschedule);
        TextView buttonCancel = rescheduleDialog.findViewById(R.id.buttonCancel);

        /*Setting Fonts for all the views*/
        textPickDate.setTypeface(Constants.setLatoRegularFont(mCtx));
        textPickTime.setTypeface(Constants.setLatoRegularFont(mCtx));
        buttonReschedule.setTypeface(Constants.setLatoRegularFont(mCtx));
        buttonCancel.setTypeface(Constants.setLatoRegularFont(mCtx));

        /*We don't want the keyboard to be displayed when user clicks on the pick date and time edit fields*/
        editPickDate.setInputType(InputType.TYPE_NULL);
        editPickTime.setInputType(InputType.TYPE_NULL);

        /*This method is used to create reschedule dialog */
        createRescheduleDialog();

        /*Setting OnClick Listeners to the views*/
        editPickDate.setOnClickListener(this);
        editPickTime.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        buttonReschedule.setOnClickListener(this);
    }

    /**
     * This method is invoked to create a reschedule dialog.
     */
    private void createRescheduleDialog() {
        AlertDialog.Builder alertRescheduleDialog = new AlertDialog.Builder(mCtx);
        alertRescheduleDialog.setView(rescheduleDialog);
        dialog = alertRescheduleDialog.create();

        new Dialog(mCtx);
        dialog.show();
    }

    /**
     * This method is invoked when user clicks on cancel icon.
     */
    private void openCancelDialog() {
        cancelDialog = View.inflate(mCtx, R.layout.layout_dialog_cancel, null);

        /*Getting Id's for all the views*/
        TextView textCancelDescription = cancelDialog.findViewById(R.id.textCancelDescription);

        /*Setting Fonts for all the views*/
        textCancelDescription.setTypeface(Constants.setLatoRegularFont(mCtx));

        /*This method is used to create cancel dialog */
        createCancelDialog();
    }

    /**
     * This method is invoked to create a cancel dialog.
     */
    private void createCancelDialog() {
        AlertDialog.Builder alertCancelDialog = new AlertDialog.Builder(mCtx);
        alertCancelDialog.setTitle("Delete");
        alertCancelDialog.setPositiveButton("Yes", (dialog, which) -> deleteVisitorData());
        alertCancelDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertCancelDialog.setView(cancelDialog);
        dialog = alertCancelDialog.create();

        new Dialog(mCtx);
        dialog.show();
    }

    /**
     * This method is invoked to delete visitor data.
     */
    private void deleteVisitorData() {
        notifyItemRemoved(0);
        /*Decrementing the count variable on deletion of one visitor data.*/
        --count;
        /*After deletion of one row we are notifying the adapter*/
        notifyDataSetChanged();
        dialog.cancel();
    }

    /**
     * This method is invoked when user clicks on pick date icon.
     */
    private void displayDate() {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Date Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(mCtx,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    selectedDate = "";
                    selectedDate = new DateFormatSymbols().getMonths()[month].substring(0, 3) + " " + dayOfMonth + ", " + year;
                    editPickDate.setText(selectedDate);
                    editPickTime.requestFocus();
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    /**
     * This method is invoked when user clicks on pick time icon.
     */
    private void displayTime() {
        Calendar calendar = Calendar.getInstance();
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);

        // Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(mCtx,
                (view, hourOfDay, minute) -> {
                    selectedTime = "";
                    selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    editPickTime.setText(selectedTime);
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

}
