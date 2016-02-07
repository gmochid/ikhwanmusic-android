package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gi.ikhwanmusicandroid.R;
import com.gi.ikhwanmusicandroid.actions.Dispatcher;
import com.gi.ikhwanmusicandroid.actions.PlayerAction;
import com.gi.ikhwanmusicandroid.stores.SongStore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends Fragment {

    public SearchResultFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultFragment newInstance(SongStore songStore, PlayerAction playerAction, Dispatcher dispatcher) {
        SearchResultFragment fragment = new SearchResultFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false);
    }
}
