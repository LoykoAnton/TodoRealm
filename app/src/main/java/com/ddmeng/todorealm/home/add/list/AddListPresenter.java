package com.ddmeng.todorealm.home.add.list;

import android.text.TextUtils;

import com.ddmeng.todorealm.data.TodoRepository;

import io.realm.Realm;

class AddListPresenter implements AddListContract.Presenter {

    private AddListContract.View view;
    private TodoRepository repository;

    AddListPresenter(TodoRepository todoRepository) {
        this.repository = todoRepository;
    }

    @Override
    public void onDoneButtonClick(String title) {
        if (!TextUtils.isEmpty(title)) {
            repository.addNewList(title, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    if (view != null) {
                        view.exit();
                    }
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    if (view != null) {
                        view.exit();
                    }
                }
            });
        }

    }

    @Override
    public void onCancelButtonClick() {
        view.exit();
    }

    @Override
    public void attachView(AddListContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
