package com.tilialog;

import com.github.zafarkhaja.semver.Version;

public class HighestVersion {

    private Version v1;

    private Version v2;

    public HighestVersion(String v1, String v2) {
        this(Version.valueOf(v1), Version.valueOf(v2));
    }

    public HighestVersion(Version v1, Version v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Version version() {
        if (v1.greaterThan(v2)) {
            return v1;
        } else {
            return v2;
        }
    }
}
