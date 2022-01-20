package edu.calpoly.csc305.decorators;

import edu.calpoly.csc305.Article;
import edu.calpoly.csc305.processing.Processor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CacheFilterDecorator implements Processor {

  private List<Article> oldCache;
  private final Processor processor;

  /**
   * constructs base Decorator of a Processor.
   *
   * @param processor a Processor (likely a JsonProcessor)
   */
  public CacheFilterDecorator(Processor processor) {
    this.processor = processor;
    this.oldCache = new ArrayList<>();
  }

  /** extracts Articles & filters out repeats.
   *
   * @return new Articles
   * @throws IOException any problems during extraction
   */
  @Override
  public List<Article> extract() throws IOException {
    List<Article> unfiltered = processor.extract();
    List<Article> filtered = unfiltered.stream()
        .filter(a -> {
          if (oldCache.contains(a)) { // article is in the cache
            return Boolean.FALSE;
          } else { // article is not yet in the cache
            return Boolean.TRUE;
          }
        })
        .collect(Collectors.toList());
    oldCache = unfiltered; // this fixes the timing problem, since you are adding them all anyway
    return filtered;
  }
}
