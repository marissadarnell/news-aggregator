package edu.calpoly.csc305;

import edu.calpoly.csc305.config.grammars.AggregatorConfigLexer;
import edu.calpoly.csc305.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.config.grammars.AggregatorConfigParserBaseVisitor;
import edu.calpoly.csc305.config.visitors.AggregatorSourceVisitor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;



public class Asgn5Demo {
  /**
   * Demonstration of asgn5 Scheduled Processors & cache.
   *
   * @param args sequence of presumed file names
   */

  // TODO: for reviewer: need to specify apikey in config file & pass config file as arg
  public static void main(String[] args) {
    Logger logger = Logger.getLogger(Asgn5Demo.class.toString());
    List<ParseTree> parseTrees = parseFiles(args, logger);
    List<List<ScheduledProcessor>> processorLists = gatherVisitorResults(parseTrees,
        new AggregatorSourceVisitor(logger));

    List<ScheduledProcessor> processors = new ArrayList<>();

    for (List<ScheduledProcessor> list : processorLists) {
      processors.addAll(list);
    }



    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
        Runtime.getRuntime().availableProcessors());
    if (!processors.isEmpty()) {
      BlockingQueue<Article> queue = processors.get(0).getQueue();
      scheduler.schedule(new ArticlePrinter(queue), 0, TimeUnit.MILLISECONDS);
    } else {
      return;
    }

    for (ScheduledProcessor processor : processors) {
      if (processor.delay() > 0) {
        scheduler.scheduleWithFixedDelay(processor, 0, processor.delay(), TimeUnit.MILLISECONDS);
      } else {
        scheduler.schedule(processor, 0, TimeUnit.MILLISECONDS);
      }
    }



  }

  private static List<ParseTree> parseFiles(String[] filenames, Logger logger) {
    List<ParseTree> parseTrees = new ArrayList<>();

    for (String filename : filenames) {
      try {
        CommonTokenStream tokens = new CommonTokenStream(
            new AggregatorConfigLexer(CharStreams.fromFileName(filename)));
        AggregatorConfigParser parser = new AggregatorConfigParser(tokens);
        ParseTree parseTree = parser.sources();

        if (parser.getNumberOfSyntaxErrors() == 0) {
          parseTrees.add(parseTree);
        }
      } catch (IOException e) {
        logger.warning(e.getMessage());
      }
    }

    return parseTrees;
  }

  private static <T> List<T> gatherVisitorResults(List<ParseTree> parseTrees,
                                                  AggregatorConfigParserBaseVisitor<T> visitor) {
    List<T> results = new ArrayList<>();

    for (ParseTree parseTree : parseTrees) {
      results.add(parseTree.accept(visitor));
    }

    return results;
  }

}


