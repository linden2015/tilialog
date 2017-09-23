package com.tilialog;

import com.github.zafarkhaja.semver.Version;

public interface AppIsOutdated {

    public boolean asBoolean();

    public Version runningVersion();
}
