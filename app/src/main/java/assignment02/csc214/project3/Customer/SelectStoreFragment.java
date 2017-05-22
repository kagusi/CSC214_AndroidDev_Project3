package assignment02.csc214.project3.Customer;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import assignment02.csc214.project3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectStoreFragment extends Fragment {

    public interface SelectStoreUpdate{
        public void storeSelected(String storeName);
    }

    private SelectStoreUpdate mSelectStoreUpdate;
    private CardView mWalmartCardView;
    private CardView mKrogerCardView;
    private CardView mTargetCardView;
    private CardView mWholeFoodCardView;


    public SelectStoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mSelectStoreUpdate = (SelectStoreUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mSelectStoreUpdate = (SelectStoreUpdate)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_store, container, false);

        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mWalmartCardView = (CardView)view.findViewById(R.id.walmartLayout);
        mKrogerCardView = (CardView)view.findViewById(R.id.krogerLayout);
        mTargetCardView = (CardView)view.findViewById(R.id.targetLayout);
        mWholeFoodCardView = (CardView)view.findViewById(R.id.wholeFoodLayout);

        mWalmartCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWalmartCardView.setCardElevation(4);
                mWholeFoodCardView.setCardElevation(28);
                mKrogerCardView.setCardElevation(28);
                mTargetCardView.setCardElevation(28);
                mSelectStoreUpdate.storeSelected("(Walmart)");
                //Log.d("CARD", String.valueOf(ele));
            }
        });

        mKrogerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKrogerCardView.setCardElevation(4);
                mWholeFoodCardView.setCardElevation(28);
                mWalmartCardView.setCardElevation(28);
                mTargetCardView.setCardElevation(28);
                mSelectStoreUpdate.storeSelected("(Kroger)");
            }
        });

        mTargetCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTargetCardView.setCardElevation(4);
                mWholeFoodCardView.setCardElevation(28);
                mWalmartCardView.setCardElevation(28);
                mKrogerCardView.setCardElevation(28);
                mSelectStoreUpdate.storeSelected("(Target)");
            }
        });

        mWholeFoodCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWholeFoodCardView.setCardElevation(4);
                mWalmartCardView.setCardElevation(28);
                mKrogerCardView.setCardElevation(28);
                mTargetCardView.setCardElevation(28);
                mSelectStoreUpdate.storeSelected("(Whole Foods)");
            }
        });



        return view;
    }

}
