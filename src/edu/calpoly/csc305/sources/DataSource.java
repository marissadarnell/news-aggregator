package edu.calpoly.csc305.sources;

import java.io.IOException;
import java.io.Reader;

public interface DataSource {

  /** opens a Reader for a DataSource.
   *
   * @return a reader
   * @throws IOException any Exceptions while trying to open a Reader
   */
  Reader openSource() throws IOException;
}
