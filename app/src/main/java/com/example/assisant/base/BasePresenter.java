package com.example.assisant.base;

/**
 * Base interface for all Presenter components in MVP architecture
 * Defines common methods for view lifecycle management
 * 
 * @param <V> The view type that extends BaseView
 */
public interface BasePresenter<V extends BaseView> {

    /**
     * Attach view to presenter
     * Called when view is created and ready to receive updates
     * 
     * @param view The view to attach
     */
    void attachView(V view);

    /**
     * Detach view from presenter
     * Called when view is destroyed or no longer active
     * Should clean up any ongoing operations
     */
    void detachView();

    /**
     * Get the currently attached view
     * 
     * @return The attached view, or null if no view is attached
     */
    V getView();

    /**
     * Check if view is attached and active
     * 
     * @return true if view is attached and active, false otherwise
     */
    boolean isViewAttached();

    /**
     * Called when presenter should start its work
     * Override this method to implement initial data loading
     */
    void start();

    /**
     * Called when presenter should stop its work
     * Override this method to clean up resources
     */
    void stop();
}