package edu.calpoly.csc305.sources;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class UrlSource implements DataSource {
  private final URL url;

  /** constructs a URLSource.
   *
   * @param url the url to be parsed for edu.calpoly.csc305.Article(s)
   */
  public UrlSource(URL url) {
    this.url = url;
  }

  /** opens an InputStreamReader for a URL DataSource.
   *
   * @return a reader
   * @throws IOException any Exceptions while trying to open a Reader
   */
  @Override
  public Reader openSource() throws IOException {
    // shorthand for url.openConnection().getInputStream()
    return new InputStreamReader(url.openStream());
  }
}
