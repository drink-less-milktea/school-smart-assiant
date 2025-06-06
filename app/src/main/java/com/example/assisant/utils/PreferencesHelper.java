package com.example.assisant.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Helper class for SharedPreferences operations
 * Provides convenient methods for storing and retrieving preferences
 */
public class PreferencesHelper {

    private final SharedPreferences preferences;

    public PreferencesHelper(Context context) {
        preferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Store a string value
     * 
     * @param key   The preference key
     * @param value The value to store
     */
    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    /**
     * Retrieve a string value
     * 
     * @param key          The preference key
     * @param defaultValue Default value if key doesn't exist
     * @return The stored value or default value
     */
    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    /**
     * Store a boolean value
     * 
     * @param key   The preference key
     * @param value The value to store
     */
    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    /**
     * Retrieve a boolean value
     * 
     * @param key          The preference key
     * @param defaultValue Default value if key doesn't exist
     * @return The stored value or default value
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     * Store an integer value
     * 
     * @param key   The preference key
     * @param value The value to store
     */
    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    /**
     * Retrieve an integer value
     * 
     * @param key          The preference key
     * @param defaultValue Default value if key doesn't exist
     * @return The stored value or default value
     */
    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    /**
     * Store a long value
     * 
     * @param key   The preference key
     * @param value The value to store
     */
    public void putLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    /**
     * Retrieve a long value
     * 
     * @param key          The preference key
     * @param defaultValue Default value if key doesn't exist
     * @return The stored value or default value
     */
    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    /**
     * Remove a preference
     * 
     * @param key The preference key to remove
     */
    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }

    /**
     * Clear all preferences
     */
    public void clear() {
        preferences.edit().clear().apply();
    }

    /**
     * Check if a key exists
     * 
     * @param key The preference key
     * @return true if key exists, false otherwise
     */
    public boolean contains(String key) {
        return preferences.contains(key);
    }
}