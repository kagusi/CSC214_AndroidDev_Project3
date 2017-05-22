package assignment02.csc214.project3.Customer;


import android.app.Activity;
import android.content.Context;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import assignment02.csc214.project3.Models.Cart;
import assignment02.csc214.project3.Models.Item;
import assignment02.csc214.project3.R;

import static assignment02.csc214.project3.Customer.HomeActivity.CART_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewCartFragment extends Fragment {

    public interface viewCartFragUpdate{
        public void continueShopping(Cart cart);
        public void updateCart(Cart cart);
        public void checkOut(Cart cart);
    }

    private viewCartFragUpdate mViewCartFragUpdate;
    private Cart mCart;
    private Button mContinueShopButton;
    private Button mCheckoutButton;
    private RecyclerView mRecyclerView;
    private ItemAdapter adapter;


    int colorss[] = new int[2];
    int a = 1;


    public ViewCartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mViewCartFragUpdate = (viewCartFragUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mViewCartFragUpdate = (viewCartFragUpdate)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_cart, container, false);

        colorss[0] = getResources().getColor(R.color.rowColor1);
        colorss[1] = getResources().getColor(R.color.rowColor2);

        mCart = (Cart)getArguments().getSerializable(CART_KEY);
        mContinueShopButton = (Button)view.findViewById(R.id.cart_ContinueShopButton);
        mCheckoutButton = (Button)view.findViewById(R.id.cart_checkoutShopButton);


        if(mCart.getItemCount() != 0)
        {
            double res = mCart.getTotalCost();
            DecimalFormat df = new DecimalFormat("#.00");
            String total = "$"+df.format(res);
            mCheckoutButton.setEnabled(true);
            mCheckoutButton.setText("CHECKOUT "+total);
        }
        else
            mCheckoutButton.setEnabled(false);

        adapter = new ItemAdapter(mCart.getItemList());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.cartTable_RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        mContinueShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewCartFragUpdate.continueShopping(mCart);

            }
        });

        mCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewCartFragUpdate.checkOut(mCart);
            }
        });

        return view;
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
            View view = inflater.inflate(R.layout.cart_tablerow, parent, false);

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

        private TextView mCartIndexTextView;
        private TextView mCartItemDetailTextView;
        private TextView mCartCostTextView;
        private TextView mCartItemRemoveTextView;
        private Item mItem;

        public ItemHolder(View itemView) {
            super(itemView);

            a ^= 1;
            itemView.setBackgroundColor(colorss[a]);
            mCartIndexTextView = (TextView)itemView.findViewById(R.id.cartRow_indexTextView);
            mCartItemDetailTextView = (TextView)itemView.findViewById(R.id.cartRow_itemTextView);
            mCartCostTextView = (TextView)itemView.findViewById(R.id.cartRow_costTextView);
            mCartItemRemoveTextView = (TextView)itemView.findViewById(R.id.cartRow_removeTextView);

            mCartItemRemoveTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    float itemCost = mItem.getItemCost();
                    float totalCost = mCart.getTotalCost() - itemCost;
                    mCart.setItemCount(mCart.getItemCount() - 1);
                    mCart.setTotalCost(totalCost);
                    mCart.getItemList().remove(getAdapterPosition());

                    if(mCart.getItemCount() != 0)
                    {
                        double res = mCart.getTotalCost();
                        DecimalFormat df = new DecimalFormat("#.00");
                        String total = "$"+df.format(res);
                        mCheckoutButton.setEnabled(true);
                        mCheckoutButton.setText("CHECKOUT "+total);
                    }
                    else
                    {
                        mCheckoutButton.setEnabled(false);
                        mCheckoutButton.setText("");
                    }
                    mViewCartFragUpdate.updateCart(mCart);
                    adapter.notifyDataSetChanged();

                }
            });

        }

        public void bind(Item item) {
            mItem = item;
            mCartIndexTextView.setText(String.valueOf(getAdapterPosition()));
            String itemInfo = item.getItemDetails();
            mCartItemDetailTextView.setText(itemInfo);
            String itemCost = String.valueOf(item.getItemCost());
            mCartCostTextView.setText("$"+itemCost);
        }
    }

}
