package assignment02.csc214.project3.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import assignment02.csc214.project3.Customer.CustomerAccountFragment;
import assignment02.csc214.project3.Customer.CustomerCreateAccountFragment;
import assignment02.csc214.project3.Customer.HomeActivity;
import assignment02.csc214.project3.Driver.DriverAccountFragment;
import assignment02.csc214.project3.Driver.DriverActivity;
import assignment02.csc214.project3.Models.Account;
import assignment02.csc214.project3.Models.CardInfo;
import assignment02.csc214.project3.Models.DriverProfile;
import assignment02.csc214.project3.Models.ShippingAddress;
import assignment02.csc214.project3.R;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragUpdate, CustomerCreateAccountFragment.CustomerCreateAccountFragUpdate,
        ForgotFragment.ForgotPasswordUpdate, DriverAccountFragment.DriverCreateAccountUpdate, CustomerAccountFragment.CustomerAccountUpdate{

    public static final String ACCOUNT_TYPE_KEY = "AccountType";
    public static final String ACCOUNT_KEY = "accountKey";

    public static final String SHIPPADD_KEY = "shippadd";
    public static final String CARD_KEY = "card";
    public static final String DRIVERPROFILE_KEY = "driverprofile";

    public static final String PAGETYPE_KEY = "pagetype";

    DriverAccountFragment mDriverAccountFrag;

    private Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment frag = new LoginFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.login_Layout, frag)
                .commit();

    }

    @Override
    public void loginSuccess(Account account) {

        if(account.getAccountType().equals("Customer"))
        {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra(ACCOUNT_KEY, account);
            intent.putExtra(PAGETYPE_KEY, "login");
            startActivity(intent);

        }
        else if(account.getAccountType().equals("Driver"))
        {
            Intent intent = new Intent(LoginActivity.this, DriverActivity.class);
            intent.putExtra(ACCOUNT_KEY, account);
            startActivity(intent);

        }

    }

    @Override
    public void addCreateCustomerAccountFrag() {
        CustomerCreateAccountFragment frag = new CustomerCreateAccountFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ACCOUNT_TYPE_KEY, "Customer");
        frag.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.login_Layout, frag)
                .commit();
    }

    @Override
    public void addCreateDriverAccountFrag() {
        CustomerCreateAccountFragment frag = new CustomerCreateAccountFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ACCOUNT_TYPE_KEY, "Driver");
        frag.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.login_Layout, frag)
                .commit();

    }

    @Override
    public void addForgotPassFrag() {
        ForgotFragment frag = new ForgotFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.login_Layout, frag)
                .commit();

    }


    @Override
    public void doneResetPass() {
        LoginFragment frag = new LoginFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.login_Layout, frag)
                .commit();
    }

    @Override
    public void customerCreateAccountDone(Account account) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ACCOUNT_KEY ,account);
        CustomerAccountFragment frag = new CustomerAccountFragment();
        frag.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.login_Layout, frag)
                .commit();

    }

    @Override
    public void driverCreateAccountDone(Account account) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ACCOUNT_KEY ,account);
        mDriverAccountFrag = new DriverAccountFragment();
        mDriverAccountFrag.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.login_Layout, mDriverAccountFrag)
                .commit();

    }

    @Override
    public void driverCreateDone(Account account) {
        Intent intent = new Intent(LoginActivity.this, DriverActivity.class);
        intent.putExtra(ACCOUNT_KEY, account);

        LoginFragment frag = new LoginFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.login_Layout, frag)
                .commit();

        startActivity(intent);
    }

    @Override
    public void customerDoneCreateProfile(ShippingAddress shipAdd, CardInfo cardDetail) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra(SHIPPADD_KEY, shipAdd);
        intent.putExtra(CARD_KEY, cardDetail);
        intent.putExtra(PAGETYPE_KEY, "create");

        LoginFragment frag = new LoginFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.login_Layout, frag)
                .commit();

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mDriverAccountFrag.changeImage();

    }


}
