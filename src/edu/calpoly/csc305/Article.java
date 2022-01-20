package edu.calpoly.csc305;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {
  private final String title;
  private final String description;
  private final LocalDateTime publishedDateTime;
  private final String url;

  /** constructs an edu.calpoly.csc305.Article.
   *
   * @param title title of the article
   * @param description article desc
   * @param date publication date
   * @param url url of the article
   */
  public Article(
      String title,
      String description,
      LocalDateTime date,
      String url) {
    this.title = title;
    this.description = description;
    this.publishedDateTime = date;
    this.url = url;
  }

  /** title getter.
   *
   * @return title
   */
  public String title() {
    return title;
  }

  /** description getter.
   *
   * @return description
   */
  public String description() {
    return description;
  }

  /** published time getter.
   *
   * @return publishedDateTime
   */
  public String publishedDateTime() {
    return publishedDateTime.toString();
  }

  /** url getter.
   *
   * @return url
   */
  public String url() {
    return url;
  }

  /** overriding equals for comparison.
   *
   * @param o the other object being compared
   * @return true or false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Article article = (Article) o;
    return (title.equals(article.title)
        && description.equals(article.description)
        && publishedDateTime.equals(article.publishedDateTime)
        && url.equals(article.url));
  }

  /** override hashCode with equals method.
   *
   * @return the hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(title, description, publishedDateTime, url);
  }

  /** override toString for pretty printing.
   *
   * @return the printing string
   */
  @Override
  public String toString() {
    return (String.format("%s%n%s%n%s%n%s%n", title, description, publishedDateTime, url));
  }
}
