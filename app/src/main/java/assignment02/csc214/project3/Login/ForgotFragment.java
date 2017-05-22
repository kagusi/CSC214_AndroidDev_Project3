package assignment02.csc214.project3.Login;


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
import android.widget.Toast;

import assignment02.csc214.project3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotFragment extends Fragment {

    public interface ForgotPasswordUpdate{
        public void doneResetPass();
    }

    private ForgotPasswordUpdate mForgotPasswordUpdate;
    private EditText mUsername;
    private EditText mMothersMName;
    private EditText mNewPass;
    private EditText mComfirmPass;
    private Button mSubmitButton;


    public ForgotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mForgotPasswordUpdate = (ForgotPasswordUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mForgotPasswordUpdate = (ForgotPasswordUpdate)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot, container, false);

        mUsername = (EditText)view.findViewById(R.id.ForgotPass_Username);
        mMothersMName = (EditText)view.findViewById(R.id.forgotPass_MothersMaiden);
        mNewPass = (EditText)view.findViewById(R.id.forgotPass_NewPass);
        mComfirmPass = (EditText)view.findViewById(R.id.forgotPass_ComfirmPass);
        mSubmitButton = (Button) view.findViewById(R.id.forgotPass_SubmitButton);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mUsername.getText().toString()))
                {
                    Toast.makeText(getActivity(), "PLEASE ENTER USERNAME", Toast.LENGTH_LONG).show();

                }
                else if(TextUtils.isEmpty(mMothersMName.getText().toString()))
                {
                    Toast.makeText(getActivity(), "PLEASE RESPOND TO SECURITY QUESTION", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(mNewPass.getText().toString()))
                {
                    Toast.makeText(getActivity(), "PLEASE ENTER NEW PASSWORD", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(mComfirmPass.getText().toString()))
                {
                    Toast.makeText(getActivity(), "PLEASE RE-ENTER PASSWORD", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String username = mUsername.getText().toString();
                    String secAns = mMothersMName.getText().toString();
                    String pass1 = mNewPass.getText().toString();
                    String pass2 = mComfirmPass.getText().toString();

                    if(pass1.equals(pass2))
                    {
                        String[] uname = new String[1];
                        uname[0] = username;
                        /*
                        account = ModelQueryManager.get(getContext().getApplicationContext()).login(uname);
                        if(account == null)
                            Toast.makeText(getActivity(), "USERNAME NOT FOUND", Toast.LENGTH_LONG).show();
                        else
                        {
                            String verifySecAns = account.verifySecureQuestion(secAns);
                            if(verifySecAns.equals("success"))
                            {
                                account.setPassword(pass1);
                                ModelQueryManager.get(getContext().getApplicationContext()).updatePassword(account);
                                mForgotPassUpdate.doneForgotPass();
                                Toast.makeText(getActivity(), "PASSWORD CHANGED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(getActivity(), "SECURITY ANSWER NOT CORREC", Toast.LENGTH_LONG).show();
                        }
                        */
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
