package edu.calpoly.csc305.parsing;

import edu.calpoly.csc305.Article;
import edu.calpoly.csc305.sources.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ApiParser implements ArticleParser {
  private final Logger logger;

  /**
   * constructs an org.jsonParser for NewsAPI format.
   *
   * @param logger a logger passed in
   */
  public ApiParser(Logger logger) {
    this.logger = logger;
  }

  /** parses sources into a list of Articles.
   *
   * @param src the src of the JSON
   * @return a list of Articles
   * @throws IOException any Exceptions while parsing
   */
  @Override
  public List<Article> parseJson(DataSource src)
      throws IOException {
    List<Article> articles = new ArrayList<>();
    Reader reader = src.openSource();
    JSONTokener tokener = new JSONTokener(reader);
    JSONObject json = new JSONObject(tokener);

    for (Object jsonArticle : json.getJSONArray("articles")) {
      articles.add(parseArticle((JSONObject) jsonArticle));
    }

    reader.close();

    return articles;
  }

  private Article parseArticle(JSONObject obj) {
    String title = null;
    String desc = null;
    String published = null;
    String url = null;
    LocalDateTime publishedAt = null;
    try {
      title = obj.getString("title");
    } catch (Exception e) {
      logger.warning("skipped malformed article: missing title\n");
      return null;
    }
    try {
      desc = obj.getString("description");
    } catch (Exception e) {
      logger.warning("skipped malformed article: missing description\n");
      return null;
    }
    try {
      published = obj.getString("publishedAt");
    } catch (Exception e) {
      logger.warning("skipped malformed article: missing publishedAt\n");
      return null;
    }
    try {
      url = obj.getString("url");
    } catch (Exception e) {
      logger.warning("skipped malformed article: missing url\n");
      return null;
    }

    try {
      publishedAt = LocalDateTime.parse(published,
        DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    } catch (Exception e2) {
      logger.warning("skipped malformed article: malformed publishedAt\n");
      return null;
    }


    if (title.equals("")
        || desc.equals("")
        || published.equals("")
        || url.equals("")) {
      logger.warning("skipped malformed article: empty field prohibited\n");
      return null;
    }
    try {
      return new Article(title, desc, publishedAt, url);
    } catch (Exception e) {
      logger.warning(e.getMessage());
      return null;
    }

  }

}
