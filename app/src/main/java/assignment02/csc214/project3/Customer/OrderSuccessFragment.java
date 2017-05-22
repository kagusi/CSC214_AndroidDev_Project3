package assignment02.csc214.project3.Customer;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import assignment02.csc214.project3.Models.Order;
import assignment02.csc214.project3.R;

import static assignment02.csc214.project3.Customer.HomeActivity.ORDER_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderSuccessFragment extends Fragment {

    public interface OrderSuccessUpdate{
        public void trackOrder(String orderNumber);
    }

    private OrderSuccessUpdate mOrderSuccessUpdate;
    private TextView mOrderNumTextView;
    private TextView mOrderTotalTextView;
    private TextView mDeliveredByTextView;
    private Button mTrackOrderButton;

    private String mOrderNumber;
    private String mOrderTotal;
    private String mDeliveryDriverName;

    private Order mOrder;

    public static final String ORDERNUM_KEY = "OrderNumKey";
    public static final String ORDERTOTAL_KEY = "OrderTotalKey";
    public static final String ORDERDELIDRIVER_KEY = "OrderNDeliDriverKey";

    public OrderSuccessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOrderSuccessUpdate = (OrderSuccessUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mOrderSuccessUpdate = (OrderSuccessUpdate)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_success, container, false);

        mOrderNumTextView = (TextView)view.findViewById(R.id.OrderSuceess_OrderNumber);
        mOrderTotalTextView = (TextView)view.findViewById(R.id.OrderSuceess_OrderTotal);
        mDeliveredByTextView = (TextView)view.findViewById(R.id.OrderSuceess_WillbeDeliveredBy);
        mTrackOrderButton = (Button) view.findViewById(R.id.OrderSuceess_TrackDelivery);

        mOrder = (Order)getArguments().getSerializable(ORDER_KEY);

        mOrderNumTextView.setText(mOrder.getOrderID());
        mOrderTotalTextView.setText(mOrder.getAmount());
        String willBeDelivered = getString(R.string.YourGrocery) +" " +mOrder.getDeliveryDriver() + " " +getString(R.string.withinMinutes);
        mDeliveredByTextView.setText(willBeDelivered);


        mTrackOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderSuccessUpdate.trackOrder(mOrderNumber);
            }
        });

        return view;
    }

}
