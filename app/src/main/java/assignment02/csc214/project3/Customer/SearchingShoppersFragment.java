package assignment02.csc214.project3.Customer;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import assignment02.csc214.project3.Models.Cart;
import assignment02.csc214.project3.R;

import static assignment02.csc214.project3.Customer.HomeActivity.CART_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchingShoppersFragment extends Fragment {

    public interface SearchShopperUpdate{
        public void shopperFound(Cart cart);
    }

    private SearchShopperUpdate mSearchShopperUpdate;
    private ProgressBar mProgressBar;
    private Cart mCart;

    public SearchingShoppersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mSearchShopperUpdate = (SearchShopperUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mSearchShopperUpdate = (SearchShopperUpdate)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_searching_shoppers, container, false);

        mProgressBar = (ProgressBar)view.findViewById(R.id.searchShoppers_ProgressBar);
        mCart = (Cart)getArguments().getSerializable(CART_KEY);

        scheduleTimer();

        return view;
    }

    public void scheduleTimer()
    {
        //Loop for 5 seconds
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getContext(), "Shopper Found!", Toast.LENGTH_SHORT).show();
                        mSearchShopperUpdate.shopperFound(mCart);
                    }
                });
            }

        },5000);
    }

}
