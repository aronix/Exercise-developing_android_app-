package com.example.android.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myapplication.data.dataContract;

import org.w3c.dom.Text;

/**
 * Created by han sb on 2017-01-14.
 */

public class dataAdapter extends RecyclerView.Adapter<dataAdapter.dataViewHolder>{


    private final Context mContext;
    private Cursor mCursor;
    public dataAdapter(@NonNull Context context){
        mContext= context;
    }

    public void SwapCursor(Cursor cursor){
        mCursor=cursor;
        notifyDataSetChanged();
    }

    @Override
    public dataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.data_list_item,parent,false);
        view.setFocusable(true);


        return new dataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(dataViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        final int id= mCursor.getInt(mCursor.getColumnIndex(dataContract.WishEntry._ID));
        String date = mCursor.getString(mCursor.getColumnIndex(dataContract.WishEntry.COLUMN_DATE));
        String wish = mCursor.getString(mCursor.getColumnIndex(dataContract.WishEntry.COLUMN_WISH));
        String lotto = mCursor.getString(mCursor.getColumnIndex(dataContract.WishEntry.COLUMN_LOTTO));
        holder.itemView.setTag(id);
        holder.wishView.setText(wish);
        holder.lottoView.setText(lotto);
        holder.dateView.setText(String.valueOf(id));

    }

    @Override
    public int getItemCount() {
        if(mCursor==null){
            return 0;
        }
        return mCursor.getCount();
    }

    class dataViewHolder extends RecyclerView.ViewHolder {

        final TextView wishView;
        final TextView lottoView;
        final TextView dateView;
        public dataViewHolder(View itemView) {
            super(itemView);
            wishView =(TextView)itemView.findViewById(R.id.tv_wish);
            lottoView=(TextView)itemView.findViewById(R.id.tv_lotto);
            dateView = (TextView)itemView.findViewById(R.id.tv_date);
        }


    }
}
