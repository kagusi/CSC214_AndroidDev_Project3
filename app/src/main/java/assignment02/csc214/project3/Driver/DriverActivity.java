package assignment02.csc214.project3.Driver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import assignment02.csc214.project3.Database.ModelQueryManager;
import assignment02.csc214.project3.Login.LoginActivity;
import assignment02.csc214.project3.Models.Account;
import assignment02.csc214.project3.Models.DriverProfile;
import assignment02.csc214.project3.PictureManager.PictureManager;
import assignment02.csc214.project3.R;

import static assignment02.csc214.project3.Login.LoginActivity.ACCOUNT_KEY;
import static assignment02.csc214.project3.Login.LoginActivity.DRIVERPROFILE_KEY;

public class DriverActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DriverAccountFragment.DriverCreateAccountUpdate, ShoppingRequestFragment.MapInterface {

    private DriverProfile mDriverProfile;
    private Account mAccount;
    private ImageView mProfilePicImageView;
    private TextView mProfileNameTextview;
    private TextView mDateTextView;
    private PictureManager mPictureManager;

    DriverAccountFragment mDriverAccountFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mAccount = (Account) intent.getSerializableExtra(ACCOUNT_KEY);
        mPictureManager = new PictureManager(DriverActivity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        mProfilePicImageView = (ImageView)header.findViewById(R.id.imageView);
        mProfileNameTextview = (TextView)header.findViewById(R.id.driverHeader_FullNameTextView);
        mDateTextView = (TextView)header.findViewById(R.id.driverHeader_DateTextView) ;
        updateDrawerHeader();

        ShoppingRequestFragment frag = new ShoppingRequestFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_layout , frag)
                .commit();
    }

    public void updateDrawerHeader()
    {
        String[] uname = new String[1];
        uname[0] = mAccount.getUsername();
        mDriverProfile = ModelQueryManager.get(getApplicationContext()).getDriverProfile(uname);

        String mImageUrl = mDriverProfile.getProfilePicLocation();
        DateFormat dateformat = new SimpleDateFormat("MM/dd/yy");
        Date currentDate = new Date();
        mDateTextView.setText(dateformat.format(currentDate));


        if(!mImageUrl.isEmpty())
        {
            Bitmap bitmap = mPictureManager.getScaledBitmap(mImageUrl,
                    mProfilePicImageView.getWidth(), mProfilePicImageView.getMaxHeight());
            mProfilePicImageView.setImageBitmap(bitmap);
            mProfileNameTextview.setText(mDriverProfile.getFullName());
        }
        if(!mDriverProfile.getFullName().isEmpty())
            mProfileNameTextview.setText(mDriverProfile.getFullName());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.driver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.driverMenu_home){
            ShoppingRequestFragment frag = new ShoppingRequestFragment();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_layout , frag)
                    .commit();

        }
        else if (id == R.id.driverMenu_account) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ACCOUNT_KEY ,mAccount);
            mDriverAccountFrag = new DriverAccountFragment();
            mDriverAccountFrag.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_layout, mDriverAccountFrag)
                    .commit();

        } else if (id == R.id.driverMenu_rides) {

        } else if (id == R.id.driverMenu_requestPayment) {

        } else if (id == R.id.driverMenu_logout) {
            Intent intent = new Intent(DriverActivity.this, LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void driverCreateDone(Account account) {
        Toast.makeText(this, "Account was successfully updated!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMap(String address) {
        Bundle bundle = new Bundle();
        bundle.putString("DeliveryAddress", address);
        ProceedToDeliveryFragment frag = new ProceedToDeliveryFragment();
        frag.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_layout, frag)
                .commit();
    }
}
