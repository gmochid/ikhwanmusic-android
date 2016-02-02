package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
 * Activities that contain this fragment must implement the
 * {@link PlayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment {

    private static final String SONG_PARAM = "song";
    private static final String PLAY_PARAM = "play";

    private OnFragmentInteractionListener mListener;
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
        }
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
