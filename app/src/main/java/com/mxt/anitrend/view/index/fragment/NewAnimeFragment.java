package com.mxt.anitrend.view.index.fragment;

import android.os.Bundle;

import com.mxt.anitrend.R;
import com.mxt.anitrend.adapter.recycler.index.SeriesAnimeAdapter;
import com.mxt.anitrend.api.model.Series;
import com.mxt.anitrend.api.structure.Search;
import com.mxt.anitrend.presenter.base.TrendingPresenter;
import com.mxt.anitrend.viewmodel.fragment.DefaultListFragment;

/**
 * @see DefaultListFragment
 */
public class NewAnimeFragment extends DefaultListFragment<Series> {

    private Search mSearch;

    public NewAnimeFragment() {
        // Required empty public constructor
    }

    public static DefaultListFragment newInstance() {
        return new NewAnimeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mColumnSize = R.integer.card_col_size_home;
        mPresenter = new TrendingPresenter(getContext());
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to {@link Activity#onStart() Activity.onStart} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        mSearch = ((TrendingPresenter)mPresenter).getDefaultSearch();
        super.onStart();
    }

    @Override
    public void updateUI() {
        if(recyclerView != null) {
            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
            if(mAdapter == null) {
                mAdapter = new SeriesAnimeAdapter(model, getActivity(), mPresenter.getAppPrefs());
                recyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.onDataSetModified(model);
            }

            if (mAdapter.getItemCount() > 0) {
                progressLayout.showContent();
            } else
                showEmpty(getString(R.string.layout_empty_response));
        }
    }


    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        mSearch.setSort_by("id-desc");
        mSearch.setAiring_data(null);
        mSearch.setItem_status(null);
        mPresenter.beginAsync(this, mSearch);
    }

    /**
     * Normal fragments
     */
    @Override
    public void update() {

    }
}