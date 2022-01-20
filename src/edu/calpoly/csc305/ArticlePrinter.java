package edu.calpoly.csc305;

import java.util.concurrent.BlockingQueue;

public class ArticlePrinter implements Runnable {
  private final BlockingQueue<Article> queue;

  /** constructs an Article Printer Runnable.
   *
   * @param queue a concurrent queue for extracted Articles
   */
  public ArticlePrinter(BlockingQueue<Article> queue) {
    this.queue = queue;
  }

  /** gets the extracted Articles & displays them.
   *
   */
  @Override
  public void run() {
    while (!Thread.interrupted()) {
      try {
        output(queue.take());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private void output(Article a) {
    System.out.printf("title: %s%nat: %s%nurl: %s%n%s%n%n",
        a.title(),
        a.publishedDateTime(),
        a.url(),
        a.description());
  }


}
