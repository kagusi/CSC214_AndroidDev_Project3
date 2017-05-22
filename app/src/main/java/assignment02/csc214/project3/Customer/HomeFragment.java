package assignment02.csc214.project3.Customer;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import assignment02.csc214.project3.Models.Cart;
import assignment02.csc214.project3.Item.DownloadHandlerThread;
import assignment02.csc214.project3.Models.Item;
import assignment02.csc214.project3.R;

import static assignment02.csc214.project3.Customer.HomeActivity.CART_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public interface HomeFragUpdate{
        public void viewCart(Cart cart);
    }

    private HomeFragUpdate mHomeFragUpdate;
    private RecyclerView mRecyclerView;
    private ItemAdapter adapter;
    private List<Item>  mItemList = new ArrayList<>(10);
    private Cart mCart;
    private TextView mCartItemCountTextView;
    private TextView mCartTotalTextView;
    private Button mCheckoutButton;

    private ProgressBar mProgressBar;
    private View mView;

    int currIndex = 0;

    private DownloadHandlerThread mDownloadHandlerThread;
    private ImageView mImageView;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mHomeFragUpdate = (HomeFragUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mHomeFragUpdate = (HomeFragUpdate)activity;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);

       setUpDownLoadHandler();


    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mProgressBar = (ProgressBar)view.findViewById(R.id.customer_ProgressBar);
        mView = view.findViewById(R.id.customer_MainLayout);

        readFile();
        //Initialize and start download handler
        //setUpDownLoadHandler();

        mCart = (Cart)getArguments().getSerializable(CART_KEY);

        mDownloadHandlerThread.setImageView(mImageView);


        mCartItemCountTextView = (TextView)view.findViewById(R.id.home_cartCountTextView);
        mCheckoutButton = (Button)view.findViewById(R.id.home_checkoutButton);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.home_recyclerView);
        adapter = new ItemAdapter(mItemList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, convertedPixel(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

        String count = String.valueOf(mCart.getItemCount()) +" item(s)";
        mCartItemCountTextView.setText(count);

        if(mCart.getItemCount() != 0)
        {
            mCheckoutButton.setEnabled(true);
            String total = "$"+String.valueOf(mCart.getTotalCost())+"  VIEW BAG";
            mCheckoutButton.setText(total);
        }
        else
            mCheckoutButton.setEnabled(false);

        mCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeFragUpdate.viewCart(mCart);
            }
        });



        /*
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    ImageView newView = (ImageView)inflater.inflate(R.layout.image_switcher_view, null);
                    return newView;
                }
        });
        */

        return view;
    }

    public void setUpDownLoadHandler()
    {


        Handler responseHandler = new Handler();
        mDownloadHandlerThread = new DownloadHandlerThread(responseHandler);
        mDownloadHandlerThread.setDownloadProgressListener(new DownloadHandlerThread.DownloadProgressListener() {

            @Override
            public void someWorkCompleted(Integer work) {

            }

            @Override
            public void jobComplete() {
                mItemList = mDownloadHandlerThread.getItemList();
                adapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.GONE);
                mView.setVisibility(View.VISIBLE);

                //scheduleTimer();

            }
        });
        mDownloadHandlerThread.start();
        mDownloadHandlerThread.getLooper();
    }


    public class ItemAdapter extends RecyclerView.Adapter<ItemHolder>  {

        private List<Item> mItmes;

        public ItemAdapter(List<Item> items)
        {
            mItmes = items;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.itemrecylcler_display, parent, false);

            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.bind(mItmes.get(position));

        }

        @Override
        public int getItemCount() {
            return mItmes.size();
        }
    }


    public class ItemHolder extends RecyclerView.ViewHolder  {

        private ImageView mItemImageView;
        private TextView mItemTitleTextView;
        private TextView mItemPriceTextView;
        private Button mItemBagButton;
        private Item mItem;

        public ItemHolder(View itemView) {
            super(itemView);

            mItemImageView = (ImageView)itemView.findViewById(R.id.recyler_itemImageView);
            mItemTitleTextView = (TextView)itemView.findViewById(R.id.recylcer_ItemTitleTextView);
            mItemPriceTextView = (TextView)itemView.findViewById(R.id.recylcer_PriceTextView);
            mItemBagButton = (Button)itemView.findViewById(R.id.recylcer_BagButton);

            mItemBagButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCheckoutButton.setEnabled(true);
                    double totalPrice = mCart.getTotalCost() + mItem.getItemCost();
                    mCart.setTotalCost((float) totalPrice);

                    DecimalFormat df = new DecimalFormat("#.00");
                    String total = "$"+df.format(totalPrice)+" VIEW BAG";

                    mCheckoutButton.setText(total);
                    int itemCount = mCart.getItemCount() + 1;
                    mCart.setItemCount(itemCount);
                    String count = String.valueOf(itemCount) +" item(s)";
                    mCartItemCountTextView.setText(count);
                    mCart.getItemList().add(mItem);
                }
            });

        }

        public void bind(Item item) {
            mItem = item;
            //Glide.with(getContext()).load(item.getItemImage()).into(mItemImageView);
            mItemImageView.setImageBitmap(item.getItemImage());
            mItemTitleTextView.setText(item.getItemDetails());
            String cost = "$"+String.valueOf(item.getItemCost());
            mItemPriceTextView.setText(cost);
        }
    }


    public void readFile()
    {
        Item item = new Item();
        int index = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getActivity().getApplicationContext().getAssets().open("Items.txt")));
            String mLine = reader.readLine();
            while(mLine != null) {
                String[] data = mLine.split("%");
                switch (data[0]) {
                    case "Name":
                        item.setItemDetails(data[1]);
                        break;
                    case "Price":
                        item.setItemCost(Float.parseFloat(data[1]));
                        break;
                    case "Url":
                        item.setItemImageUrl(data[1]);
                        mItemList.add(index, item);
                        item = new Item();
                        break;
                }
                mLine = reader.readLine();
            }
        } catch (IOException e) {
            Log.d("READFILE ", "FILE NOT FOUND");
        }
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }


    private int convertedPixel(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //readFile();
       // setUpDownLoadHandler();

        mDownloadHandlerThread.downloadImage(mItemList);

    }

    public void scheduleTimer()
    {
        //Loop for 5 seconds
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        //Toast.makeText(getContext(), "Shopper Found!", Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.GONE);
                        mView.setVisibility(View.VISIBLE);
                    }
                });
            }

        },4000);
    }

}
