package assignment02.csc214.project3.Customer;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import assignment02.csc214.project3.Database.ModelQueryManager;
import assignment02.csc214.project3.Models.Order;
import assignment02.csc214.project3.R;

import static assignment02.csc214.project3.Customer.HomeActivity.USERAME_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private OrderAdapter adapter;
    private List<Order> mOrderList;

    int colorss[] = new int[2];
    int a = 1;


    public OrderHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order_history, container, false);

        colorss[0] = getResources().getColor(R.color.rowColor1);
        colorss[1] = getResources().getColor(R.color.rowColor2);

        String username = getArguments().getString(USERAME_KEY);
        String[] uname = new String[1];
        uname[0] = username;

        mOrderList = ModelQueryManager.get(getContext().getApplicationContext()).getOrderHistory(uname);

        adapter = new OrderAdapter(mOrderList);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.orderHistory_RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        return view;
    }


    public class OrderAdapter extends RecyclerView.Adapter<OrderHolder>  {

        private List<Order> mOrders;

        public OrderAdapter(List<Order> orders)
        {
            mOrders = orders;
        }

        @Override
        public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.order_history_display, parent, false);

            return new OrderHolder(view);
        }

        @Override
        public void onBindViewHolder(OrderHolder holder, int position) {
            holder.bind(mOrders.get(position));

        }

        @Override
        public int getItemCount() {
            return mOrders.size();
        }
    }


    public class OrderHolder extends RecyclerView.ViewHolder  {

        TextView mOrderID;
        TextView mItemsOrdered;
        TextView mDelieryDriver;
        TextView mDateOrdered;
        private Order mItem;

        public OrderHolder(View view) {
            super(view);

            a ^= 1;
            itemView.setBackgroundColor(colorss[a]);
            mOrderID = (TextView)view.findViewById(R.id.orderHistory_OrderIDTextView);
            mItemsOrdered = (TextView)view.findViewById(R.id.orderHistory_ItemsOrdered);
            mDelieryDriver = (TextView)view.findViewById(R.id.orderHistory_DriverTextView);
            mDateOrdered = (TextView)view.findViewById(R.id.orderHistory_DateTextView);

        }

        public void bind(Order order) {
            mItem = order;
            mOrderID.setText(order.getOrderID());
            mItemsOrdered.setText(order.getItemsOrdered());
            mDelieryDriver.setText(order.getDeliveryDriver());
            mDateOrdered.setText(order.getOrderDate());
        }
    }


}
