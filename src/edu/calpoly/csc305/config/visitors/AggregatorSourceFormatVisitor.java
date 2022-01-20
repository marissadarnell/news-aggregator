package edu.calpoly.csc305.config.visitors;

import edu.calpoly.csc305.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.config.grammars.AggregatorConfigParserBaseVisitor;
import edu.calpoly.csc305.parsing.ApiParser;
import edu.calpoly.csc305.parsing.ArticleParser;
import edu.calpoly.csc305.parsing.SimpleParser;
import java.util.logging.Logger;

public class AggregatorSourceFormatVisitor
    extends AggregatorConfigParserBaseVisitor<ArticleParser> {

  private final Logger logger;

  /** constructs an AggregatorSourceFormatVisitor.
   *
   * @param logger a given logger
   */
  public AggregatorSourceFormatVisitor(Logger logger) {
    this.logger = logger;
  }

  /** gets the ArticleParser for a file.
   *
   * @param ctx FileSourceTypeContext
   * @return the corresponding ArticleParser
   */
  @Override
  public ArticleParser visitFileSourceType(AggregatorConfigParser.FileSourceTypeContext ctx) {
    return ctx.format().accept(this);
  }

  /** gets the ArticleParser for a url.
   *
   * @param ctx UrlSourceTypeContext
   * @return the corresponding ArticleParser
   */
  @Override
  public ArticleParser visitUrlSourceType(AggregatorConfigParser.UrlSourceTypeContext ctx) {
    return ctx.format().accept(this);
  }

  /** determines if simple or Api format.
   *
   * @param ctx FormatContext
   * @return the corresponding ArticleParser
   */
  @Override
  public ArticleParser visitFormat(AggregatorConfigParser.FormatContext ctx) {
    return ctx.format_option().accept(this);
  }


  /** creates an ArticleParser for the given format context.
   *
   * @param ctx SimpleFormatContext
   * @return a (SimpleParser implements) edu.calpoly.csc305.Article Parser
   */
  @Override
  public ArticleParser visitSimpleFormat(AggregatorConfigParser.SimpleFormatContext ctx) {
    return new SimpleParser(logger);
  }

  /** creates an ArticleParser for the given format context.
   *
   * @param ctx a NewsApiFormatContext
   * @return a (ApiParser implements) ArticleParser
   */
  @Override
  public ArticleParser visitNewsApiFormat(AggregatorConfigParser.NewsApiFormatContext ctx) {
    return new ApiParser(logger);
  }
}
