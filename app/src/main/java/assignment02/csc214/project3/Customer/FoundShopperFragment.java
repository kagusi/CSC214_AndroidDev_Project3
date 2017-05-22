package assignment02.csc214.project3.Customer;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import assignment02.csc214.project3.Database.ModelQueryManager;
import assignment02.csc214.project3.Models.Cart;
import assignment02.csc214.project3.Models.DriverProfile;
import assignment02.csc214.project3.Models.Order;
import assignment02.csc214.project3.PictureManager.PictureManager;
import assignment02.csc214.project3.R;

import static assignment02.csc214.project3.Customer.HomeActivity.CART_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoundShopperFragment extends Fragment {

    public interface  FoundShopperFragUpdate{
        public void orderComfirmed(Order order);
        public void foundShopperViewCart(Cart cart);
    }

    public FoundShopperFragUpdate mFoundShopperFragUpdate;
    private ImageView mShopperProflImage;
    private TextView mShopperName;
    private TextView mShopperDistanceToStore;
    private TextView mShopperDistanceToDelivery;
    private TextView mCartTotalTextView;
    private ImageView mCartImage;
    private TextView mCartItemCountTextView;
    private Button mComfirmOrderButton;
    private Cart mCart;

    private DriverProfile mDriver;
    private String mImageUrl;
    private PictureManager mPictureManager;


    public FoundShopperFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mFoundShopperFragUpdate = (FoundShopperFragUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mFoundShopperFragUpdate = (FoundShopperFragUpdate)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_found_shopper, container, false);

        mShopperProflImage = (ImageView)view.findViewById(R.id.ShopperFound_PicImageView);
        mShopperName = (TextView)view.findViewById(R.id.ShopperFound_NameTextView);
        mShopperDistanceToStore = (TextView)view.findViewById(R.id.ShopperFound_DistanceToStoreTextView);
        mShopperDistanceToDelivery = (TextView)view.findViewById(R.id.ShopperFound_DistanceToYouTextView);
        mCartTotalTextView = (TextView)view.findViewById(R.id.ShopperFound_TotalTextView);
        mCartImage = (ImageView)view.findViewById(R.id.ShopperFound_cartImage);
        mCartItemCountTextView = (TextView)view.findViewById(R.id.ShopperFound_cartCountTextView);
        mComfirmOrderButton = (Button)view.findViewById(R.id.ShopperFound_comfirmOrderButton);

        mPictureManager = new PictureManager(getActivity());

        mCart = (Cart)getArguments().getSerializable(CART_KEY);
        String count = String.valueOf(mCart.getItemCount()) +" item(s)";
        mCartItemCountTextView.setText(count);

        mDriver = ModelQueryManager.get(getContext().getApplicationContext()).getDeliveryDriver();
        mImageUrl = mDriver.getProfilePicLocation();

        mShopperName.setText(mDriver.getFullName());
        mShopperDistanceToStore.setText("0.5 mile(s)");
        mShopperDistanceToDelivery.setText("1.2 miles(s)");

        if(!mImageUrl.isEmpty())
        {
            Bitmap bitmap = mPictureManager.getScaledBitmap(mImageUrl,
                    mShopperProflImage.getWidth(), mShopperProflImage.getMaxHeight());
            mShopperProflImage.setImageBitmap(bitmap);
        }


        double res = mCart.getTotalCost();
        DecimalFormat df = new DecimalFormat("#.00");
        String total = "Total: "+df.format(res);
        mCartTotalTextView.setText(total);


        mCartItemCountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFoundShopperFragUpdate.foundShopperViewCart(mCart);
            }
        });

        mCartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFoundShopperFragUpdate.foundShopperViewCart(mCart);
            }
        });

        mComfirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mOrderID = "3848734JK";
                String mUsername = mCart.getUserID();
                String mItemsOrdered = "";
                int itemCount = mCart.getItemList().size();
                for(int i = 0; i<itemCount; i++)
                {
                    mItemsOrdered += mCart.getItemList().get(i).getItemDetails()+" ";
                }
                String mDeliveryDriver = mDriver.getFullName();
                String mAmount = String.valueOf(mCart.getTotalCost());

                Order order = new Order(mOrderID, mUsername, mItemsOrdered, mDeliveryDriver, mAmount);
                ModelQueryManager.get(getContext().getApplicationContext()).createOrder(order);

                mFoundShopperFragUpdate.orderComfirmed(order);
            }
        });

        return view;
    }

}
