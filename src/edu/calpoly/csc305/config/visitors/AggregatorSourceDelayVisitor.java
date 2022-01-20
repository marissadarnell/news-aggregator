package edu.calpoly.csc305.config.visitors;

import edu.calpoly.csc305.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.config.grammars.AggregatorConfigParserBaseVisitor;

public class AggregatorSourceDelayVisitor
    extends AggregatorConfigParserBaseVisitor<Integer> {

  @Override
  public Integer visitFileSourceType(AggregatorConfigParser.FileSourceTypeContext ctx) {
    if (ctx.delay() == null) {
      return 0;
    }
    return Integer.valueOf(ctx.delay().NUM().getText());
  }

  @Override
  public Integer visitUrlSourceType(AggregatorConfigParser.UrlSourceTypeContext ctx) {
    if (ctx.delay() == null) {
      return 0;
    }
    return Integer.valueOf(ctx.delay().NUM().getText());
  }

}
