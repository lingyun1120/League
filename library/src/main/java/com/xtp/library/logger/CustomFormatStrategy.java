package com.xtp.library.logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.LogcatLogStrategy;

/**
 * Base on PrettyFormatStrategy.java
 * remove stack information
 */
public class CustomFormatStrategy implements FormatStrategy {
    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;
    private static final int LINE_LENGTH  = 110;

    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '┌';
    private static final char BOTTOM_LEFT_CORNER = '└';
    private static final char MIDDLE_CORNER = '├';
    private static final char HORIZONTAL_LINE = '│';
    private static final String DOUBLE_DIVIDER = "────────────────────────────────────────────────────";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    private final boolean showThreadInfo;
    @NonNull
    private final LogStrategy logStrategy;
    @Nullable
    private final String tag;

    private CustomFormatStrategy(@NonNull Builder builder) {
        showThreadInfo = builder.showThreadInfo;
        logStrategy = builder.logStrategy;
        tag = builder.tag;
    }

    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void log(int priority, @Nullable String onceOnlyTag, @NonNull String message) {

        String tag = formatTag(onceOnlyTag);

        logTopBorder(priority, tag);
        logHeaderContent(priority, tag);

        //get bytes of message with system's default charset (which is UTF-8 for Android)
        byte[] bytes = message.getBytes();
        int length = bytes.length;
        if (length <= CHUNK_SIZE) {
            logContent(priority, tag, message);
            logBottomBorder(priority, tag);
            return;
        }
        for (int i = 0; i < length; i += CHUNK_SIZE) {
            int count = Math.min(length - i, CHUNK_SIZE);
            //create a new String with system's default charset (which is UTF-8 for Android)
            logContent(priority, tag, new String(bytes, i, count));
        }
        logBottomBorder(priority, tag);
    }

    private void logTopBorder(int logType, @Nullable String tag) {
        logChunk(logType, tag, MIDDLE_BORDER);
    }

    private void logHeaderContent(int logType, @Nullable String tag) {
        if (showThreadInfo) {
            logChunk(logType, tag, HORIZONTAL_LINE + "────── Thread: [" + Thread.currentThread().getName() + "] ──────");
        }
    }

    private void logBottomBorder(int logType, @Nullable String tag) {
//        logChunk(logType, tag, BOTTOM_BORDER);
    }

    private void logDivider(int logType, @Nullable String tag) {
        logChunk(logType, tag, MIDDLE_BORDER);
    }

    private void logContent(int logType, @Nullable String tag, @NonNull String chunk) {
//        chunk = chunk.replaceAll(System.getProperty("line.separator"), " ");
//        List<String> list = new ArrayList<>();
//        int size = (chunk.length() / LINE_LENGTH) == 0 ? 1 : (chunk.length() / LINE_LENGTH) + 1;
//        for (int index = 0; index < size; index++) {
//            int start = index * LINE_LENGTH;
//            int end = (index + 1) * LINE_LENGTH > chunk.length() ? chunk.length() : (index + 1) * LINE_LENGTH;
//            String childStr = chunk.substring(start, end);
//            list.add(childStr);
//        }
        String[] lines = chunk.split(System.getProperty("line.separator"));
        for (String line : lines) {
            logChunk(logType, tag, HORIZONTAL_LINE + " " + line);
        }
    }

    private void logChunk(int priority, @Nullable String tag, @NonNull String chunk) {
        logStrategy.log(priority, tag, chunk);
    }

    @Nullable
    private String formatTag(@Nullable String tag) {
        if (!LogUtil.isEmpty(tag) && !LogUtil.equals(this.tag, tag)) {
            return this.tag + "-" + tag;
        }
        return this.tag;
    }

    public static class Builder {
        boolean showThreadInfo = true;
        @Nullable
        LogStrategy logStrategy;
        @Nullable
        String tag = "PRETTY_LOGGER";

        private Builder() {
        }

        @NonNull
        public Builder showThreadInfo(boolean val) {
            showThreadInfo = val;
            return this;
        }

        @NonNull
        public Builder logStrategy(@Nullable LogStrategy val) {
            logStrategy = val;
            return this;
        }

        @NonNull
        public Builder tag(@Nullable String tag) {
            this.tag = tag;
            return this;
        }

        @NonNull
        public CustomFormatStrategy build() {
            if (logStrategy == null) {
                logStrategy = new LogcatLogStrategy();
            }
            return new CustomFormatStrategy(this);
        }
    }
}
