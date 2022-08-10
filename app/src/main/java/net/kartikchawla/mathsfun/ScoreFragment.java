package net.kartikchawla.mathsfun;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.kartikchawla.mathsfun.ScoreList.ScoreListContent;
import net.kartikchawla.mathsfun.models.DataModel;

/**
 * A fragment representing a list of Items.
 */
public class ScoreFragment extends Fragment {
    /**
     * Class variables
     * mColumnCount is the default value of columns
     */

    private final int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ScoreFragment() {
    }

    /**
     * This method is called when this fragment is created in the memory.
     * Here we load the past game data.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScoreListContent.loadPastGameData(getActivity().getSharedPreferences(DataModel.Constants.PAST_GAMES_FILE, Context.MODE_PRIVATE));
    }

    /**
     * This method is called whenever view is created for this fragment.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new ScoreRecyclerViewAdapter(ScoreListContent.ITEMS));
        }
        return view;
    }
}