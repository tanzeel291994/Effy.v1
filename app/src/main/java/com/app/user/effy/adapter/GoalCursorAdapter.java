package com.app.user.effy.adapter;



import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.user.effy.R;
import com.app.user.effy.data.GoalContract.GoalEntry;

import java.util.ArrayList;

public class GoalCursorAdapter extends RecyclerView.Adapter<GoalCursorAdapter.GoalViewHolder> {
    private Cursor mCursor;
    private Context mContext;
    ArrayList<GoalModel> goal_list;
    public interface OnItemClickListener {
        void onItemClick(GoalModel movie_item);
    }

    private final OnItemClickListener listener;

    @Override
    public GoalCursorAdapter.GoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.goal_item, parent, false);
        return new GoalViewHolder(view);
    }


    public GoalCursorAdapter(Context mContext,OnItemClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        goal_list=new ArrayList<GoalModel>();

    }

    @Override
    public void onBindViewHolder(GoalCursorAdapter.GoalViewHolder holder, int position) {
        int goal_name_Index = mCursor.getColumnIndex(GoalEntry.COLUMN_GOAL_NAME);
        int imp_Index = mCursor.getColumnIndex(GoalEntry.COLUMN_IMORTANT);
        int urg_Index = mCursor.getColumnIndex(GoalEntry.COLUMN_URGENT);


        mCursor.moveToPosition(position);

        goal_list.add(new GoalModel(mCursor.getString(goal_name_Index)
                , mCursor.getString(imp_Index),
                mCursor.getString(urg_Index)));
        holder.bind(goal_list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class GoalViewHolder extends RecyclerView.ViewHolder {

        CheckBox chk_imp;
        CheckBox chk_urg;
        TextView goal_name;

        public GoalViewHolder(View itemView) {
            super(itemView);
            chk_imp=(CheckBox)itemView.findViewById(R.id.chk_imp1);
            chk_urg=(CheckBox)itemView.findViewById(R.id.chk_urg1);
            goal_name=(TextView) itemView.findViewById(R.id.goal_name);

        }

        void bind(final GoalModel goal_item, final OnItemClickListener listener) {

            goal_name.setText(goal_item.goal_name);
            //Log.i("tag",goal_item.imp);
           chk_imp.setChecked(Boolean.parseBoolean(goal_item.imp));
            //chk_imp.setChecked(true);
            chk_urg.setChecked(Boolean.parseBoolean(goal_item.urg));

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(goal_item);

                }

            });
        }
    }
}