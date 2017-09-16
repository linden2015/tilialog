package com.tilialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class PersistedLogsTest {

    @Test
    public void list() {
        MatcherAssert.assertThat(
            new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)).size(),
            Matchers.equalTo(4)
        );
    }

    @Test
    public void split() {
        MatcherAssert.assertThat(
            "".split(","),
            Matchers.equalTo(new String[]{""})
        );
    }

    @Test
    public void load_none() {
        List<LogEntryRow> rows = new PersistedLogs(
            new FkTextFile(""),
            ","
        ).load();
        MatcherAssert.assertThat(rows.size(), Matchers.is(0));
    }

    @Test
    public void load_one() {
        List<LogEntryRow> rows = new PersistedLogs(
            new FkTextFile("a,b,c,d,"),
            ","
        ).load();
        LogEntryRow row = rows.get(0);
        MatcherAssert.assertThat(row.story(), Matchers.equalTo("a"));
        MatcherAssert.assertThat(row.startedAt(), Matchers.equalTo("b"));
        MatcherAssert.assertThat(row.endedAt(), Matchers.equalTo("c"));
        MatcherAssert.assertThat(row.description(), Matchers.equalTo("d"));
    }

    @Test
    public void load_many() {
        List<LogEntryRow> rows = new PersistedLogs(
            new FkTextFile("a,b,c,d,e,f,g,h,"),
            ","
        ).load();

        LogEntryRow firstRow = rows.get(0);
        MatcherAssert.assertThat(firstRow.story(), Matchers.equalTo("a"));
        MatcherAssert.assertThat(firstRow.startedAt(), Matchers.equalTo("b"));
        MatcherAssert.assertThat(firstRow.endedAt(), Matchers.equalTo("c"));
        MatcherAssert.assertThat(firstRow.description(), Matchers.equalTo("d"));

        LogEntryRow secondRow = rows.get(1);
        MatcherAssert.assertThat(secondRow.story(), Matchers.equalTo("e"));
        MatcherAssert.assertThat(secondRow.startedAt(), Matchers.equalTo("f"));
        MatcherAssert.assertThat(secondRow.endedAt(), Matchers.equalTo("g"));
        MatcherAssert.assertThat(secondRow.description(), Matchers.equalTo("h"));
    }

    @Test
    public void load_complex() {
        List<LogEntryRow> rows = new PersistedLogs(
            new FkTextFile("a,,,,,,,,"),
            ","
        ).load();
    }

    @Test
    public void save() throws IOException {
        TextFile file = new FkTextFile("");
        List<Log> rows = new ArrayList<>();
        rows.add(new TlLog("a", "b", "c", "d"));
        rows.add(new TlLog("e", "f", "g", "h"));
        new PersistedLogs(file, ",").save(new DefaultLogs(rows));
        MatcherAssert.assertThat(
            file.readAll(),
            Matchers.equalTo("a,b,c,d,e,f,g,h,")
        );
    }
}