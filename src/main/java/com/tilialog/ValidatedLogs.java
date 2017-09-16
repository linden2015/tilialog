package com.tilialog;

import java.util.ArrayList;
import java.util.List;

public class ValidatedLogs implements Logs {

    private Logs logs;

    public ValidatedLogs(Logs logs) {
        this.logs = logs;
    }

    @Override
    public List<Log> get() {
        List<Log> validatedLogs = new ArrayList<>();
        for (Log log : logs.get()) {
            validatedLogs.add(new ValidatedLog(log));
        }
        return validatedLogs;
    }
}
