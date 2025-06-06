package com.example.assisant.utils;

/**
 * Application constants
 * Contains all constant values used throughout the application
 */
public class Constants {

    // Prevent instantiation
    private Constants() {
    }

    // SharedPreferences keys
    public static final String PREFS_NAME = "campus_assistant_prefs";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_FIRST_LAUNCH = "first_launch";

    // File names for local storage
    public static final String COURSES_FILE = "courses.json";
    public static final String STUDY_PLANS_FILE = "study_plans.json";
    public static final String CHAT_HISTORY_FILE = "chat_history.json";
    public static final String CAMPUS_INFO_FILE = "campus_info.json";

    // Request codes
    public static final int REQUEST_CODE_PERMISSION = 1001;

    // Fragment tags
    public static final String TAG_AI_CHAT = "ai_chat";
    public static final String TAG_COURSE_MANAGEMENT = "course_management";
    public static final String TAG_STUDY_PLAN = "study_plan";
    public static final String TAG_CAMPUS_INFO = "campus_info";

    // Animation durations
    public static final int ANIMATION_DURATION_SHORT = 200;
    public static final int ANIMATION_DURATION_MEDIUM = 300;
    public static final int ANIMATION_DURATION_LONG = 500;

    // Network timeouts
    public static final int NETWORK_TIMEOUT = 30000; // 30 seconds

    // Date formats
    public static final String DATE_FORMAT_DISPLAY = "yyyy-MM-dd";
    public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm";
}