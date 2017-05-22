package assignment02.csc214.project3.Customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import assignment02.csc214.project3.Database.ModelQueryManager;
import assignment02.csc214.project3.Driver.DeliveryHistoryFragment;
import assignment02.csc214.project3.Login.LoginActivity;
import assignment02.csc214.project3.Models.Account;
import assignment02.csc214.project3.Models.CardInfo;
import assignment02.csc214.project3.Models.Cart;
import assignment02.csc214.project3.Models.Order;
import assignment02.csc214.project3.Models.ShippingAddress;
import assignment02.csc214.project3.R;

import static assignment02.csc214.project3.Customer.OrderSuccessFragment.ORDERDELIDRIVER_KEY;
import static assignment02.csc214.project3.Customer.OrderSuccessFragment.ORDERNUM_KEY;
import static assignment02.csc214.project3.Customer.OrderSuccessFragment.ORDERTOTAL_KEY;
import static assignment02.csc214.project3.Login.LoginActivity.ACCOUNT_KEY;
import static assignment02.csc214.project3.Login.LoginActivity.CARD_KEY;
import static assignment02.csc214.project3.Login.LoginActivity.PAGETYPE_KEY;
import static assignment02.csc214.project3.Login.LoginActivity.SHIPPADD_KEY;
import static assignment02.csc214.project3.Login.LoginFragment.SHAREDPREF_STATUSKEY;
import static assignment02.csc214.project3.Login.LoginFragment.SHAREDPREF_USERNAMEKEY;
import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.HomeFragUpdate, ViewCartFragment.viewCartFragUpdate,
        PaymentInfoFragment.PaymentInfoUpdate, SearchingShoppersFragment.SearchShopperUpdate, FoundShopperFragment.FoundShopperFragUpdate,
        OrderSuccessFragment.OrderSuccessUpdate, SelectStoreFragment.SelectStoreUpdate, ViewCustomerAccountFragment.ViewCustomerACCUpdate{

    public static final String CART_KEY = "cart_key";
    public static final String USERAME_KEY = "user_nameKey";

    public static final String ORDER_KEY = "orderkey";

    private ImageSwitcher mImageSwitcher;
    int currentIndex = 0;
    int imageIDs[] = {R.mipmap.home_image1, R.mipmap.home_image2,
            R.mipmap.home_image3, R.mipmap.home_image4,};
    private ImageView mSwitcherImageView;

    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;

    private HomeFragment mHomeFragment;
    private ViewCartFragment mViewCartFragment;
    private Cart mCart;
    private String mStore;
    private TextView mHomeStoreName;
    private TextView mProfileNameTextview;
    private TextView mDateTextView;

    private ShippingAddress mShippingAddress;
    private CardInfo mCardInfo;
    private Account mAccount;
    private String mUserName;

    public void setShippingAddress(ShippingAddress shippingAddress) {
        mShippingAddress = shippingAddress;
    }

    public void setCardInfo(CardInfo cardInfo) {
        mCardInfo = cardInfo;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String pageType = intent.getStringExtra(PAGETYPE_KEY);
        if(pageType.equals("create"))
        {
            mShippingAddress = (ShippingAddress)intent.getSerializableExtra(SHIPPADD_KEY);
            mUserName = mShippingAddress.getUsername();
            mCardInfo = (CardInfo)intent.getSerializableExtra(CARD_KEY);
        }
        else
        {
            mAccount = (Account)intent.getSerializableExtra(ACCOUNT_KEY);
            mUserName = mAccount.getUsername();
            String[] uname = new String[1];
            uname[0] = mAccount.getUsername();
            mShippingAddress = ModelQueryManager.get(getApplicationContext()).getShippingAdd(uname);
            mCardInfo = ModelQueryManager.get(getApplicationContext()).getCardInfo(uname);
        }

        mCart = new Cart(mUserName);
        mHomeStoreName = (TextView)findViewById(R.id.Home_StoreName);

        mSwitcherImageView = (ImageView)findViewById(R.id.home_switcherImage);
        mImageSwitcher = (ImageSwitcher)findViewById(R.id.home_switcher);
        Animation in = AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_out_right);
        mImageSwitcher.setInAnimation(in);
        mImageSwitcher.setOutAnimation(out);

        startCollapsingToolbar();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        startImageSwitcher();

        View header = navigationView.getHeaderView(0);
        mProfileNameTextview = (TextView)header.findViewById(R.id.CustomerHeader_FullNameTextView);
        mDateTextView = (TextView)header.findViewById(R.id.CustomerHeader_DateTextView);
        updateDrawerHeader();

        //Add select store fragment
        SelectStoreFragment frag = new SelectStoreFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.CustomerContent_layout, frag)
                .commit();

    }

    public void updateDrawerHeader()
    {
        mProfileNameTextview.setText(mShippingAddress.getFullname());
        DateFormat dateformat = new SimpleDateFormat("MM/dd/yy");
        Date currentDate = new Date();
        mDateTextView.setText(dateformat.format(currentDate));
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
        getMenuInflater().inflate(R.menu.home, menu);
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

        appBarLayout.setExpanded(true);

        if (id == R.id.customerMenu_home) {

            if(mStore == null)
            {
                SelectStoreFragment frag = new SelectStoreFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.CustomerContent_layout, frag)
                        .commit();
            }
            else
            {
                Bundle bundle = new Bundle();
                bundle.putSerializable(CART_KEY, mCart);
                HomeFragment frag = new HomeFragment();
                frag.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.CustomerContent_layout, frag)
                        .commit();
            }

        } else if (id == R.id.customerMenu_SelectStore) {
            SelectStoreFragment frag = new SelectStoreFragment();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.CustomerContent_layout, frag)
                    .commit();

        } else if (id == R.id.customerMenu_OrderHistory) {
            appBarLayout.setExpanded(false);
            Bundle bundle = new Bundle();
            bundle.putString(USERAME_KEY, mUserName);
            OrderHistoryFragment frag = new OrderHistoryFragment();
            frag.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.CustomerContent_layout, frag)
                    .commit();

        } else if (id == R.id.customerMenu_MyAccount) {
            appBarLayout.setExpanded(false);
            Bundle bundle = new Bundle();
            bundle.putSerializable(SHIPPADD_KEY, mShippingAddress);
            bundle.putSerializable(CARD_KEY, mCardInfo);
            ViewCustomerAccountFragment frag = new ViewCustomerAccountFragment();
            frag.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.CustomerContent_layout, frag)
                    .commit();

        } else if (id == R.id.customerMenu_logout) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit()
                    .remove(SHAREDPREF_STATUSKEY)
                    .putString(SHAREDPREF_STATUSKEY, "LoggedOut")
                    .apply();

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.customerMenu_Share) {

        }
        else if (id == R.id.customerMenu_Browse) {
            BrowserFragment frag = new BrowserFragment();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.CustomerContent_layout, frag)
                    .commit();
        }
        else if (id == R.id.customerMenu_PlayMusic) {
            appBarLayout.setExpanded(false);
            PlayMusicFragment frag = new PlayMusicFragment();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.CustomerContent_layout, frag)
                    .commit();

        }
        else if (id == R.id.customerMenu_Help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void startCollapsingToolbar() {
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.home_collapsingToolbar);
        collapsingToolbar.setTitle(" ");
        appBarLayout = (AppBarLayout) findViewById(R.id.home_AppBar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }


    public void startImageSwitcher()
    {
        try {
            Glide.with(getApplicationContext()).load(android.R.color.transparent).into((ImageView)findViewById(R.id.home_switcherImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                currentIndex++;
                if(currentIndex == (imageIDs.length-1))
                    currentIndex=0;
                runOnUiThread(new Runnable() {

                    public void run() {
                        try {
                            Glide.with(getApplicationContext()).load(android.R.color.transparent).into((ImageView)findViewById(R.id.home_switcherImage));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //mSwitcherImageView.setImageResource(imageIDs[currentIndex]);

                    }
                });
            }

        },1000,5000);
        */
    }

    @Override
    public void viewCart(Cart cart) {
        appBarLayout.setExpanded(false);
        mCart = cart;
        Bundle bundle = new Bundle();
        bundle.putSerializable(CART_KEY, mCart);
        ViewCartFragment frag = new ViewCartFragment();
        frag.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.CustomerContent_layout, frag)
                .commit();
    }

    @Override
    public void continueShopping(Cart cart) {
        startCollapsingToolbar();
        mCart = cart;
        Bundle bundle = new Bundle();
        bundle.putSerializable(CART_KEY, mCart);
        HomeFragment frag = new HomeFragment();
        frag.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.CustomerContent_layout, frag)
                .commit();
    }

    @Override
    public void updateCart(Cart cart) {
        mCart = cart;
    }

    @Override
    public void checkOut(Cart cart) {
        appBarLayout.setExpanded(false);
        mCart = cart;
        Bundle bundle = new Bundle();
        bundle.putSerializable(CART_KEY, mCart);
        PaymentInfoFragment frag = new PaymentInfoFragment();
        frag.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.CustomerContent_layout, frag)
                .commit();
    }

    @Override
    public void paymentInfoViewCart(Cart cart) {
        viewCart(cart);
    }

    @Override
    public void paymentInfoContinue(Cart cart) {
        appBarLayout.setExpanded(true);
        //startCollapsingToolbar();
        mCart = cart;
        Bundle bundle = new Bundle();
        bundle.putSerializable(CART_KEY, mCart);
        SearchingShoppersFragment frag = new SearchingShoppersFragment();
        frag.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.CustomerContent_layout, frag)
                .commit();
    }

    @Override
    public ShippingAddress getShippAdd() {
        return mShippingAddress;
    }

    @Override
    public CardInfo getCardInfo() {
        return mCardInfo;
    }

    @Override
    public void shopperFound(Cart cart) {
        appBarLayout.setExpanded(false);
        mCart = cart;
        Bundle bundle = new Bundle();
        bundle.putSerializable(CART_KEY, mCart);
        FoundShopperFragment frag = new FoundShopperFragment();
        frag.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.CustomerContent_layout, frag)
                .commit();

    }

    @Override
    public void orderComfirmed(Order order) {
        appBarLayout.setExpanded(true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ORDER_KEY, order);
        OrderSuccessFragment frag = new OrderSuccessFragment();
        frag.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.CustomerContent_layout, frag)
                .commit();

        mCart = new Cart("Kennedy");

    }

    @Override
    public void foundShopperViewCart(Cart cart) {
        viewCart(cart);
    }

    @Override
    public void trackOrder(String orderNumber) {

    }

    @Override
    public void storeSelected(String storeName) {
        mStore = storeName;
        mHomeStoreName.setText(storeName);
        continueShopping(mCart);
    }

    @Override
    public void customerACCUpdated(ShippingAddress shippAdd, CardInfo card) {
        setShippingAddress(shippAdd);
        setCardInfo(card);
    }

    @Override
    public void onPause() {
        super.onPause();

        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putString(SHAREDPREF_USERNAMEKEY, mUserName)
                .apply();

    }
}
