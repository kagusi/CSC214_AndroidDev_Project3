package assignment02.csc214.project3.Models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Kennedy on 4/28/2017.
 */

public class Item  implements Serializable {
    private UUID mItemID;
    private String mItemDetails;
    private float mItemCost;
    private String mItemImageUrl;
    private Bitmap mItemImage;

    public Item() {
        mItemID = UUID.randomUUID();
    }

    public UUID getItemID() {
        return mItemID;
    }

    public void setItemID(UUID itemID) {
        mItemID = itemID;
    }

    public String getItemDetails() {
        return mItemDetails;
    }

    public void setItemDetails(String itemDetails) {
        mItemDetails = itemDetails;
    }

    public float getItemCost() {
        return mItemCost;
    }

    public void setItemCost(float itemCost) {
        mItemCost = itemCost;
    }

    public String getItemImageUrl() {
        return mItemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        mItemImageUrl = itemImageUrl;
    }

    public Bitmap getItemImage() {
        return mItemImage;
    }

    public void setItemImage(Bitmap itemImage) {
        mItemImage = itemImage;
    }
}
