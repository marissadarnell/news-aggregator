package edu.calpoly.csc305.processing;

import edu.calpoly.csc305.Article;
import java.io.IOException;
import java.util.List;

public interface Processor {

  List<Article> extract() throws IOException;
}
