package com.example.themoviedbclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.example.themoviedbclient.repository.db.FilmsDatabase;

public class FilmsListActivity extends AppCompatActivity {

    private FilmsListActivityViewModel filmsListActivityViewModel;

    private RecyclerView recyclerView;
    private FilmsListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films_list);

        initViews();
        initViewModel();

        refreshFilmsList();
    }

    private void initViews() {
        initRecyclerView();
        initSwipeRefreshView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.filmsListRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new FilmsListAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    private void initSwipeRefreshView() {
        swipeRefreshLayout = findViewById(R.id.filmsListSwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFilmsList();
            }
        });
    }

    private void refreshFilmsList() {
        filmsListActivityViewModel.requestFilmsListUpdate();
    }

    private void initViewModel() {
        filmsListActivityViewModel = new ViewModelProvider(this).get(FilmsListActivityViewModel.class);
        FilmsDatabase filmsDatabase = FilmsDatabase.getDatabase(getApplicationContext());
        filmsListActivityViewModel.setFilmsDatabase(filmsDatabase);
        filmsListActivityViewModel.getFilmsList().observe(this, filmsList -> {
//            mAdapter.setFilmsList(filmsList);
            mAdapter.submitList(filmsList);
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}
