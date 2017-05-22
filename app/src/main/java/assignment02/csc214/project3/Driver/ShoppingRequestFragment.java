package assignment02.csc214.project3.Driver;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import assignment02.csc214.project3.Database.ModelQueryManager;
import assignment02.csc214.project3.Models.ShippingAddress;
import assignment02.csc214.project3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingRequestFragment extends Fragment {

    public interface MapInterface{
        public void showMap(String address);
    }

    private MapInterface mMapInterface;
    private Button mAcceptButton;
    private Button mRejectButton;
    private View mNotFoundView;
    private View mFoundView;
    private TextView mCustomerName;
    private TextView mDeliveryAddress;
    private TextView mStore;

    private ShippingAddress mShippingAddress;

    public ShoppingRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mMapInterface = (MapInterface)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mMapInterface = (MapInterface)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_request, container, false);

        mNotFoundView = view.findViewById(R.id.NoRequest_Found);
        mFoundView = view.findViewById(R.id.ShopRequest_layout);
        mCustomerName = (TextView)view.findViewById(R.id.shopReuest_customerNameTextView);
        mDeliveryAddress = (TextView)view.findViewById(R.id.shopReuest_deliveryTextView);
        mStore = (TextView)view.findViewById(R.id.shopReuest_requestedStoreTextView);

        mShippingAddress = ModelQueryManager.get(getContext().getApplicationContext()).getAnyShippingAdd();

        if(mShippingAddress != null)
        {
            mCustomerName.setText(mShippingAddress.getFullname());
            mDeliveryAddress.setText(mShippingAddress.getAddress1());
            mStore.setText("Walmart");
        }
        else
        {
            mFoundView.setVisibility(View.GONE);
            mNotFoundView.setVisibility(View.VISIBLE);
        }

        //Make button blink
        final Animation blink = new AlphaAnimation(1, 0);
        blink.setDuration(500);
        blink.setInterpolator(new LinearInterpolator());
        blink.setRepeatCount(Animation.INFINITE);
        blink.setRepeatMode(Animation.REVERSE);

        mAcceptButton = (Button)view.findViewById(R.id.shopReuest_acceptButton);
        mAcceptButton.startAnimation(blink);

        mAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.clearAnimation();

                mMapInterface.showMap(mShippingAddress.getAddress1());
            }
        });


        return view;
    }

}
