package com.lidlvoucher.goapper;

public abstract class BasePresenter<T> implements PresenterJOP {

    T mView;

    public void setView(T view) {
        this.mView = view;
    }

    BasePresenter() {
    }

    @Override
    public void onDestroy() {
    }
}