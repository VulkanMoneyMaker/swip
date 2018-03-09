package coma.vouchreal.ggrt;

public abstract class ITotal<T> implements IPresenters {

    T mView;

    public void setView(T view) {
        this.mView = view;
    }

    ITotal() {
    }

    @Override
    public void onDestroy() {
    }
}