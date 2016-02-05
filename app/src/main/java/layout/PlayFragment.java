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
import com.gi.ikhwanmusicandroid.actions.Dispatcher;
import com.gi.ikhwanmusicandroid.actions.PlayerAction;
import com.gi.ikhwanmusicandroid.models.Song;
import com.gi.ikhwanmusicandroid.stores.PlayerStore;
import com.squareup.otto.Subscribe;

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

    private PlayerStore playerStore;
    private PlayerAction playerAction;
    private Dispatcher dispatcher;

    public PlayFragment() {
    }

    /**
     * Create instance of PlayFragment to instantly play the audio
     *
     * @return A new instance of fragment PlayFragment.
     */
    public static PlayFragment newInstance(PlayerStore playerStore, PlayerAction playerAction, Dispatcher dispatcher) {
        PlayFragment fragment = new PlayFragment();
        fragment.playerStore = playerStore;
        fragment.playerAction = playerAction;
        fragment.dispatcher = dispatcher;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        dispatcher.register(this);

        playButton = (ImageView) view.findViewById(R.id.play_button);
        pauseButton = (ImageView) view.findViewById(R.id.pause_button);
        titleText = (TextView) view.findViewById(R.id.play_title);

        titleText.setText(playerStore.getCurrentSong().getTitle());
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerAction.playCurrentSong();
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerAction.pause();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dispatcher.unregister(this);
    }

    @Subscribe
    public void onPlayerStoreChange(PlayerStore.PlayerStoreChangeEvent event) {
        titleText.setText(playerStore.getCurrentSong().getTitle());

        playButton.setVisibility(!playerStore.isPlaying() ? View.VISIBLE : View.INVISIBLE);
        pauseButton.setVisibility(playerStore.isPlaying() ? View.VISIBLE : View.INVISIBLE);
    }
}
