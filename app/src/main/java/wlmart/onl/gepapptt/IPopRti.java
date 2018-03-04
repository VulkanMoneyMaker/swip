package wlmart.onl.gepapptt;

public abstract class IPopRti<T> implements IKekti {

    T mView;

    public void setView(T view) {
        this.mView = view;
    }

    IPopRti() {
    }

    @Override
    public void onDestroy() {
    }
}