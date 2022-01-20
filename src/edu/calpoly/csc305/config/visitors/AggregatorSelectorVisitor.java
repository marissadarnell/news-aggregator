package edu.calpoly.csc305.config.visitors;

import edu.calpoly.csc305.Article;
import edu.calpoly.csc305.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.config.grammars.AggregatorConfigParserBaseVisitor;
import java.util.List;
import java.util.Locale;

public class AggregatorSelectorVisitor
    extends AggregatorConfigParserBaseVisitor<Boolean> {

  String articleString;

  /** constructs a SelectorVisitor given an edu.calpoly.csc305.Article.
   *
   * @param article and edu.calpoly.csc305.Article
   */
  public AggregatorSelectorVisitor(Article article) {
    this.articleString = buildArticleString(article);
  }

  private String buildArticleString(Article article) {
    return (article.title()
      + article.url()
      + article.description()
      + article.publishedDateTime())
      .toLowerCase(Locale.ROOT);
  }

  /** visits the nested expression.
   *
    * @param ctx a NestedExpressionContext
   * @return true: if the nestedExpression is satisfied, false: if not
   */
  @Override
  public Boolean visitNestedExpression(AggregatorConfigParser.NestedExpressionContext ctx) {
    return ctx.selector().accept(this);
  }

  /** visits each selector within the AND expression & evaluates true/false.
   *
   * @param ctx an AndExpressionContext
   * @return true: if the article satisfies the expression, false: if not
   */
  @Override
  public Boolean visitAndExpression(AggregatorConfigParser.AndExpressionContext ctx) {
    List<AggregatorConfigParser.SelectorContext> expressions = ctx.selector();
    for (AggregatorConfigParser.SelectorContext selector : expressions) {
      if (Boolean.FALSE.equals(selector.accept(this))) {
        return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }

  /** determined if the keyword is in the edu.calpoly.csc305.Article result.
   *
   * @param ctx a KeywordExpressionContext
   * @return true: if the keyword is present, false: if not
   */
  @Override
  public Boolean visitKeywordExpression(AggregatorConfigParser.KeywordExpressionContext ctx) {
    return articleString.contains(ctx.KEYWORD().getText().toLowerCase(Locale.ROOT));
  }

  /** visits each selector within the OR expression until one is satisfied.
   *
   * @param ctx an OrExpressionContext
   * @return true: if the article satisfies the expression, false: if not
   */
  @Override
  public Boolean visitOrExpression(AggregatorConfigParser.OrExpressionContext ctx) {
    List<AggregatorConfigParser.SelectorContext> expressions = ctx.selector();
    for (AggregatorConfigParser.SelectorContext selector : expressions) {
      if (Boolean.TRUE.equals(selector.accept(this))) {
        return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;
  }
}
