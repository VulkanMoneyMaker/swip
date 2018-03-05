package ghh.auus.fhhh.com;

public abstract class AbstractBasePresenter<T> implements UserPresenter {

    T mView;

    public void setView(T view) {
        this.mView = view;
    }

    AbstractBasePresenter() {
    }

    @Override
    public void onDestroy() {
    }
}