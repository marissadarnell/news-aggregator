package edu.calpoly.csc305.decorators;

import edu.calpoly.csc305.Article;
import edu.calpoly.csc305.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.config.visitors.AggregatorSelectorVisitor;
import edu.calpoly.csc305.processing.Processor;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FilterDecorator implements Processor {

  AggregatorConfigParser.SelectorContext filterExpression;
  Processor processor;
  // implements the visitor for the filter composite

  // selector contexts: keyword, nested, and, or

  /** constructs a FilterDecorator.
   *
   * @param processor a Processor
   * @param filterExpression a SelectorContext from the source filter
   */
  public FilterDecorator(
      Processor processor,
      AggregatorConfigParser.SelectorContext filterExpression) {
    this.processor = processor;
    this.filterExpression = filterExpression; // one of the 4 types
  }

  /** extracts then filters the Articles.
   *
   * @return a filtered list of Articles
   * @throws IOException an exception while extracting
   */
  @Override
  public List<Article> extract() throws IOException {
    List<Article> unfiltered = processor.extract();
    return unfiltered
        .stream()
        .filter((article -> {
          if (filterExpression == null) {
            return Boolean.TRUE;
          }
          AggregatorSelectorVisitor selectorVisitor = new AggregatorSelectorVisitor(article);
          return filterExpression.accept(selectorVisitor);
        }))
        .collect(Collectors.toList());
  }

  /** gets the filterExpression.
   *
   * @return the SelectorContext filterExpression
   */
  public AggregatorConfigParser.SelectorContext filterExpression() {
    return filterExpression;
  }
}
