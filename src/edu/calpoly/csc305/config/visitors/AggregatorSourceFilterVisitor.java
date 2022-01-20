package edu.calpoly.csc305.config.visitors;

import edu.calpoly.csc305.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.config.grammars.AggregatorConfigParserBaseVisitor;

public class AggregatorSourceFilterVisitor
    extends AggregatorConfigParserBaseVisitor<AggregatorConfigParser.SelectorContext> {

  /** gets the SelectorContext from the source file's filter expression.
   *
   * @param ctx a FileSourceTypeContext
   * @return one of the 4 SelectorContexts, or null if no filter expression
   */
  @Override
  public AggregatorConfigParser.SelectorContext visitFileSourceType(
      AggregatorConfigParser.FileSourceTypeContext ctx) {
    AggregatorConfigParser.FilterContext filter = ctx.filter();

    return (filter.selector());
  }

  /** gets the SelectorContext from the source url's filter expression.
   *
   * @param ctx a UrlSourceTypeContext
   * @return one of the 4 SelextorContexts, or null if no filter expression
   */
  @Override
  public AggregatorConfigParser.SelectorContext visitUrlSourceType(
      AggregatorConfigParser.UrlSourceTypeContext ctx) {
    AggregatorConfigParser.FilterContext filter = ctx.filter();

    return (filter.selector());
  }
}
