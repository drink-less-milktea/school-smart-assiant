package com.example.assisant.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.assisant.base.BasePresenter;
import com.example.assisant.base.BaseView;

/**
 * Base Fragment class for MVP architecture
 * Provides common functionality for all fragments
 * 
 * @param <P> The presenter type that extends BasePresenter
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    protected P presenter;
    private boolean isActive = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create presenter
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }

        // Initialize views
        initViews(view);

        // Setup listeners
        setupListeners();

        isActive = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.stop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        if (message != null && !message.isEmpty() && getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showSuccess(String message) {
        if (message != null && !message.isEmpty() && getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isActive() {
        return isActive && isAdded() && !isDetached() && getActivity() != null && !getActivity().isFinishing();
    }

    /**
     * Get the layout resource ID for this fragment
     * 
     * @return Layout resource ID
     */
    protected abstract int getLayoutId();

    /**
     * Create the presenter for this fragment
     * 
     * @return The presenter instance
     */
    protected abstract P createPresenter();

    /**
     * Initialize views after onCreateView
     * 
     * @param view The root view of the fragment
     */
    protected abstract void initViews(View view);

    /**
     * Setup click listeners and other event handlers
     */
    protected abstract void setupListeners();
}