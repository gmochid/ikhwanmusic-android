package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gi.ikhwanmusicandroid.R;
import com.gi.ikhwanmusicandroid.adapters.SongAdapter;
import com.gi.ikhwanmusicandroid.models.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView songView;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        songView = (RecyclerView) view.findViewById(R.id.song_recycler_view);
        songView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        songView.setLayoutManager(llm);
        songView.setAdapter(new SongAdapter(sampleData()));

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public List<Song> sampleData() {
        List<Song> songs = new ArrayList<Song>();

        songs.add(new Song("Mengenal Nabi", "https://s3-ap-southeast-1.amazonaws.com/ikhwan-music/Mengenal+Nabi.mp3"));
        songs.add(new Song("Satu Peringatan", "https://s3-ap-southeast-1.amazonaws.com/ikhwan-music/Satu+Peringatan.mp3"));
        songs.add(new Song("Sembahyang Hubungan dengan Khaliqnya", "https://s3-ap-southeast-1.amazonaws.com/ikhwan-music/Sembahyang+Hubungan+Hamba+dengan+Khaliqnya.mp3"));

        return songs;
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
