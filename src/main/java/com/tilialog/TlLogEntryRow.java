package com.tilialog;

public class TlLogEntryRow implements LogEntryRow {

    public static String EOL = System.getProperty("line.separator");

    private String story;

    private String startedAt;

    private String endedAt;

    private String description;

    public TlLogEntryRow(String story, String startedAt, String endedAt, String description) {
        this.story = story;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.description = description;
    }

    @Override
    public String story() {
        return story;
    }

    @Override
    public String startedAt() {
        return startedAt;
    }

    @Override
    public String endedAt() {
        return endedAt;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Boolean isEmpty() {
        return
            story.length() == 0 &&
            startedAt.length() == 0 &&
            endedAt.length() == 0 &&
            description.length() == 0
        ;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append(story()).append("\t")
            .append(startedAt()).append("\t")
            .append(endedAt()).append("\t")
            .append(description()).append(EOL)
            .toString();
    }
}
