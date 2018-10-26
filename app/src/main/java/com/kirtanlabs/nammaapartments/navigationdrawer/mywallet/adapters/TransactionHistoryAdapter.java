package com.kirtanlabs.nammaapartments.navigationdrawer.mywallet.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirtanlabs.nammaapartments.R;
import com.kirtanlabs.nammaapartments.navigationdrawer.mywallet.activities.TransactionSummaryActivity;
import com.kirtanlabs.nammaapartments.navigationdrawer.mywallet.pojo.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.kirtanlabs.nammaapartments.utilities.Constants.SERVICE_TYPE;
import static com.kirtanlabs.nammaapartments.utilities.Constants.setLatoBoldFont;
import static com.kirtanlabs.nammaapartments.utilities.Constants.setLatoRegularFont;

/**
 * KirtanLabs Pvt. Ltd.
 * Created by Roshan Halwai on 9/8/2018
 */
public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryViewHolder> {

    /* ------------------------------------------------------------- *
     * Private Members
     * ------------------------------------------------------------- */

    private final Context mCtx;
    private final List<Transaction> transactionList;

    /* ------------------------------------------------------------- *
     * Constructor
     * ------------------------------------------------------------- */

    public TransactionHistoryAdapter(Context mCtx, List<Transaction> transactionList) {
        this.mCtx = mCtx;
        this.transactionList = transactionList;
    }

    /* ------------------------------------------------------------- *
     * Overriding RecyclerView.Adapter Methods
     * ------------------------------------------------------------- */

    @NonNull
    @Override
    public TransactionHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*inflating and returning our view holder*/
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_transaction_history, parent, false);
        return new TransactionHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        String serviceCategory = transaction.getServiceCategory();
        String amount = mCtx.getString(R.string.rupees_symbol) + " " + String.valueOf(transaction.getAmount());
        SimpleDateFormat sfd = new SimpleDateFormat("EEE, MMM dd, HH:mm", Locale.US);
        String formattedDateAndTime = sfd.format(new Date(transaction.getTimestamp()));
        if (transaction.getResult().equals(mCtx.getString(R.string.successful))) {
            holder.imageTransactionResult.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.request_accepted_na));
        } else {
            holder.imageTransactionResult.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.remove_new));
        }
        holder.textAmount.setText(amount);
        holder.textServiceCategory.setText(serviceCategory);
        holder.textDateAndTime.setText(formattedDateAndTime);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    class TransactionHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /* ------------------------------------------------------------- *
         * Private Members
         * ------------------------------------------------------------- */

        private final TextView textAmount;
        private final TextView textServiceCategory;
        private final TextView textDateAndTime;
        private final ImageView imageTransactionResult;
        private final CardView transactionHistoryView;

        /* ------------------------------------------------------------- *
         * Constructor
         * ------------------------------------------------------------- */

        TransactionHistoryViewHolder(View itemView) {
            super(itemView);

            /*Getting Id's for all the views*/
            textAmount = itemView.findViewById(R.id.textAmount);
            textServiceCategory = itemView.findViewById(R.id.textServiceCategory);
            textDateAndTime = itemView.findViewById(R.id.textDateAndTime);
            imageTransactionResult = itemView.findViewById(R.id.imageTransactionResult);
            transactionHistoryView = itemView.findViewById(R.id.transaction_history_view);

            /*Setting Fonts for all the views on cardView*/
            textAmount.setTypeface(setLatoBoldFont(mCtx));
            textServiceCategory.setTypeface(setLatoRegularFont(mCtx));
            textDateAndTime.setTypeface(setLatoRegularFont(mCtx));

            /*Setting On Click listeners to the view*/
            transactionHistoryView.setOnClickListener(this);
        }

        /* ------------------------------------------------------------- *
         * Overriding OnClick Listener Objects
         * ------------------------------------------------------------- */

        @Override
        public void onClick(View v) {
            sendTransactionData();
        }

        /* ------------------------------------------------------------- *
         * Private Method
         * ------------------------------------------------------------- */

        /**
         * This method sends the required transaction data from 'My Transactions' screen to 'Transaction
         * summary' screen through Intents.
         */
        private void sendTransactionData() {
            int position = getLayoutPosition();
            Transaction transaction = transactionList.get(position);
            SimpleDateFormat sfd = new SimpleDateFormat("EEE, MMM dd, HH:mm", Locale.US);
            String formattedDateAndTime = sfd.format(new Date(transaction.getTimestamp()));
            String amount = mCtx.getString(R.string.rupees_symbol) + " " + String.valueOf(transaction.getAmount());
            String status = transaction.getResult();
            String period = transaction.getPeriod();
            Intent transactionSummaryIntent = new Intent(mCtx, TransactionSummaryActivity.class);
            transactionSummaryIntent.putExtra(mCtx.getString(R.string.paymentId), transaction.getPaymentId());
            transactionSummaryIntent.putExtra(mCtx.getString(R.string.amount), amount);
            transactionSummaryIntent.putExtra(mCtx.getString(R.string.dateAndTime), formattedDateAndTime);
            transactionSummaryIntent.putExtra(mCtx.getString(R.string.payment_status), status);
            transactionSummaryIntent.putExtra(SERVICE_TYPE, transaction.getServiceCategory());
            if (!transaction.getServiceCategory().equals(mCtx.getString(R.string.event_management))) {
                transactionSummaryIntent.putExtra(mCtx.getString(R.string.period), period);
            }
            mCtx.startActivity(transactionSummaryIntent);
        }
    }
}
