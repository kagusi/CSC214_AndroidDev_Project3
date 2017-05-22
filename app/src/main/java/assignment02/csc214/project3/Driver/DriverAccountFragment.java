package assignment02.csc214.project3.Driver;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import assignment02.csc214.project3.Database.ModelQueryManager;
import assignment02.csc214.project3.Models.Account;
import assignment02.csc214.project3.Models.DriverProfile;
import assignment02.csc214.project3.PictureManager.PictureManager;
import assignment02.csc214.project3.R;

import static assignment02.csc214.project3.Login.LoginActivity.ACCOUNT_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverAccountFragment extends Fragment {

    public interface DriverCreateAccountUpdate{
        public void driverCreateDone(Account account);
    }

    private DriverCreateAccountUpdate mDriverCreateAccountUpdate;
    private ImageView mProfileImageView;
    private EditText mName;
    private EditText mSSN;
    private EditText mDOB;
    private EditText mPhone;
    private EditText mAddress;
    private String mImageUrl;
    private Account mAccount;
    private Button mSubmitButton;
    private Button mChangePicButton;
    private PictureManager mPictureManager;

    private DriverProfile mDriverProfile;

    private ProgressBar mProgressBar;
    private View mView;


    public DriverAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mDriverCreateAccountUpdate = (DriverCreateAccountUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mDriverCreateAccountUpdate = (DriverCreateAccountUpdate)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_driver_account, container, false);

        mView = view.findViewById(R.id.scrollLayout);
        mProgressBar = (ProgressBar)view.findViewById(R.id.driver_ProgressBar);

        mAccount = (Account)getArguments().getSerializable(ACCOUNT_KEY);
        mPictureManager = new PictureManager(getActivity());

        mProfileImageView = (ImageView)view.findViewById(R.id.DriverProfile_ProfilePixImageView);
        mName = (EditText)view.findViewById(R.id.DriverProfile_FullNameEditText);
        mSSN = (EditText)view.findViewById(R.id.DriverSSNEditText);
        mDOB = (EditText)view.findViewById(R.id.DriverProfile_DateOfBirthEditText);
        mPhone = (EditText)view.findViewById(R.id.DriverProfile_PhoneEditText);
        mAddress = (EditText)view.findViewById(R.id.DriverProfile_AddressEditText);
        mChangePicButton = (Button)view.findViewById(R.id.DriverProfile_UploadImageButton);
        mSubmitButton = (Button)view.findViewById(R.id.DriverProfile_UpdateButton);

        String[] uname = new String[1];
        uname[0] = mAccount.getUsername();
        mDriverProfile = ModelQueryManager.get(getContext().getApplicationContext()).getDriverProfile(uname);
        mImageUrl = mDriverProfile.getProfilePicLocation();

        if(!mImageUrl.isEmpty())
        {
            Bitmap bitmap = mPictureManager.getScaledBitmap(mImageUrl,
                    mProfileImageView.getWidth(), mProfileImageView.getMaxHeight());
            mProfileImageView.setImageBitmap(bitmap);
        }

        mName.setText(mDriverProfile.getFullName());
        mSSN.setText(mDriverProfile.getSSN());
        mDOB.setText(mDriverProfile.getDOB());
        mPhone.setText(mDriverProfile.getPhone());
        mAddress.setText(mDriverProfile.getAddress());


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        mChangePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPictureManager.takeAPhoto(mDriverProfile.getDriverUsername());
                mImageUrl = mPictureManager.getFilePath();
            }
        });


        return view;
    }

    public void updateProfile()
    {
        if(TextUtils.isEmpty(mName.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(mSSN.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter SSN", Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(mDOB.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter Date of birth", Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(mPhone.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter Phone", Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(mAddress.getText().toString()))
        {
            Toast.makeText(getActivity(), "Please enter address", Toast.LENGTH_LONG).show();

        }
        else
        {
            //mView.setVisibility(View.GONE);
            //mProgressBar.setVisibility(View.VISIBLE);

            String username = mAccount.getUsername();
            String fulName = mName.getText().toString();
            String ssn = mSSN.getText().toString();
            String dob = mDOB.getText().toString();
            String phone = mPhone.getText().toString();
            String address = mAddress.getText().toString();

            mDriverProfile.setDriverUsername(username);
            mDriverProfile.setFullName(fulName);
            mDriverProfile.setSSN(ssn);
            mDriverProfile.setDOB(dob);
            mDriverProfile.setPhone(phone);
            mDriverProfile.setAddress(address);
            mDriverProfile.setProfilePicLocation(mImageUrl);

            ModelQueryManager.get(getContext().getApplicationContext()).updateDriverProfile(mDriverProfile);
            mDriverCreateAccountUpdate.driverCreateDone(mAccount);
            //Toast.makeText(getActivity(), "PROFILE SUCCESSFULY CREATED", Toast.LENGTH_LONG).show();
            //scheduleTimer();

        }
    }

    public void changeImage()
    {
        Bitmap bitmap = mPictureManager.getScaledBitmap(mImageUrl,
                mProfileImageView.getWidth(), mProfileImageView.getMaxHeight());
        mProfileImageView.setImageBitmap(bitmap);
    }

    public void scheduleTimer()
    {
        //Loop for 5 seconds
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        mDriverCreateAccountUpdate.driverCreateDone(mAccount);
                        //Toast.makeText(getContext(), "Shopper Found!", Toast.LENGTH_SHORT).show();
                        //mProgressBar.setVisibility(View.GONE);
                        //mView.setVisibility(View.VISIBLE);
                    }
                });
            }

        },3000);
    }

}
