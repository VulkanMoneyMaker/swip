package amazgift.com.mazopq;

public abstract class AbstractTemplate<T> implements ITest {

    T mView;

    public void www(T view) {
        this.mView = view;
    }

    AbstractTemplate() {
    }

    @Override
    public void onDestroy() {
    }
}