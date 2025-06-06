package com.example.assisant.base;

/**
 * Base interface for all View components in MVP architecture
 * Defines common methods that all views should implement
 */
public interface BaseView {

    /**
     * Show loading indicator to user
     */
    void showLoading();

    /**
     * Hide loading indicator
     */
    void hideLoading();

    /**
     * Display error message to user
     * 
     * @param message Error message to display
     */
    void showError(String message);

    /**
     * Display success message to user
     * 
     * @param message Success message to display
     */
    void showSuccess(String message);

    /**
     * Check if view is currently active/attached
     * 
     * @return true if view is active, false otherwise
     */
    boolean isActive();
}