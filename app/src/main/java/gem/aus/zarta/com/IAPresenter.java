package gem.aus.zarta.com;

public abstract class IAPresenter<T> implements IMain {

    T mView;

    public void setView(T view) {
        this.mView = view;
    }

    IAPresenter() {
    }

    @Override
    public void onDestroy() {
    }
}