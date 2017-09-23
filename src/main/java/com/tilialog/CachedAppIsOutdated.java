package com.tilialog;

import com.github.zafarkhaja.semver.Version;

public class CachedAppIsOutdated implements AppIsOutdated {

    private AppIsOutdated appIsOutdated;

    private Boolean isOutdated;

    public CachedAppIsOutdated(AppIsOutdated appIsOutdated) {
        this.appIsOutdated = appIsOutdated;
    }

    @Override
    public boolean asBoolean() {
        if (isOutdated != null) {
            return isOutdated;
        }

        isOutdated = appIsOutdated.asBoolean();
        return isOutdated;
    }

    @Override
    public Version runningVersion() {
        return appIsOutdated.runningVersion();
    }
}
