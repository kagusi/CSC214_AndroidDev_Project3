package assignment02.csc214.project3.Customer;


import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import assignment02.csc214.project3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayMusicFragment extends Fragment {

    private VideoView mVideoView;

    public PlayMusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_music, container, false);

        mVideoView = (VideoView)view.findViewById(R.id.videoView);


        Uri video = Uri.parse("android.resource://" + getActivity().getPackageName() + "/"
                + R.raw.riser);
        mVideoView.setVideoURI(video);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mVideoView.start();
            }
        });

        return view;
    }

}
