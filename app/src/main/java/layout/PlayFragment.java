package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gi.ikhwanmusicandroid.R;
import com.gi.ikhwanmusicandroid.models.Song;
import com.gi.ikhwanmusicandroid.services.AudioService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment {

    private static final String SONG_PARAM = "song";
    private static final String PLAY_PARAM = "play";

    private ImageView playButton;
    private ImageView pauseButton;
    private TextView titleText;

    private Song song = new Song("Mengenal Nabi", "https://s3-ap-southeast-1.amazonaws.com/ikhwan-music/Mengenal+Nabi.mp3", "Qatrunnada");
    private Boolean play = false;

    public PlayFragment() {
        // Required empty public constructor
    }

    /**
     * Create instance of PlayFragment to instantly play the audio
     *
     * @return A new instance of fragment PlayFragment.
     */
    public static PlayFragment newInstance(Song song, Boolean play) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putSerializable(SONG_PARAM, song);
        args.putBoolean(PLAY_PARAM, play);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            song = (Song) getArguments().getSerializable(SONG_PARAM);
            play = getArguments().getBoolean(PLAY_PARAM);
            return;
        }

        if (savedInstanceState != null) {
            song = (Song) savedInstanceState.getSerializable(SONG_PARAM);
            play = savedInstanceState.getBoolean(PLAY_PARAM);
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            song = (Song) savedInstanceState.getSerializable(SONG_PARAM);
            play = savedInstanceState.getBoolean(PLAY_PARAM);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(SONG_PARAM, song);
        outState.putBoolean(PLAY_PARAM, play);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        playButton = (ImageView) view.findViewById(R.id.play_button);
        pauseButton = (ImageView) view.findViewById(R.id.pause_button);
        titleText = (TextView) view.findViewById(R.id.play_title);

        titleText.setText(song.getTitle());
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioService.getInstance().play(song.getUrl());
                play = true;

                v.setVisibility(View.INVISIBLE);
                pauseButton.setVisibility(View.VISIBLE);
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioService.getInstance().pause();
                play = false;

                v.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (play) {
            playSong();
        }
    }

    public void playSong() {
        playButton.callOnClick();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
