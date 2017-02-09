package com.ddmeng.todorealm.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.home.add.AddListDialogFragment;
import com.ddmeng.todorealm.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class HomeListFragment extends Fragment implements HomeListContract.View, HomeListAdapter.HomeListCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_list)
    RecyclerView homeList;
    private HomeListAdapter homeListAdapter;

    private HomeListContract.Presenter presenter;
    private ActionMode actionMode;

    public HomeListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        presenter = new HomeListPresenter();
        presenter.attachView(this);
        initViews();
        presenter.loadAllLists();
    }

    private void initViews() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        homeList.setLayoutManager(new LinearLayoutManager(getContext()));
        homeListAdapter = new HomeListAdapter(this);
        homeList.setAdapter(homeListAdapter);
    }

    @Override
    public void showAddNewList() {
        AddListDialogFragment addListDialogFragment = new AddListDialogFragment();
        addListDialogFragment.show(getChildFragmentManager(), AddListDialogFragment.TAG);
    }

    @Override
    public void bindListData(RealmResults<TodoList> lists) {
        homeListAdapter.setTodoLists(lists);
    }

    @Override
    public void notifyDataChanged() {
        homeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showListDetail(TodoList list) {
        // TODO
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onListItemClicked(View itemView, TodoList list) {
        if (isInActionMode()) {
            itemView.setSelected(true);
        }
        presenter.onListItemClicked(list);
    }

    @Override
    public void onListItemLongClicked(View itemView, TodoList list) {
        LogUtils.d("onLongClicked");
        if (isInActionMode()) {
            return;
        }
        itemView.setSelected(true);
        presenter.enterActionMode(list);

        actionMode = getActivity().startActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.home_list_context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete: {
                        presenter.deleteSelectedItems();
                        mode.finish();
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                presenter.exitActionMode();
                actionMode = null;

            }
        });

    }

    private boolean isInActionMode() {
        return actionMode != null;
    }
}
