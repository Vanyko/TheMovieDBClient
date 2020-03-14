package com.example.themoviedbclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedbclient.repository.entity.Film;

public class FilmsListAdapter extends PagedListAdapter<Film, FilmsListAdapter.MyViewHolder> {

    // Provide a suitable constructor (depends on the kind of dataset)
    public FilmsListAdapter() {
        super(Film.DIFF_CALLBACK);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FilmsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.bindTo(getItem(position));
    }



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView titleTextView;

        public MyViewHolder(View v) {
            super(v);

            titleTextView = v.findViewById(R.id.filmItemTitle);
        }

        public void bindTo(Film film) {
            titleTextView.setText(film.getTitle());
        }
    }
}
