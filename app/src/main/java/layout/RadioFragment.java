package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gi.ikhwanmusicandroid.R;
import com.gi.ikhwanmusicandroid.services.AudioService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RadioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RadioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RadioFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ImageView playButton;
    private ImageView pauseButton;

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
    public static RadioFragment newInstance() {
        RadioFragment fragment = new RadioFragment();
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
