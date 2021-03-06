package com.ddmeng.todorealm.detail.task;

import com.ddmeng.todorealm.base.BasePresenter;
import com.ddmeng.todorealm.base.BaseView;
import com.ddmeng.todorealm.data.models.Task;

public interface TaskDetailContract {
    interface View extends BaseView {
        void initViews();

        void showEditActionText(String title);

        void updateViews(Task task);

        void exit();
    }

    interface Presenter extends BasePresenter<TaskDetailContract.View> {
        void init(long taskId);

        void onDestroy();

        void onEditActionExpanded();

        void onEditActionCollapsed(String newTitle);

        void onDeleteMenuItemClicked();

        void onNoteEditorActionDone(String newNote);
    }
}
