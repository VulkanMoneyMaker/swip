package fonbet.svakionline.here;

public abstract class Presenter_Base<T> implements Life {

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