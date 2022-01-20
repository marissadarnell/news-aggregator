package edu.calpoly.csc305.parsing;

import edu.calpoly.csc305.Article;
import edu.calpoly.csc305.sources.DataSource;
import java.io.IOException;
import java.util.List;

public interface ArticleParser {

  /** parses sources into a list of Articles.
   *
   * @param src the src of the JSON
   * @return a list of Articles
   * @throws IOException any Exceptions while parsing
   */
  List<Article> parseJson(DataSource src) throws IOException;


}
