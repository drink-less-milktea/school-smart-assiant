package com.example.assisant.ui.base;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assisant.base.BasePresenter;
import com.example.assisant.base.BaseView;

/**
 * Base Activity class for MVP architecture
 * Provides common functionality for all activities
 * 
 * @param <P> The presenter type that extends BasePresenter
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected P presenter;
    private boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        // Create presenter
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }

        // Initialize views
        initViews();

        // Setup listeners
        setupListeners();

        isActive = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActive = false;
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    public void showLoading() {
        // Default implementation - can be overridden
        // Show progress dialog or loading indicator
    }

    @Override
    public void hideLoading() {
        // Default implementation - can be overridden
        // Hide progress dialog or loading indicator
    }

    @Override
    public void showError(String message) {
        if (message != null && !message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showSuccess(String message) {
        if (message != null && !message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isActive() {
        return isActive && !isFinishing() && !isDestroyed();
    }

    /**
     * Get the layout resource ID for this activity
     * 
     * @return Layout resource ID
     */
    protected abstract int getLayoutId();

    /**
     * Create the presenter for this activity
     * 
     * @return The presenter instance
     */
    protected abstract P createPresenter();

    /**
     * Initialize views after setContentView
     */
    protected abstract void initViews();

    /**
     * Setup click listeners and other event handlers
     */
    protected abstract void setupListeners();
}