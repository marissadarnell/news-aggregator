package edu.calpoly.csc305.sources;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FileSource implements DataSource {
  private final String filename;

  /** constructs a FileSource.
   *
   * @param filename name of the file to be parsed
   */
  public FileSource(String filename) {
    this.filename = filename;
  }

  /** opens a FileReader for a File DataSource.
   *
   * @return a reader
   * @throws IOException any Exceptions while trying to open a Reader
   */
  @Override
  public Reader openSource() throws IOException {
    return new FileReader(filename);
  }
}
