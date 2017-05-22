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
import android.widget.TextView;
import android.widget.Toast;

import assignment02.csc214.project3.Database.ModelQueryManager;
import assignment02.csc214.project3.Models.Account;
import assignment02.csc214.project3.Models.DriverProfile;
import assignment02.csc214.project3.Models.ShippingAddress;
import assignment02.csc214.project3.R;

import static assignment02.csc214.project3.Login.LoginActivity.ACCOUNT_TYPE_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerCreateAccountFragment extends Fragment {

    public interface CustomerCreateAccountFragUpdate{
        public void customerCreateAccountDone(Account account);
        public void driverCreateAccountDone(Account account);
    }

    private CustomerCreateAccountFragUpdate mCreateAccountFragUpdate;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mComfirmPassEditText;
    private EditText mSecureQuestionEditText;
    private Button mCreateAccountButton;
    private TextView mCancelTextView;


    private String mUsername;
    private String mPassword;
    private String mComfirmpass;
    private String mSecureQuestion;

    private String AccountType;



    public CustomerCreateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCreateAccountFragUpdate = (CustomerCreateAccountFragUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mCreateAccountFragUpdate = (CustomerCreateAccountFragUpdate)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_create_account, container, false);

        AccountType = getArguments().getString(ACCOUNT_TYPE_KEY);

        mUsernameEditText = (EditText)view.findViewById(R.id.ForgotPass_Username);
        mPasswordEditText = (EditText)view.findViewById(R.id.forgotPass_NewPass);
        mComfirmPassEditText = (EditText)view.findViewById(R.id.forgotPass_ComfirmPass);
        mSecureQuestionEditText = (EditText)view.findViewById(R.id.forgotPass_MothersMaiden);
        mCreateAccountButton = (Button)view.findViewById(R.id.fCreateAccount_SubmitButton);

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mUsernameEditText.getText().toString()))
                {
                    Toast.makeText(getActivity(), "PLEASE ENTER USERNAME", Toast.LENGTH_LONG).show();

                }
                else if(TextUtils.isEmpty(mPasswordEditText.getText().toString()))
                {
                    Toast.makeText(getActivity(), "PLEASE ENTER PASSWORD", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(mComfirmPassEditText.getText().toString()))
                {
                    Toast.makeText(getActivity(), "PLEASE COMFIRM PASSWORD", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(mSecureQuestionEditText.getText().toString()))
                {
                    Toast.makeText(getActivity(), "PLEASE ENTER SECURITY QUESTION", Toast.LENGTH_LONG).show();
                }
                else
                {

                    mUsername = mUsernameEditText.getText().toString();
                    mPassword = mPasswordEditText.getText().toString();
                    mComfirmpass = mComfirmPassEditText.getText().toString();
                    mSecureQuestion = mSecureQuestionEditText.getText().toString();

                    if(mPassword.equals(mComfirmpass))
                    {
                        Account account;
                        String[] uname = new String[1];
                        uname[0] = mUsername;
                        //Check whether username already exist
                        account = ModelQueryManager.get(getContext().getApplicationContext()).login(uname);
                        if(account != null)
                            Toast.makeText(getActivity(), "USER ALREADY EXIST", Toast.LENGTH_LONG).show();
                        else
                        {

                            if(AccountType.equals("Customer"))
                            {
                                account = new Account(mUsername, mPassword, mSecureQuestion, "Customer");
                                ModelQueryManager.get(getContext().getApplicationContext()).createAccount(account);
                                Toast.makeText(getActivity(), "ACCOUNT SUCCESSFULLY CREATED", Toast.LENGTH_LONG).show();
                                mCreateAccountFragUpdate.customerCreateAccountDone(account);

                            }
                            else
                            {
                                account = new Account(mUsername, mPassword, mSecureQuestion, "Driver");
                                ModelQueryManager.get(getContext().getApplicationContext()).createAccount(account);
                                Toast.makeText(getActivity(), "ACCOUNT SUCCESSFULLY CREATED", Toast.LENGTH_LONG).show();

                                DriverProfile driverProfile = new DriverProfile("","","","","","",mUsername);
                                ModelQueryManager.get(getContext().getApplicationContext()).createDriverProfile(driverProfile);

                                mCreateAccountFragUpdate.driverCreateAccountDone(account);
                            }

                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "PASSWORD DOES NOT MATCH", Toast.LENGTH_LONG).show();
                    }

                }
            }

        });

        return view;
    }

}
