package com.example.assisant.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据存储管理器
 * 提供统一的数据存储接口，支持JSON文件读写、SharedPreferences操作、数据缓存和导入导出功能
 * 
 * 功能特性：
 * - JSON文件读写操作
 * - SharedPreferences数据持久化
 * - 内存缓存机制
 * - 数据导入导出
 * - 线程安全操作
 * - 异常处理和日志记录
 * 
 * @author Campus AI Assistant Team
 * @version 1.0
 */
public class StorageManager {

    private static final String TAG = "StorageManager";
    private static final String PREFS_NAME = "campus_assistant_prefs";

    // 单例实例
    private static volatile StorageManager instance;

    // 应用上下文
    private final Context context;

    // Gson实例，用于JSON序列化/反序列化
    private final Gson gson;

    // SharedPreferences实例
    private final SharedPreferences sharedPreferences;

    // 内存缓存，使用ConcurrentHashMap确保线程安全
    private final Map<String, Object> memoryCache;

    /**
     * 私有构造函数，实现单例模式
     * 
     * @param context 应用上下文
     */
    public StorageManager(Context context) {
        this.context = context.getApplicationContext();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        this.sharedPreferences = this.context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.memoryCache = new ConcurrentHashMap<>();

        Log.d(TAG, "StorageManager initialized");
    }

    /**
     * 获取StorageManager单例实例
     * 
     * @param context 应用上下文
     * @return StorageManager实例
     */
    public static StorageManager getInstance(Context context) {
        if (instance == null) {
            synchronized (StorageManager.class) {
                if (instance == null) {
                    instance = new StorageManager(context);
                }
            }
        }
        return instance;
    }

    // ==================== JSON文件操作 ====================

