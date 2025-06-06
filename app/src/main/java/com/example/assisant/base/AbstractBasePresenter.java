package com.example.assisant.base;

import java.lang.ref.WeakReference;

/**
 * Abstract base presenter implementation
 * Provides common functionality for all presenters
 * Uses WeakReference to prevent memory leaks
 * 
 * @param <V> The view type that extends BaseView
 */
public abstract class AbstractBasePresenter<V extends BaseView> implements BasePresenter<V> {

    // Use WeakReference to prevent memory leaks
    private WeakReference<V> viewRef;

    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    @Override
    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null && viewRef.get().isActive();
    }

    @Override
    public void start() {
        // Default implementation - override in subclasses if needed
    }

    @Override
    public void stop() {
        // Default implementation - override in subclasses if needed
    }

    /**
     * Helper method to safely execute view operations
     * Only executes if view is attached and active
     * 
     * @param viewAction The action to execute on the view
     */
    protected void executeViewAction(ViewAction<V> viewAction) {
        if (isViewAttached()) {
            viewAction.execute(getView());
        }
    }

    /**
     * Interface for view actions
     * 
     * @param <V> The view type
     */
    protected interface ViewAction<V> {
        void execute(V view);
    }
}