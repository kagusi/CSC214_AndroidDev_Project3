package assignment02.csc214.project3.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kennedy on 4/29/2017.
 */

public class Cart implements Serializable {
    private String mUserID;
    private List<Item> mItemList;
    private float mTotalCost;
    private int mItemCount;

    public Cart(String userID)
    {
        mUserID = userID;
        mTotalCost = 0;
        mItemCount = 0;
        mItemList =  new ArrayList<>();
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String userID) {
        mUserID = userID;
    }

    public List<Item> getItemList() {
        return mItemList;
    }

    public void setItemList(List<Item> itemList) {
        mItemList = itemList;
    }

    public float getTotalCost() {
        return mTotalCost;
    }

    public void setTotalCost(float totalCost) {
        mTotalCost = totalCost;
    }

    public int getItemCount() {
        return mItemCount;
    }

    public void setItemCount(int itemCount) {
        mItemCount = itemCount;
    }
}
