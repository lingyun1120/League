package com.xtp.library.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Logger;

/**
 * This is used to saves log messages to the disk.
 */
public class FileLogAdapter implements LogAdapter {

    @NonNull
    private final FormatStrategy formatStrategy;

    public FileLogAdapter() {
        formatStrategy = CsvFormatStrategy.newBuilder().build();
    }

    public FileLogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.formatStrategy = LogUtil.checkNotNull(formatStrategy);
    }

    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
        if (priority == Logger.ASSERT) {
            return true;
        }
        return false;
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        formatStrategy.log(priority, tag, message);
    }
}
