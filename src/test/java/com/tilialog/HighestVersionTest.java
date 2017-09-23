package com.tilialog;

import com.github.zafarkhaja.semver.Version;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class HighestVersionTest {

    @Test
    public void version() {
        MatcherAssert.assertThat(
            new HighestVersion(
                Version.valueOf("0.13.0"),
                Version.valueOf("0.14.0")
            ).version(),
            Matchers.equalTo(
                Version.valueOf("0.14.0")
            )
        );

        MatcherAssert.assertThat(
            new HighestVersion(
                Version.valueOf("0.14.0"),
                Version.valueOf("0.13.0")
            ).version(),
            Matchers.equalTo(
                Version.valueOf("0.14.0")
            )
        );
    }
}
