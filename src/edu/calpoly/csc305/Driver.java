package edu.calpoly.csc305;

import edu.calpoly.csc305.parsing.ApiParser;
import edu.calpoly.csc305.parsing.SimpleParser;
import edu.calpoly.csc305.processing.JsonProcessor;
import edu.calpoly.csc305.sources.FileSource;
import edu.calpoly.csc305.sources.UrlSource;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Driver {

  /** demo parses from a list of file names.
   *
   * @param args filenames from command line
   * @throws IOException any Exceptions while
   */
  public static void main(String[] args) throws IOException {

    Logger logger = Logger.getLogger("DriverLogger");

    ApiParser apiParser = new ApiParser(logger);
    SimpleParser simpleParser = new SimpleParser(logger);

    FileSource simpleFile = new FileSource("inputs/simple.json");
    JsonProcessor simpleProcessor1 = new JsonProcessor(simpleFile, simpleParser);

    // grab API key from command line arg[0]
    String apiUrlString = String.format("http://newsapi.org/v2/top-headlines?country=us&apiKey=%s", args[0]);

    UrlSource apiUrl = new UrlSource(new URL(apiUrlString));
    JsonProcessor apiProcessor2 = new JsonProcessor(apiUrl, apiParser);

    FileSource apiFile = new FileSource("inputs/newsapi.json");
    JsonProcessor apiProcessor1 = new JsonProcessor(apiFile, apiParser);

    UrlSource simpleUrl = new UrlSource(new URL("http://users.csc.calpoly.edu/~akeen/courses/csc305/simple.json"));
    JsonProcessor simpleProcessor2 = new JsonProcessor(simpleUrl, simpleParser);


    List<Article> articles = new ArrayList<>();
    articles.addAll(apiProcessor1.extract());
    articles.addAll(apiProcessor2.extract());
    articles.addAll(simpleProcessor1.extract());
    articles.addAll(simpleProcessor2.extract());

    displayArticles(articles);

  }

  /** outputs the list of successfully parsed Articles.
   *
   * @param articles the list of parsed Articles
   */
  public static void displayArticles(List<Article> articles) {
    for (Article a : articles) {
      System.out.printf("title: %s%nat: %s%nurl: %s%n%s%n%n",
          a.title(),
          a.publishedDateTime(),
          a.url(),
          a.description());
    }
  }
}