    /**
     * 将对象保存到JSON文件
     * 
     * @param data     要保存的数据对象
     * @param fileName 文件名
     * @param <T>      数据类型
     * @return 保存是否成功
     */
    public <T> boolean saveToJsonFile(T data, String fileName) {
        if (data == null) {
            Log.w(TAG, "Cannot save null data to file: " + fileName);
            return false;
        }

        if (fileName == null || fileName.trim().isEmpty()) {
            Log.w(TAG, "Invalid file name provided");
            return false;
        }

        try {
            File file = new File(context.getFilesDir(), fileName);

            // 确保父目录存在
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            // 将数据序列化为JSON并写入文件
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(data, writer);
                writer.flush();
            }

            Log.d(TAG, "Successfully saved data to file: " + fileName);
            return true;

        } catch (IOException e) {
            Log.e(TAG, "Failed to save data to file: " + fileName, e);
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error while saving data to file: " + fileName, e);
            return false;
        }
    }

    /**
     * 从JSON文件加载对象
     * 
     * @param fileName 文件名
     * @param type     数据类型
     * @param <T>      数据类型
     * @return 加载的数据对象，失败时返回null
     */
    public <T> T loadFromJsonFile(String fileName, Type type) {
        if (fileName == null || fileName.trim().isEmpty()) {
            Log.w(TAG, "Invalid file name provided");
            return null;
        }

        try {
            File file = new File(context.getFilesDir(), fileName);

            if (!file.exists()) {
                Log.w(TAG, "File does not exist: " + fileName);
                return null;
            }

            // 从文件读取JSON并反序列化为对象
            try (FileReader reader = new FileReader(file)) {
                T result = gson.fromJson(reader, type);
                Log.d(TAG, "Successfully loaded data from file: " + fileName);
                return result;
            }

        } catch (JsonSyntaxException e) {
            Log.e(TAG, "Invalid JSON format in file: " + fileName, e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "Failed to load data from file: " + fileName, e);
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error while loading data from file: " + fileName, e);
            return null;
        }
    }

    // ==================== SharedPreferences操作 ====================

    /**
     * 保存字符串到SharedPreferences
     * 
     * @param key   键
     * @param value 值
     * @return 保存是否成功
     */
    public boolean savePreference(String key, String value) {
        if (key == null || key.trim().isEmpty()) {
            Log.w(TAG, "Invalid key provided for preference");
            return false;
        }

        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            boolean result = editor.commit();

            if (result) {
                Log.d(TAG, "Successfully saved string preference: " + key);
            } else {
                Log.w(TAG, "Failed to save string preference: " + key);
            }

            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error saving string preference: " + key, e);
            return false;
        }
    }

    /**
     * 保存布尔值到SharedPreferences
     * 
     * @param key   键
     * @param value 值
     * @return 保存是否成功
     */
    public boolean savePreference(String key, boolean value) {
        if (key == null || key.trim().isEmpty()) {
            Log.w(TAG, "Invalid key provided for preference");
            return false;
        }

        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            boolean result = editor.commit();

            if (result) {
                Log.d(TAG, "Successfully saved boolean preference: " + key);
            } else {
                Log.w(TAG, "Failed to save boolean preference: " + key);
            }

            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error saving boolean preference: " + key, e);
            return false;
        }
    }

    /**
     * 保存整数到SharedPreferences
     * 
     * @param key   键
     * @param value 值
     * @return 保存是否成功
     */
    public boolean savePreference(String key, int value) {
        if (key == null || key.trim().isEmpty()) {
            Log.w(TAG, "Invalid key provided for preference");
            return false;
        }

        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            boolean result = editor.commit();

            if (result) {
                Log.d(TAG, "Successfully saved int preference: " + key);
            } else {
                Log.w(TAG, "Failed to save int preference: " + key);
            }

            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error saving int preference: " + key, e);
            return false;
        }
    }

    /**
     * 保存长整数到SharedPreferences
     * 
     * @param key   键
     * @param value 值
     * @return 保存是否成功
     */
    public boolean savePreference(String key, long value) {
        if (key == null || key.trim().isEmpty()) {
            Log.w(TAG, "Invalid key provided for preference");
            return false;
        }

        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(key, value);
            boolean result = editor.commit();

            if (result) {
                Log.d(TAG, "Successfully saved long preference: " + key);
            } else {
                Log.w(TAG, "Failed to save long preference: " + key);
            }

            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error saving long preference: " + key, e);
            return false;
        }
    }

    /**
     * 从SharedPreferences获取字符串
     * 
     * @param key          键
     * @param defaultValue 默认值
     * @return 获取的值
     */
    public String getPreference(String key, String defaultValue) {
        if (key == null || key.trim().isEmpty()) {
            Log.w(TAG, "Invalid key provided for preference");
            return defaultValue;
        }

        try {
            String result = sharedPreferences.getString(key, defaultValue);
            Log.d(TAG, "Retrieved string preference: " + key);
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving string preference: " + key, e);
            return defaultValue;
        }
    }

    /**
     * 从SharedPreferences获取布尔值
     * 
     * @param key          键
     * @param defaultValue 默认值
     * @return 获取的值
     */
    public boolean getPreference(String key, boolean defaultValue) {
        if (key == null || key.trim().isEmpty()) {
            Log.w(TAG, "Invalid key provided for preference");
            return defaultValue;
        }

        try {
            boolean result = sharedPreferences.getBoolean(key, defaultValue);
            Log.d(TAG, "Retrieved boolean preference: " + key);
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving boolean preference: " + key, e);
            return defaultValue;
        }
    }

    /**
     * 从SharedPreferences获取整数
     * 
     * @param key          键
     * @param defaultValue 默认值
     * @return 获取的值
     */
    public int getPreference(String key, int defaultValue) {
        if (key == null || key.trim().isEmpty()) {
            Log.w(TAG, "Invalid key provided for preference");
            return defaultValue;
        }

        try {
            int result = sharedPreferences.getInt(key, defaultValue);
            Log.d(TAG, "Retrieved int preference: " + key);
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving int preference: " + key, e);
            return defaultValue;
        }
    }

    /**
     * 从SharedPreferences获取长整数
     * 
     * @param key          键
     * @param defaultValue 默认值
     * @return 获取的值
     */
    public long getPreference(String key, long defaultValue) {
        if (key == null || key.trim().isEmpty()) {
            Log.w(TAG, "Invalid key provided for preference");
            return defaultValue;
        }

        try {
            long result = sharedPreferences.getLong(key, defaultValue);
            Log.d(TAG, "Retrieved long preference: " + key);
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving long preference: " + key, e);
            return defaultValue;
        }
    }

    // ==================== 内存缓存操作 ====================

    /**
     * 将数据存储到内存缓存
     * 
     * @param key  缓存键
     * @param data 要缓存的数据
     * @param <T>  数据类型
     */
    public <T> void cacheData(String key, T data) {
        if (key == null || key.trim().isEmpty()) {
            Log.w(TAG, "Invalid cache key provided");
            return;
        }

        try {
            if (data == null) {
                memoryCache.remove(key);
                Log.d(TAG, "Removed cache entry: " + key);
            } else {
                memoryCache.put(key, data);
                Log.d(TAG, "Cached data with key: " + key);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error caching data with key: " + key, e);
        }
    }

    /**
     * 从内存缓存获取数据
     * 
     * @param key   缓存键
     * @param clazz 数据类型
     * @param <T>   数据类型
     * @return 缓存的数据，不存在时返回null
     */
    @SuppressWarnings("unchecked")
    public <T> T getCachedData(String key, Class<T> clazz) {
        if (key == null || key.trim().isEmpty()) {
            Log.w(TAG, "Invalid cache key provided");
            return null;
        }

        try {
            Object cachedData = memoryCache.get(key);
            if (cachedData != null && clazz.isInstance(cachedData)) {
                Log.d(TAG, "Retrieved cached data with key: " + key);
                return (T) cachedData;
            } else {
                Log.d(TAG, "No cached data found for key: " + key);
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving cached data with key: " + key, e);
            return null;
        }
    }

    /**
     * 从内存缓存获取数据（支持Type参数）
     * 
     * @param key  缓存键
     * @param type 数据类型
     * @param <T>  数据类型
     * @return 缓存的数据，不存在时返回null
     */
    @SuppressWarnings("unchecked")
    public <T> T getCachedData(String key, Type type) {
        if (key == null || key.trim().isEmpty()) {
            Log.w(TAG, "Invalid cache key provided");
            return null;
        }

        try {
            Object cachedData = memoryCache.get(key);
            if (cachedData != null) {
                // 将缓存数据转换为JSON字符串再反序列化为目标类型
                String dataJson = gson.toJson(cachedData);
                T result = gson.fromJson(dataJson, type);
                Log.d(TAG, "Retrieved cached data with key: " + key);
                return result;
            } else {
                Log.d(TAG, "No cached data found for key: " + key);
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving cached data with key: " + key, e);
            return null;
        }
    }

    /**
     * 清除指定键的缓存
     * 
     * @param key 缓存键
     */
    public void clearCache(String key) {
        if (key == null || key.trim().isEmpty()) {
            Log.w(TAG, "Invalid cache key provided");
            return;
        }

        try {
            Object removed = memoryCache.remove(key);
            if (removed != null) {
                Log.d(TAG, "Cleared cache for key: " + key);
            } else {
                Log.d(TAG, "No cache found for key: " + key);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error clearing cache for key: " + key, e);
        }
    }

    /**
     * 清除所有缓存
     */
    public void clearAllCache() {
        try {
            int size = memoryCache.size();
            memoryCache.clear();
            Log.d(TAG, "Cleared all cache entries, count: " + size);
        } catch (Exception e) {
            Log.e(TAG, "Error clearing all cache", e);
        }
    }

    /**
     * 获取缓存大小
     * 
     * @return 缓存条目数量
     */
    public int getCacheSize() {
        try {
            return memoryCache.size();
        } catch (Exception e) {
            Log.e(TAG, "Error getting cache size", e);
            return 0;
        }
    }

    // ==================== 数据导入导出 ====================

    /**
     * 导出数据到指定文件
     * 
     * @param data     要导出的数据
     * @param fileName 导出文件名
     * @param <T>      数据类型
     * @return 导出是否成功
     */
    public <T> boolean exportData(T data, String fileName) {
        if (data == null) {
            Log.w(TAG, "Cannot export null data");
            return false;
        }

        if (fileName == null || fileName.trim().isEmpty()) {
            Log.w(TAG, "Invalid export file name");
            return false;
        }

        try {
            // 创建导出数据的包装对象
            Map<String, Object> exportWrapper = new HashMap<>();
            exportWrapper.put("exportTime", System.currentTimeMillis());
            exportWrapper.put("dataType", data.getClass().getSimpleName());
            exportWrapper.put("data", data);

            boolean result = saveToJsonFile(exportWrapper, fileName);
            if (result) {
                Log.d(TAG, "Successfully exported data to: " + fileName);
            } else {
                Log.w(TAG, "Failed to export data to: " + fileName);
            }

            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error exporting data to: " + fileName, e);
            return false;
        }
    }

    /**
     * 从指定文件导入数据
     * 
     * @param fileName 导入文件名
     * @param dataType 数据类型
     * @param <T>      数据类型
     * @return 导入的数据，失败时返回null
     */
    @SuppressWarnings("unchecked")
    public <T> T importData(String fileName, Type dataType) {
        if (fileName == null || fileName.trim().isEmpty()) {
            Log.w(TAG, "Invalid import file name");
            return null;
        }

        try {
            // 加载导出的包装对象
            Type wrapperType = new com.google.gson.reflect.TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> importWrapper = loadFromJsonFile(fileName, wrapperType);

            if (importWrapper == null) {
                Log.w(TAG, "Failed to load import file: " + fileName);
                return null;
            }

            // 提取实际数据
            Object data = importWrapper.get("data");
            if (data == null) {
                Log.w(TAG, "No data found in import file: " + fileName);
                return null;
            }

            // 将数据转换为JSON字符串再反序列化为目标类型
            String dataJson = gson.toJson(data);
            T result = gson.fromJson(dataJson, dataType);

            Log.d(TAG, "Successfully imported data from: " + fileName);
            return result;

        } catch (Exception e) {
            Log.e(TAG, "Error importing data from: " + fileName, e);
            return null;
        }
    }

    /**
     * 从指定文件导入数据（返回Map类型）
     * 
     * @param fileName 导入文件名
     * @return 导入的数据，失败时返回null
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> importData(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            Log.w(TAG, "Invalid import file name");
            return null;
        }

        try {
            // 加载导出的包装对象
            Type wrapperType = new com.google.gson.reflect.TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> importWrapper = loadFromJsonFile(fileName, wrapperType);

            if (importWrapper == null) {
                Log.w(TAG, "Failed to load import file: " + fileName);
                return null;
            }

            // 提取实际数据
            Object data = importWrapper.get("data");
            if (data == null) {
                Log.w(TAG, "No data found in import file: " + fileName);
                return null;
            }

            // 将数据转换为Map
            if (data instanceof Map) {
                Log.d(TAG, "Successfully imported data from: " + fileName);
                return (Map<String, Object>) data;
            } else {
                Log.w(TAG, "Imported data is not a Map: " + fileName);
                return null;
            }

        } catch (Exception e) {
            Log.e(TAG, "Error importing data from: " + fileName, e);
            return null;
        }
    }

    // ==================== 文件管理操作 ====================

    /**
     * 删除指定文件
     * 
     * @param fileName 文件名
     * @return 删除是否成功
     */
    public boolean deleteFile(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            Log.w(TAG, "Invalid file name for deletion");
            return false;
        }

        try {
            File file = new File(context.getFilesDir(), fileName);

            if (!file.exists()) {
                Log.w(TAG, "File does not exist for deletion: " + fileName);
                return false;
            }

            boolean result = file.delete();
            if (result) {
                Log.d(TAG, "Successfully deleted file: " + fileName);
            } else {
                Log.w(TAG, "Failed to delete file: " + fileName);
            }

            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error deleting file: " + fileName, e);
            return false;
        }
    }

    /**
     * 检查文件是否存在
     * 
     * @param fileName 文件名
     * @return 文件是否存在
     */
    public boolean fileExists(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            Log.w(TAG, "Invalid file name for existence check");
            return false;
        }

        try {
            File file = new File(context.getFilesDir(), fileName);
            boolean exists = file.exists();
            Log.d(TAG, "File existence check for " + fileName + ": " + exists);
            return exists;
        } catch (Exception e) {
            Log.e(TAG, "Error checking file existence: " + fileName, e);
            return false;
        }
    }

    /**
     * 获取文件大小
     * 
     * @param fileName 文件名
     * @return 文件大小（字节），文件不存在时返回-1
     */
    public long getFileSize(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            Log.w(TAG, "Invalid file name for size check");
            return -1;
        }

        try {
            File file = new File(context.getFilesDir(), fileName);

            if (!file.exists()) {
                Log.w(TAG, "File does not exist for size check: " + fileName);
                return -1;
            }

            long size = file.length();
            Log.d(TAG, "File size for " + fileName + ": " + size + " bytes");
            return size;
        } catch (Exception e) {
            Log.e(TAG, "Error getting file size: " + fileName, e);
            return -1;
        }
    }

    /**
     * 获取应用数据目录
     * 
     * @return 数据目录文件对象
     */
    public File getDataDirectory() {
        return context.getFilesDir();
    }

    /**
     * 获取Gson实例
     * 
     * @return Gson实例
     */
    public Gson getGson() {
        return gson;
    }
}