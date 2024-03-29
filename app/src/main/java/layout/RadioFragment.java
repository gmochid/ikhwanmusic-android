package layout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gi.ikhwanmusicandroid.R;
import com.gi.ikhwanmusicandroid.actions.Dispatcher;
import com.gi.ikhwanmusicandroid.actions.PlayerAction;
import com.gi.ikhwanmusicandroid.services.AudioService;
import com.gi.ikhwanmusicandroid.stores.PlayerStore;
import com.squareup.otto.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RadioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RadioFragment extends Fragment {
    private ImageView playButton;
    private ImageView pauseButton;
    private ProgressBar playProgressBar;

    private PlayerStore playerStore;
    private PlayerAction playerAction;
    private Dispatcher dispatcher;

    public RadioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RadioFragment.
     */
    public static RadioFragment newInstance(PlayerStore playerStore, PlayerAction playerAction, Dispatcher dispatcher) {
        RadioFragment fragment = new RadioFragment();
        fragment.playerStore = playerStore;
        fragment.playerAction = playerAction;
        fragment.dispatcher = dispatcher;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_radio, container, false);

        dispatcher.register(this);

        playButton = (ImageView) view.findViewById(R.id.play_button);
        pauseButton = (ImageView) view.findViewById(R.id.pause_button);
        playProgressBar = (ProgressBar) view.findViewById(R.id.play_progress_bar);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playProgressBar.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.INVISIBLE);
                playerAction.playRadio();
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerAction.pause();
            }
        });

        onPlayerStoreChange(null);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dispatcher.unregister(this);
    }

    @Subscribe
    public void onPlayerStoreChange(PlayerStore.PlayerStoreChangeEvent event) {
        if (playerStore.getPlayingMode() == PlayerStore.PlayingMode.RADIO) {
            playButton.setVisibility(
                    playerStore.getPlayingStatus() == PlayerStore.PlayingStatus.PAUSE ? View.VISIBLE : View.INVISIBLE);
            pauseButton.setVisibility(
                    playerStore.getPlayingStatus() == PlayerStore.PlayingStatus.PLAY ? View.VISIBLE : View.INVISIBLE);
        } else {
            playButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.INVISIBLE);
        }
        playProgressBar.setVisibility(View.GONE);
    }
}
