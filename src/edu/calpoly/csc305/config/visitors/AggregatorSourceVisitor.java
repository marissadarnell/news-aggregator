package edu.calpoly.csc305.config.visitors;

import edu.calpoly.csc305.Article;
import edu.calpoly.csc305.ScheduledProcessor;
import edu.calpoly.csc305.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.config.grammars.AggregatorConfigParserBaseVisitor;
import edu.calpoly.csc305.decorators.CacheFilterDecorator;
import edu.calpoly.csc305.decorators.FilterDecorator;
import edu.calpoly.csc305.processing.JsonProcessor;
import edu.calpoly.csc305.processing.Processor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class AggregatorSourceVisitor
    extends AggregatorConfigParserBaseVisitor<List<ScheduledProcessor>> {

  private final Logger logger;

  /** constructs an AggregatorSourceVisitor.
   *
   * @param logger a provided logger
   */
  public AggregatorSourceVisitor(Logger logger) {
    this.logger = logger;
  }

  /** constructs a list of Runnables given a config source.
   *
   * @param ctx a SourcesContext
   * @return a list of corresponding Runnables
   */
  @Override
  public List<ScheduledProcessor> visitSources(AggregatorConfigParser.SourcesContext ctx) {

    List<ScheduledProcessor> processors = new ArrayList<>();
    
    AggregatorSourceFormatVisitor formatVisitor = new AggregatorSourceFormatVisitor(logger);
    AggregatorSourceTypeVisitor typeVisitor = new AggregatorSourceTypeVisitor();
    AggregatorSourceFilterVisitor filterVisitor = new AggregatorSourceFilterVisitor();
    AggregatorSourceDelayVisitor delayVisitor = new AggregatorSourceDelayVisitor();
    BlockingQueue<Article> queue = new LinkedBlockingQueue<>();
    for (AggregatorConfigParser.SourceTypeContext sourceType : ctx.sourceType()) {
      Processor processor = new JsonProcessor(
          sourceType.accept(typeVisitor),
          sourceType.accept(formatVisitor)
      );
      // stack decorator 1
      processor = new FilterDecorator(
          processor,
          sourceType.accept(filterVisitor)
      );
      // stack decorator 2
      processor = new CacheFilterDecorator(processor);

      processors.add(new ScheduledProcessor(
          processor,
          sourceType.accept(delayVisitor),
          queue
      ));
    }

    return processors;

  }
}
