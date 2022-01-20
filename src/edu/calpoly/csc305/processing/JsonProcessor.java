package edu.calpoly.csc305.processing;

import edu.calpoly.csc305.Article;
import edu.calpoly.csc305.parsing.ArticleParser;
import edu.calpoly.csc305.sources.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonProcessor implements Processor {

  private final DataSource src;
  private final ArticleParser parser;

  /** constructs a JsonProcessor.
   *
   * @param src a DataSource (File or URL)
   * @param parser a parser for a certain format (newsAPI or simple)
   */
  public JsonProcessor(DataSource src, ArticleParser parser) {
    this.src = src;
    this.parser = parser;
  }

  /** extracts a list of successfully parsed articles.
   *
   * @return list of parsed articles
   * @throws IOException if there is an exception with the DataSource
   */
  public List<Article> extract() throws IOException {
    List<Article> articles = parser.parseJson(src);
    return articles.stream()
      .filter(Objects::nonNull)
      .collect(Collectors.toList());

  }

  public boolean parser() {
    return parser == null;
  }
}
