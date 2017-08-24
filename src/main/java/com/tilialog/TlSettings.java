package com.tilialog;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TlSettings implements Settings {

    public static String keyValuePattern = "(?m)^(?<key>\\w+)=(?<value>.*)$";

    public static String testKeyPattern = "^\\w+$";

    public static String testValuePattern = "^\\w+$";

    public static String replaceValueByKeyFormat = "(?m)^%s=.*$";

    private TextFile settingsFile;

    public TlSettings(TextFile settingsFile) {
        this.settingsFile = settingsFile;
    }

    @Override
    public void put(String key, Object value) {
        String valueAsString = String.valueOf(value);
        if (
            ! key.matches(testKeyPattern) ||
                ! valueAsString.matches(testValuePattern)
            ) {
            throw new IllegalStateException(
                "Settings key or value incorrectly formatted."
            );
        }
        try {
            settingsFile.replaceAll(
                settingsFile
                    .readAll()
                    .replaceFirst(
                        replaceValueByKeyPattern(key),
                        key + "=" + valueAsString
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(
                "Couldn't write to settingsfile: " + settingsFile + ".", e
            );
        }
    }

    private String replaceValueByKeyPattern(String key) {
        return String.format(replaceValueByKeyFormat, key);
    }

    @Override
    public String get(String key) {
        try {
            Matcher matcher = Pattern.compile(keyValuePattern).matcher(
                settingsFile.readAll()
            );
            while (matcher.find()) {
                if (matcher.group("key").equals(key)) {
                    return matcher.group("value");
                }
            }
            throw new RuntimeException(
                "Couldn't read settingskey: " + key + "."
            );
        } catch (IOException e) {
            throw new RuntimeException(
                "Couldn't read from settingsfile: " + settingsFile + ".", e
            );
        }
    }
}
