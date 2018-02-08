package com.giftgoapp.jhq;

public abstract class Presenter_Base<T> implements Presenter_Circle_Life {

    protected T mView;

    public void setView(T view) {
        this.mView = view;
    }

    public Presenter_Base() {
    }

    @Override
    public void onDestroy() {
    }
}