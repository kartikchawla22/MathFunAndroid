package net.kartikchawla.mathsfun;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.kartikchawla.mathsfun.ScoreList.ScoreListContent.ScoreListItem;
import net.kartikchawla.mathsfun.databinding.FragmentItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ScoreListItem}.
 */
public class ScoreRecyclerViewAdapter extends RecyclerView.Adapter<ScoreRecyclerViewAdapter.ViewHolder> {
    /**
     * Class Variables
     * mValues is an Array of ScoreListItem
     */
    private final List<ScoreListItem> mValues;

    /**
     * Constructor of this Class
     *
     * @param items
     */

    public ScoreRecyclerViewAdapter(List<ScoreListItem> items) {
        mValues = items;
    }

    /**
     * @param parent
     * @param viewType
     * @return ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    /**
     * This method sets the data in the view.
     *
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText((position + 1) + "");
        holder.mScoreView.setText(mValues.get(position).score.toString());
        holder.mDateTimeView.setText(mValues.get(position).timeStamp);
    }

    /**
     * @return the size of total data that needs to be displayed.
     * We can say it returns total number of rows.
     */

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * This class holds the variables for the fields in which we need to display the data
     */

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mScoreView;
        public final TextView mDateTimeView;
        public ScoreListItem mItem;

        /**
         * Binding the view to data.
         *
         * @param binding
         */

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mDateTimeView = binding.dateTime;
            mScoreView = binding.gameScore;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}