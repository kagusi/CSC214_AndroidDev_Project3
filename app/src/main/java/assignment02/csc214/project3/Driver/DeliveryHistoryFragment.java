package assignment02.csc214.project3.Driver;


import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

import assignment02.csc214.project3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryHistoryFragment extends Fragment {

    //private TableLayout mTable;
    //private TableRow mTableRow;
    //private TextView mData;

    public DeliveryHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_delivery_history, container, false);

        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //ShapeDrawable border = new ShapeDrawable(new RectShape());
        //border.getPaint().setStyle(Paint.Style.STROKE);
        //border.getPaint().setColor(Color.BLACK);








        return view;
    }

}
