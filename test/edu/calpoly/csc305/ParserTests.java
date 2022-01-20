package edu.calpoly.csc305;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.calpoly.csc305.config.grammars.AggregatorConfigLexer;
import edu.calpoly.csc305.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.config.visitors.AggregatorSelectorVisitor;
import edu.calpoly.csc305.config.visitors.AggregatorSourceVisitor;
import edu.calpoly.csc305.decorators.CacheFilterDecorator;
import edu.calpoly.csc305.decorators.FilterDecorator;
import edu.calpoly.csc305.parsing.ApiParser;
import edu.calpoly.csc305.parsing.ArticleParser;
import edu.calpoly.csc305.parsing.SimpleParser;
import edu.calpoly.csc305.processing.JsonProcessor;
import edu.calpoly.csc305.processing.Processor;
import edu.calpoly.csc305.sources.StringSource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.junit.jupiter.api.Test;



class ParserTests {

  Processor processor = mock(Processor.class);


  @Test
  void testMock() throws IOException {
    List<Article> articles1 = new ArrayList<>();
    articles1.add(new Article(
      "hello1",
      "testing",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));
    articles1.add(new Article(
      "hello2",
      "testing",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));
    articles1.add(new Article(
      "hello3",
      "testing",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));

    List<Article> expected = new ArrayList<>();
    expected.addAll(articles1);

    List<Article> articles2 = new ArrayList<>();
    articles2.add(new Article(
      "hello1",
      "testing",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));
    articles2.add(new Article(
      "hello4",
      "testing",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));
    articles2.add(new Article(
      "hello5",
      "testing",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));
    when(processor.extract())
        .thenReturn(articles1)
    .thenReturn(articles1).thenReturn(articles2);

    assertArrayEquals(articles1.toArray(), processor.extract().toArray());
    assertArrayEquals(articles1.toArray(), processor.extract().toArray());
    assertArrayEquals(articles2.toArray(), processor.extract().toArray());
  }

  @Test
  void testCacheFilterDecorator() throws IOException {

    Processor decorator = new CacheFilterDecorator(processor);

    List<Article> articles1 = new ArrayList<>();
    articles1.add(new Article(
      "hello1",
      "testing",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));
    articles1.add(new Article(
      "hello2",
      "testing",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));
    articles1.add(new Article(
      "hello3",
      "testing",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));

    List<Article> expected = new ArrayList<>();
    expected.addAll(articles1);

    List<Article> articles2 = new ArrayList<>();
    articles2.add(new Article(
      "hello1",
      "testing",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));
    articles2.add(new Article(
      "hello4",
      "testing4",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));
    articles2.add(new Article(
      "hello5",
      "testing5",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));
    when(processor.extract())
      .thenReturn(articles1)
      .thenReturn(articles1).thenReturn(articles2);

    List<Article> actual1 = decorator.extract();
    List<Article> actual2 = decorator.extract();
    List<Article> actual3 = decorator.extract();

    assertEquals(2, actual3.size());


    assertArrayEquals(expected.toArray(), actual1.toArray());
    List<Article> empty = new ArrayList<>();
    assertArrayEquals(empty.toArray(), actual2.toArray());


    List<Article> expected2 = new ArrayList<>();

    expected2.add(new Article(
      "hello4",
      "testing4",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));
    expected2.add(new Article(
      "hello5",
      "testing5",
      LocalDateTime.parse("2021-04-16 09:53:23.709229",
        DateTimeFormatter.ofPattern("y-M-d H:m:s[.SSSSSS]")),
      "bbc"
    ));

    assertArrayEquals(expected2.toArray(), actual3.toArray());

  }



}