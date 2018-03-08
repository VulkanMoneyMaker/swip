package com.fjjawinshop.giftaa;

public abstract class Oppf<T> implements InterfaceM {

    T mView;

    public void setView(T view) {
        this.mView = view;
    }

    Oppf() {
    }

    @Override
    public void goDestroy() {
    }
}