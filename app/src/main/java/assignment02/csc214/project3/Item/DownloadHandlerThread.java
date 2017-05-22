package assignment02.csc214.project3.Item;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;

import assignment02.csc214.project3.Models.Item;


/**
 * Created by Kennedy on 4/24/2017.
 */

public class DownloadHandlerThread extends HandlerThread {
    private static final String TAG = "GetDownloadHandlerTag";
    private static final int DOWNLOAD_IMAGE = 1967;
    private static Bitmap mImage;
    private static ImageView mImageView;

    private static List<Item> mItemList;

    public List<Item> getItemList() {
        return mItemList;
    }

    public  static Bitmap getImage() {
        return mImage;
    }

    public interface DownloadProgressListener extends JobListener<Integer> {
    }

    private Handler mHandler;
    private Handler mResponseHandler;
    private DownloadProgressListener mListener;

    public DownloadHandlerThread(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    public void setImageView(ImageView ImgView){
        mImageView = ImgView;
    }

    public void setDownloadProgressListener(DownloadProgressListener listener) {
        mListener = listener;
    }

    public void downloadImage(List<Item> item) {
        mHandler.obtainMessage(DOWNLOAD_IMAGE, item).sendToTarget();
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new DownloadHandler(mResponseHandler, mListener);
    }

    private static class DownloadHandler extends Handler {
        private final Handler mResponseHandler;
        private final DownloadProgressListener mListener;

        DownloadHandler(Handler responseHandler, DownloadProgressListener listener) {
            mResponseHandler = responseHandler;
            mListener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            if(msg.what == DOWNLOAD_IMAGE) {
                List<Item> item = (List<Item>) msg.obj;
                //String url = "https://cdn.pixabay.com/photo/2015/02/10/21/28/flower-631765_960_720.jpg";
                HttpHelper helper = new HttpHelper();
                        try {
                            for(int i = 0; i<10; i++)
                            {
                                String url = item.get(i).getItemImageUrl();
                                final byte[] data = helper.getBytes(url);
                                item.get(i).setItemImage(getScaledBitmap(data, 560, 631));
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                mItemList = item;
                mResponseHandler.post(new JobCompletePoster(mListener));
            }
        }

        public Bitmap getScaledBitmap(byte[] data, int width, int height){

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length,options);
            float srcWidth = options.outWidth;
            float srcHeight = options.outHeight;
            int sampleSize = 1;
            if(srcHeight > height || srcWidth > width ) {
                if(srcWidth > srcHeight) {
                    sampleSize = Math.round(srcHeight / height);
                }
                else {
                    sampleSize = Math.round(srcWidth / width);
                }
            }
            BitmapFactory.Options scaledOptions =
                    new BitmapFactory.Options();
            scaledOptions.inSampleSize = sampleSize;
            return BitmapFactory.decodeByteArray(data, 0, data.length, scaledOptions);
        }
    }
}
