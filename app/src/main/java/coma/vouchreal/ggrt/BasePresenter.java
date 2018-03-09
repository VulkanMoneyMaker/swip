package coma.vouchreal.ggrt;

public abstract class BasePresenter<T> implements MainPresenter {

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