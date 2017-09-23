package com.tilialog;

import com.github.zafarkhaja.semver.Version;
import com.jcabi.github.Release;
import com.jcabi.github.Repo;
import java.io.IOException;
import java.util.Iterator;

public class HighestRelease {

    private Repo repo;

    public HighestRelease(Repo repo) {
        this.repo = repo;
    }

    public Release release() {
        Iterator<Release> iterator = repo.releases().iterate().iterator();

        if (! iterator.hasNext()) {
            throw new IllegalStateException("No releases");
        }

        Release highestRelease = iterator.next();
        while (iterator.hasNext()) {
            try {
                Release nextRelease = iterator.next();
                if (
                    Version.valueOf(new Release.Smart(nextRelease).tag()).greaterThan(
                        Version.valueOf(new Release.Smart(highestRelease).tag())
                    )
                ) {
                    highestRelease = nextRelease;
                }
            } catch (IOException e) {
                throw new IllegalStateException(
                    "Couldn't read tag property in JSON of Github repository.", e
                );
            }
        }
        return highestRelease;
    }
}
