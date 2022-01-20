package edu.calpoly.csc305.decorators;

import edu.calpoly.csc305.Article;
import edu.calpoly.csc305.processing.Processor;
import java.io.IOException;
import java.util.List;

public class ProcessorBaseDecorator implements Processor {

  Processor jsonProcessor;

  /** constructs base Decorator of a Processor.
   *
   * @param processor a Processor (likely a JsonProcessor)
   */
  public ProcessorBaseDecorator(Processor processor) {
    this.jsonProcessor = processor;
  }

  /** calls the wrappee extract() method.
   *
   * @return a List of Articles
   * @throws IOException if there is an error during extraction
   */
  @Override
  public List<Article> extract() throws IOException {
    return jsonProcessor.extract();
  }
}
