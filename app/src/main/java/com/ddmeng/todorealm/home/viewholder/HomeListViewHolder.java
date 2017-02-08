package com.ddmeng.todorealm.home.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.models.TodoList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title)
    TextView titleTextView;
    @BindView(R.id.number)
    TextView numberTextView;

    public HomeListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populate(TodoList list) {
        titleTextView.setText(list.getTitle());
        int tasksCount = list.getTasks().size();
        numberTextView.setText(String.valueOf(tasksCount));
    }
}
