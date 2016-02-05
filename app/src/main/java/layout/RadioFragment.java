package layout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gi.ikhwanmusicandroid.R;
import com.gi.ikhwanmusicandroid.actions.Dispatcher;
import com.gi.ikhwanmusicandroid.actions.PlayerAction;
import com.gi.ikhwanmusicandroid.services.AudioService;
import com.gi.ikhwanmusicandroid.stores.PlayerStore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RadioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RadioFragment extends Fragment {
    private ImageView playButton;
    private ImageView pauseButton;

    private PlayerStore playerStore;
    private PlayerAction playerAction;

    public RadioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RadioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RadioFragment newInstance(PlayerStore playerStore, PlayerAction playerAction) {
        RadioFragment fragment = new RadioFragment();
        fragment.playerStore = playerStore;
        fragment.playerAction = playerAction;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_radio, container, false);

        playButton = (ImageView) view.findViewById(R.id.play_button);
        pauseButton = (ImageView) view.findViewById(R.id.pause_button);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioService.getInstance().play("http://philae.shoutca.st:8343/stream");

                v.setVisibility(View.INVISIBLE);
                pauseButton.setVisibility(View.VISIBLE);
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioService.getInstance().pause();

                v.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.VISIBLE);
            }
        });

        return view;
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
