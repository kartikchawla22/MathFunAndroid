package net.kartikchawla.mathsfun;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.kartikchawla.mathsfun.placeholder.PlaceholderContent.PlaceholderItem;
import net.kartikchawla.mathsfun.databinding.FragmentItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ScoreRecyclerViewAdapter extends RecyclerView.Adapter<ScoreRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public ScoreRecyclerViewAdapter(List<PlaceholderItem> items) {
        System.out.println("items");
        System.out.println(items);
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        System.out.println("mValues.get(position).score");
        System.out.println(mValues.get(position).score.toString());
        System.out.println(position);
        holder.mIdView.setText((position + 1) + "");
        holder.mScoreView.setText(mValues.get(position).score.toString());
        holder.mDateTimeView.setText(mValues.get(position).timeStamp);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mScoreView;
        public final TextView mDateTimeView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            System.out.println("binding");
            System.out.println(binding);
            System.out.println(binding.gameScore);
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