package com.cffdouglgift.toaa;

public abstract class PictureKake<T> implements IMainPre {

    T mView;

    public void setView(T view) {
        this.mView = view;
    }

    PictureKake() {
    }

    @Override
    public void preDestroy() {
    }
}