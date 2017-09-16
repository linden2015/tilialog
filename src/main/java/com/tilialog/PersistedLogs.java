package com.tilialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersistedLogs {

    private TextFile textFile;

    private String deliniator;

    public PersistedLogs(TextFile textFile, String deliniator) {
        this.textFile = textFile;
        this.deliniator = deliniator;
    }

    public void save(Logs logs) {
        StringBuilder rows = new StringBuilder();
        for (int i = 0; i < logs.get().size(); i++) {
            Log log = logs.get().get(i);
            rows.append(log.storyCode()).append(deliniator)
                .append(log.startedAt()).append(deliniator)
                .append(log.endedAt()).append(deliniator)
                .append(log.description()).append(deliniator)
            ;
        }
        try {
            textFile.replaceAll(rows.toString());
        } catch (IOException e) {
            throw new RuntimeException(
                "Unrecoverable error while saving log-entry-panel to storage.", e
            );
        }
    }

    public List<LogEntryRow> load() {
        try {
            String[] fields = textFile.readAll().split(deliniator);
            List<LogEntryRow> rows = new ArrayList<>();

            // Guard empty file
            if (fields.length == 1 && fields[0].equals("")) {
                return rows;
            }

            for (int i = 0; i < fields.length; i = i + 4) {
                String startedAt;
                if (i+1 < fields.length) {
                    startedAt = fields[i+1];
                } else {
                    startedAt = "";
                }

                String endedAt;
                if (i+2 < fields.length) {
                    endedAt = fields[i+2];
                } else {
                    endedAt = "";
                }

                String description = "";
                if (i+3 < fields.length) {
                    description = fields[i+3];
                } else {
                    description = "";
                }

                rows.add(
                    new TlLogEntryRow(fields[i], startedAt, endedAt, description)
                );
            }
            return rows;
        } catch (IOException e) {
            throw new RuntimeException(
                "Unrecoverable error while loading log-entry-panel from storage.", e
            );
        }
    }
}
