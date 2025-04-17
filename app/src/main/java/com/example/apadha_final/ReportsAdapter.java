package com.example.apadha_final;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ReportViewHolder> {

    private Cursor cursor;

    public ReportsAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each report item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            // Bind the data from cursor to the ViewHolder
            String disaster = cursor.getString(cursor.getColumnIndex(ReportsDatabaseHelper.COLUMN_DISASTER));
            String mediaUri = cursor.getString(cursor.getColumnIndex(ReportsDatabaseHelper.COLUMN_MEDIA_URI));
            String timestamp = cursor.getString(cursor.getColumnIndex(ReportsDatabaseHelper.COLUMN_TIMESTAMP));

            holder.disasterTextView.setText(disaster);
            holder.mediaUriTextView.setText(mediaUri);
            holder.timestampTextView.setText(timestamp);
        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView disasterTextView;
        TextView mediaUriTextView;
        TextView timestampTextView;

        public ReportViewHolder(View itemView) {
            super(itemView);
            disasterTextView = itemView.findViewById(R.id.reportDisaster);
            mediaUriTextView = itemView.findViewById(R.id.reportMediaUri);
            timestampTextView = itemView.findViewById(R.id.reportTimestamp);
        }
    }
}
