package edu.calpoly.csc305;

import edu.calpoly.csc305.processing.Processor;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ScheduledProcessor implements Runnable {
  private final Processor processor;
  private final int delay;
  private final BlockingQueue<Article> articleQueue;

  /** constructs a Runnable ScheduledProcessor.
   *
   * @param processor a Processor
   * @param delay the delay time
   * @param articleQueue the concurrent queue
   */
  public ScheduledProcessor(Processor processor,
                            Integer delay,
                            BlockingQueue<Article> articleQueue) {
    this.processor = processor;
    this.delay = delay;
    this.articleQueue = articleQueue;
  }


  /** gets the delay time.
   *
   * @return the delay in milliseconds
   */
  public int delay() {
    return delay;
  }

  /** the run method for the Runnable.
   *
   */
  @Override
  public void run() {
    try {
      List<Article> articles = processor.extract();
      articles.forEach(a -> {
        try {
          articleQueue.put(a);
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().interrupt();
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** gets the queue.
   *
   * @return the BlockingQueue
   */
  public BlockingQueue<Article> getQueue() {
    return articleQueue;
  }
}
