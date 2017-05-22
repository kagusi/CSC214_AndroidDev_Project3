package assignment02.csc214.project3.Customer;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import assignment02.csc214.project3.Database.ModelQueryManager;
import assignment02.csc214.project3.Models.CardInfo;
import assignment02.csc214.project3.Models.Cart;
import assignment02.csc214.project3.Models.ShippingAddress;
import assignment02.csc214.project3.R;

import static assignment02.csc214.project3.Customer.HomeActivity.CART_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentInfoFragment extends Fragment {

    public interface PaymentInfoUpdate{
        public void paymentInfoViewCart(Cart cart);
        public void paymentInfoContinue(Cart cart);
        public ShippingAddress getShippAdd();
        public CardInfo getCardInfo();
        public void setShippingAddress(ShippingAddress shippingAdd);
        public void setCardInfo(CardInfo card);
    }

    private PaymentInfoUpdate mPaymentInfoUpdate;
    private Cart mCart;
    private TextView mCartItemCountTextView;
    private ImageView mCartImageView;
    private EditText mFulname;
    private EditText mAddress1;
    private EditText mAddress2;
    private EditText mCity;
    private EditText mState;
    private EditText mPhone;
    private EditText mNameOnCard;
    private EditText mCardNumber;
    private EditText mExpDate;
    private EditText mCVV;
    private Button mContinueButton;

    private ShippingAddress mShippingAddress;
    private CardInfo mCardInfo;

    public PaymentInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPaymentInfoUpdate = (PaymentInfoUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mPaymentInfoUpdate = (PaymentInfoUpdate)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_info, container, false);

        mCart = (Cart)getArguments().getSerializable(CART_KEY);
        mCartItemCountTextView = (TextView)view.findViewById(R.id.home_cartCountTextView);
        mCartImageView = (ImageView)view.findViewById(R.id.paymentInfo_CartImage);
        mFulname = (EditText)view.findViewById(R.id.paymentInfo_FullName);
        mAddress1 = (EditText)view.findViewById(R.id.paymentInfo_Address1);
        mAddress2 = (EditText)view.findViewById(R.id.paymentInfo_Address2);
        mCity = (EditText)view.findViewById(R.id.paymentInfo_City);
        mState = (EditText)view.findViewById(R.id.paymentInfo_State);
        mPhone = (EditText)view.findViewById(R.id.paymentInfo_Phone);
        mNameOnCard = (EditText)view.findViewById(R.id.paymentInfo_NameOnCard);
        mCardNumber = (EditText)view.findViewById(R.id.paymentInfo_CardNumber);
        mExpDate = (EditText)view.findViewById(R.id.paymentInfo_ExpDate);
        mCVV = (EditText)view.findViewById(R.id.paymentInfo_CVV);
        mContinueButton = (Button)view.findViewById(R.id.paymentInfo_ContinueButton);

        mShippingAddress = mPaymentInfoUpdate.getShippAdd();
        mCardInfo = mPaymentInfoUpdate.getCardInfo();
        setAllText();

        String count = String.valueOf(mCart.getItemCount()) +" item(s)";
        mCartItemCountTextView.setText(count);

        mCartItemCountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaymentInfoUpdate.paymentInfoViewCart(mCart);
            }
        });

        mCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaymentInfoUpdate.paymentInfoViewCart(mCart);
            }
        });

        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        return view;
    }

    public void getData()
    {
        if(TextUtils.isEmpty(mFulname.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter delivery name", Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(mAddress1.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter address", Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(mCity.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter city", Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(mState.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter state", Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(mPhone.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter phone", Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(mNameOnCard.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter name on card", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(mCardNumber.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter card number", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(mExpDate.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter card expiry date", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(mCVV.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter card cvv", Toast.LENGTH_LONG).show();
        }
        else
        {
            String fullname = mFulname.getText().toString();
            String add1 = mAddress1.getText().toString();
            String add2 = "";
            if(!TextUtils.isEmpty(mAddress2.getText().toString()))
            {
                add2 = mAddress2.getText().toString();
            }
            String city = mCity.getText().toString();
            String state = mState.getText().toString();
            String phone = mPhone.getText().toString();
            String nameOnCard = mNameOnCard.getText().toString();
            String cardNumber = mCardNumber.getText().toString();
            String expDate = mExpDate.getText().toString();
            String cvv = mCVV.getText().toString();

            ShippingAddress newAdd = new ShippingAddress(fullname, add1, add2, city, state, phone, mShippingAddress.getUsername());
            CardInfo card = new CardInfo(mShippingAddress.getUsername(), nameOnCard, cardNumber, expDate, cvv);

            ModelQueryManager.get(getContext().getApplicationContext()).updateShippingAddress(newAdd);
            ModelQueryManager.get(getContext().getApplicationContext()).updateCardInfo(card);

            mPaymentInfoUpdate.setShippingAddress(newAdd);
            mPaymentInfoUpdate.setCardInfo(card);

            mPaymentInfoUpdate.paymentInfoContinue(mCart);

        }

    }

    public void setAllText()
    {
        mFulname.setText(mShippingAddress.getFullname());
        mAddress1.setText(mShippingAddress.getAddress1());
        mAddress2.setText(mShippingAddress.getAddress2());
        mCity.setText(mShippingAddress.getCity());
        mState.setText(mShippingAddress.getState());
        mPhone.setText(mShippingAddress.getPhone());

        mNameOnCard.setText(mCardInfo.getName());
        mCardNumber.setText(mCardInfo.getCardNum());
        mExpDate.setText(mCardInfo.getExpDate());
        mCVV.setText(mCardInfo.getCVV());

    }

}
