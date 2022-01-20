package edu.calpoly.csc305.sources;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class StringSource implements DataSource {
  // this DataSource is for testing purposes
  private final String string;

  /** constructs a StringSource.
   *
   * @param inputString the String to be parsed
   */
  public StringSource(String inputString) {
    this.string = inputString;
  }

  /** opens a StringReader for a String DataSource.
   *
   * @return a reader
   * @throws IOException any Exceptions while trying to open a Reader
   */
  @Override
  public Reader openSource() throws IOException {
    return new StringReader(string);
  }
}
