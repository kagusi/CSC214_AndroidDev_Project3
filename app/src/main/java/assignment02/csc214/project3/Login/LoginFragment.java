package assignment02.csc214.project3.Login;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
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
import assignment02.csc214.project3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public interface LoginFragUpdate{
        public void loginSuccess(Account account);
        public void addCreateCustomerAccountFrag();
        public void addCreateDriverAccountFrag();
        public void addForgotPassFrag();
    }

    private LoginFragUpdate mLoginFragUpdate;
    private EditText mUsernameEdiText;
    private EditText mPasswordEdiText;
    private TextView mForgotPassTextView;
    private TextView mCreateCustomerAccountTextView;
    private TextView mCreateDriverAccountTextView;
    private Button mLoginButton;

    private String mUsername;
    private String mPassword;
    private Account account;
    private String mStatus;

    public static final String SHAREDPREF_USERNAMEKEY = "shared_usernameKey";
    //Used to determine whether a user logged out during last app usage
    public static final String SHAREDPREF_STATUSKEY = "shared_statusKey";



    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mLoginFragUpdate = (LoginFragUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mLoginFragUpdate = (LoginFragUpdate)activity;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUsername = prefs.getString(SHAREDPREF_USERNAMEKEY, null);
        mStatus = prefs.getString(SHAREDPREF_STATUSKEY, null);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        //Check whether current user has used this app previously and didnt loggout
        //If so, the app will be restored to its previous state
        if(mUsername != null && mStatus != null)
        {
            if(mStatus.equals("LoggedIn"))
                restoreState();
        }

        mUsernameEdiText = (EditText)view.findViewById(R.id.Login_UserIDTextView);
        mPasswordEdiText = (EditText)view.findViewById(R.id.Login_passwordEditText);
        mForgotPassTextView = (TextView)view.findViewById(R.id.Login_ForgotPassTextView);
        mCreateCustomerAccountTextView = (TextView)view.findViewById(R.id.Login_CreateAccounTexview);
        mCreateDriverAccountTextView = (TextView)view.findViewById(R.id.Login_BecomeAShopperTextView);
        mLoginButton = (Button)view.findViewById(R.id.Login_SubmitButton);

        mForgotPassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginFragUpdate.addForgotPassFrag();

            }
        });

        mCreateCustomerAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginFragUpdate.addCreateCustomerAccountFrag();

            }
        });

        mCreateDriverAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginFragUpdate.addCreateDriverAccountFrag();

            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(mUsernameEdiText.getText().toString()))
                {
                    Toast.makeText(getActivity(), "PLEASE ENTER USERNAME", Toast.LENGTH_LONG).show();

                }
                else if(TextUtils.isEmpty(mPasswordEdiText.getText().toString()))
                {
                    Toast.makeText(getActivity(), "PLEASE ENTER PASSWORD", Toast.LENGTH_LONG).show();
                }
                else
                {

                    mUsername = mUsernameEdiText.getText().toString();
                    mPassword = mPasswordEdiText.getText().toString();
                    String[] uname = new String[1];
                    uname[0] = mUsername;
                    //uname[1] = mUsername;
                    account = ModelQueryManager.get(getContext().getApplicationContext()).login(uname);

                    if(account == null)
                    {
                        Toast.makeText(getActivity(), "INVALID USERNAME", Toast.LENGTH_LONG).show();


                    }

                    else
                    {
                        String verify = account.verifyPass(mPassword);
                        if(verify.equals("success"))
                        {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            prefs.edit()
                                    .remove(SHAREDPREF_STATUSKEY)
                                    .putString(SHAREDPREF_STATUSKEY, "LoggedIn")
                                    .apply();

                            //ModelQueryManager.get(getContext().getApplicationContext()).createFeedTable();
                            mLoginFragUpdate.loginSuccess(account);

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "INVALID PASSWORD", Toast.LENGTH_LONG).show();

                        }


                    }

                }

            }
        });



        return view;
    }

    public void restoreState()
    {
        String[] uname = new String[1];
        uname[0] = mUsername;
        //uname[1] = mUsername;
        account = ModelQueryManager.get(getContext().getApplicationContext()).login(uname);
        mLoginFragUpdate.loginSuccess(account);

    }

}
