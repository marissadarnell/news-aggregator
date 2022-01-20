package edu.calpoly.csc305;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogHandler extends Handler {
  public List<String> logs;

  public LogHandler() {
    logs = new ArrayList<>();
  }

  @Override
  public void publish(LogRecord record) {
    logs.add(record.getMessage());
  }

  @Override
  public void flush() {

  }

  @Override
  public void close() throws SecurityException {

  }
}
