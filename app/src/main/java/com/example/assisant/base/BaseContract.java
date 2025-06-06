package com.example.assisant.base;

/**
 * Base contract interface for MVP architecture
 * Defines the contract between View and Presenter components
 * Each feature module should extend this interface to define specific contracts
 */
public interface BaseContract {

    /**
     * Base View interface for contract
     * All feature-specific view interfaces should extend this
     */
    interface View extends BaseView {
        // Common view methods are inherited from BaseView
        // Feature-specific methods should be added in extending interfaces
    }

    /**
     * Base Presenter interface for contract
     * All feature-specific presenter interfaces should extend this
     * 
     * @param <V> The view type that extends BaseContract.View
     */
    interface Presenter<V extends BaseContract.View> extends BasePresenter<V> {
        // Common presenter methods are inherited from BasePresenter
        // Feature-specific methods should be added in extending interfaces
    }

    /**
     * Base Model interface for contract
     * All feature-specific model interfaces should extend this
     */
    interface Model {
        /**
         * Clean up model resources
         */
        void destroy();
    }
}