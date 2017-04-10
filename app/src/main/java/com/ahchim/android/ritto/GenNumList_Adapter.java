package com.ahchim.android.ritto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahchim.android.ritto.model.SavedNumber;

import io.realm.RealmResults;

/**
 * Created by Gold on 2017. 4. 10..
 */

public class GenNumList_Adapter extends RecyclerView.Adapter<GenNumList_Adapter.ViewHolder> {

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    private Context mContext;
    RealmResults<SavedNumber> results;

    public static String date = "";
    public static String importDate = null;

    public static boolean isDateInflated = false;

    public static final int VIEW_TYPE_DATE = 3;
    public static final int VIEW_TYPE_NUM = 2;

    public GenNumList_Adapter(Context context, RealmResults<SavedNumber> realmResults){
        mContext = context;
        results = realmResults;
//        Log.e("results","========================" + results);
//        Log.e("results","의 사이즈" + results.size());
    }

    @Override
    public int getItemViewType(int position) {

        importDate = results.get(position).getDate();
        Log.e("importDate","========================" + importDate);

        if(date.equals(importDate)){
            Log.e("date.equals(importDate)","============="+date.equals(importDate));
//            Log.e("getItemViewType","데이트 같을때" + date);
//            Log.e("date","========================" + date);
//            Log.e("results의 date","========================" + results.get(position).getDate());

            isDateInflated = true;
            return VIEW_TYPE_NUM;

        } else if (date.equals(importDate) == false){
            Log.e("date.equals(importDate)","============="+date.equals(importDate));
//            Log.e("getItemViewType","데이트 다를때" + date);
//            Log.e("date","========================" + date);
//            Log.e("results의 date","========================" + results.get(position).getDate());
            //date = results.get(position).getDate();
            //isDateInflated = false;
            return VIEW_TYPE_DATE;
        }

        //return super.getItemViewType(position);
        return position;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case VIEW_TYPE_DATE :
                //Log.e("VIEW_TYPE","======================" + VIEW_TYPE_DATE);
                View viewDate = View.inflate(mContext, R.layout.divider_layout, null);
                ViewHolder viewHolderDate = new ViewHolder(VIEW_TYPE_DATE, viewDate);
                return viewHolderDate;

            case VIEW_TYPE_NUM :
                //Log.e("VIEW_TYPE","======================" + VIEW_TYPE_DATE);
                View viewNum = View.inflate(mContext, R.layout.list_view_item_auto_gen, null);
                ViewHolder viewHolderNum = new ViewHolder(viewNum);
                return viewHolderNum;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (date.equals(importDate) == false){

            //Log.e("isDateInflated","======================" + isDateInflated);

            holder.divider.setText(results.get(position).getDate());
            holder.num1.setText(results.get(position).getNum1() + "");
            holder.num2.setText(results.get(position).getNum2() + "");
            holder.num3.setText(results.get(position).getNum3() + "");
            holder.num4.setText(results.get(position).getNum4() + "");
            holder.num5.setText(results.get(position).getNum5() + "");
            holder.num6.setText(results.get(position).getNum6() + "");
//            Log.e("true되었니?","======================" + isDateInflated);
//            Log.e("divider","======================" + holder.divider.getText());
            date = importDate;
            //Log.e("date 바꿔줬다!!","======================" + date);

        } else if (date.equals(importDate) == true) {

            //Log.e("isDateInflated","======================" + isDateInflated);

            holder.num1.setText(results.get(position).getNum1() + "");
            holder.num2.setText(results.get(position).getNum2() + "");
            holder.num3.setText(results.get(position).getNum3() + "");
            holder.num4.setText(results.get(position).getNum4() + "");
            holder.num5.setText(results.get(position).getNum5() + "");
            holder.num6.setText(results.get(position).getNum6() + "");
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView num1, num2, num3, num4, num5, num6;
        public LinearLayout ll_container;
        public TextView divider;

        public ViewHolder(View itemView) {
            super(itemView);

            //Log.e("그냥 ViewHolder","======================" + isDateInflated);

            ll_container = (LinearLayout) itemView.findViewById(R.id.ll_container_autogen);
            num1 = (TextView) itemView.findViewById(R.id.num1);
            num2 = (TextView) itemView.findViewById(R.id.num2);
            num3 = (TextView) itemView.findViewById(R.id.num3);
            num4 = (TextView) itemView.findViewById(R.id.num4);
            num5 = (TextView) itemView.findViewById(R.id.num5);
            num6 = (TextView) itemView.findViewById(R.id.num6);
        }

        public ViewHolder(int TYPE_DATE, View itemView){
            super(itemView);

            //Log.e("오버로딩 ViewHolder","======================" + isDateInflated);

            divider = (TextView) itemView.findViewById(R.id.tv_divider);
            num1 = (TextView) itemView.findViewById(R.id.num1_adapter);
            num2 = (TextView) itemView.findViewById(R.id.num2_adapter);
            num3 = (TextView) itemView.findViewById(R.id.num3_adapter);
            num4 = (TextView) itemView.findViewById(R.id.num4_adapter);
            num5 = (TextView) itemView.findViewById(R.id.num5_adapter);
            num6 = (TextView) itemView.findViewById(R.id.num6_adapter);
        }
    }
}
