package com.tilialog;

import com.github.zafarkhaja.semver.Version;
import com.jcabi.github.Coordinates;
import com.jcabi.github.Release;
import com.jcabi.github.RtGithub;
import java.io.IOException;

public class TlAppIsOutdated implements AppIsOutdated {

    private Version runningVersion;

    public TlAppIsOutdated(Version runningVersion) {
        this.runningVersion = runningVersion;
    }

    @Override
    public boolean asBoolean() {
        try {
            // Get the highest release from Github
            Release highestRelease = new HighestRelease(
                new RtGithub()
                    .repos()
                    .get(
                        new Coordinates.Simple("linden2015", "tilialog")
                    )
            ).release();
            String highestReleaseTag = new Release.Smart(highestRelease).tag();

            if (
                runningVersion.lessThan(new HighestVersion(
                    runningVersion, Version.valueOf(highestReleaseTag)
                ).version())
            ) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            System.out.println("Couldn't read release tag. Github API rate limit may be exceeded.");
            return false;
        }
    }

    @Override
    public Version runningVersion() {
        return runningVersion;
    }
}
