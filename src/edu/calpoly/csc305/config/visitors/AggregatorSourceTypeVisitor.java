package edu.calpoly.csc305.config.visitors;

import edu.calpoly.csc305.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.config.grammars.AggregatorConfigParserBaseVisitor;
import edu.calpoly.csc305.sources.DataSource;
import edu.calpoly.csc305.sources.FileSource;
import edu.calpoly.csc305.sources.UrlSource;
import java.net.MalformedURLException;
import java.net.URL;

public class AggregatorSourceTypeVisitor
    extends AggregatorConfigParserBaseVisitor<DataSource> {

  /** creates a DataSource for the given SourceTypeContext.
   *
   * @param ctx a FileSourceTypeContext
   * @return a (FileSource implements) DataSource
   */
  @Override
  public DataSource visitFileSourceType(AggregatorConfigParser.FileSourceTypeContext ctx) {
    // AggregatorSourceLocationVisitor sourceLocationVisitor = new AggregatorSourceLocationVisitor()
    // String fileLocation = ctx.sourceLocation().accept(sourceLocationVisitor); -> unnecessary
    String fileLocation = ctx.sourceLocation().LINE().toString().trim();
    return new FileSource(fileLocation);
  }

  /** creates a DataSource for the given SourceTypeContext.
   *
   * @param ctx a UrlSourceTypeContext
   * @return a (UrlSource implements) DataSource
   */
  @Override
  public DataSource visitUrlSourceType(AggregatorConfigParser.UrlSourceTypeContext ctx) {
    // AggregatorSourceLocationVisitor sourceNameVisitor = new AggregatorSourceLocationVisitor()
    // String addressString = ctx.sourceAddress().accept(sourceNameVisitor) -> unnecessary
    String addressString = ctx.sourceAddress().LINE().toString().trim();

    try {
      URL sourceAddress = new URL(addressString);
      return new UrlSource(sourceAddress);
    } catch (MalformedURLException e) {
      return null;
    }

  }


}
