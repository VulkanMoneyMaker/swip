package com.lidlvoucher.goapper;

public abstract class BasePre<T> implements PresenterJOP {

    T mView;

    public void setView(T view) {
        this.mView = view;
    }

    BasePre() {
    }

    @Override
    public void onDestroy() {
    }
}