package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gi.ikhwanmusicandroid.R;
import com.gi.ikhwanmusicandroid.actions.Dispatcher;
import com.gi.ikhwanmusicandroid.adapters.SongAdapter;
import com.gi.ikhwanmusicandroid.stores.SongStore;
import com.squareup.otto.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView songView;

    private SongStore songStore;
    private Dispatcher dispatcher;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(SongStore songStore, Dispatcher dispatcher) {
        HomeFragment fragment = new HomeFragment();
        fragment.songStore = songStore;
        fragment.dispatcher = dispatcher;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dispatcher.register(this);

        songView = (RecyclerView) view.findViewById(R.id.song_recycler_view);
        songView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        songView.setLayoutManager(llm);
        songView.setAdapter(new SongAdapter(songStore));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dispatcher.unregister(this);
    }

    @Subscribe
    public void onSongStoreUpdated(SongStore.SongStoreChangeEvent event) {
        songView.setAdapter(new SongAdapter(songStore));
    }

}