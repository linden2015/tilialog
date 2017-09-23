package com.tilialog;

import com.github.zafarkhaja.semver.Version;
import com.jcabi.github.Release;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;


public class VersionTest {

    @Test
    public void versions() {
        Version versionThis = Version.valueOf("0.13.1");
        Version versionHighest = Version.valueOf("0.14.1");
    }
}
