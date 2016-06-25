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
import com.gi.ikhwanmusicandroid.actions.PlayerAction;
import com.gi.ikhwanmusicandroid.adapters.SearchResultSongAdapter;
import com.gi.ikhwanmusicandroid.stores.PlayerStore;
import com.gi.ikhwanmusicandroid.stores.SongStore;
import com.squareup.otto.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends Fragment {

    private SongStore songStore;
    private PlayerStore playerStore;
    private PlayerAction playerAction;
    private Dispatcher dispatcher;

    private RecyclerView searchResultView;
    private SearchResultSongAdapter searchResultSongAdapter;

    public SearchResultFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultFragment newInstance(SongStore songStore, PlayerStore playerStore, PlayerAction playerAction, Dispatcher dispatcher) {
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.songStore = songStore;
        fragment.playerStore = playerStore;
        fragment.playerAction = playerAction;
        fragment.dispatcher = dispatcher;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        dispatcher.register(this);

        searchResultView = (RecyclerView) view.findViewById(R.id.song_recycler_view);
        searchResultView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        searchResultView.setLayoutManager(llm);

        searchResultSongAdapter = new SearchResultSongAdapter(songStore, playerStore, playerAction);
        searchResultView.setAdapter(searchResultSongAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dispatcher.unregister(this);
    }

    @Subscribe
    public void onSongStoreSearchUpdated(SongStore.SongStoreSearchChangeEvent event) {
        searchResultSongAdapter.notifyDataSetChanged();

        if (songStore.getQueryResult().isEmpty()) {
            searchResultView.setVisibility(View.GONE);
        } else {
            searchResultView.setVisibility(View.VISIBLE);
        }
    }
}
