package springing.struts2.views.jsp;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.IterationTag;
import jakarta.servlet.jsp.tagext.Tag;
import jakarta.servlet.jsp.tagext.TryCatchFinally;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.tags.*;

/**
 * Used to get the property of a `value`, which will default to the top of
 * the stack if none is specified.
 * - default (String)
 *     The default value to be used if `value` attribute is null
 * - escapeCsv (Boolean)
 *     Escape CSV. Defaults to `false`
 * - escapeHtml (Boolean)
 *     Escape HTML. Defaults to `true`
 * - escapeJavaScript (Boolean)
 *     Escape JavaScript. Defaults to `false`
 * - escapeXml (Boolean)
 *     Escape XML. Defaults to `false`
 * - value (Object)
 *     value to be displayed
 *
 * <pre>
 * <s:push value="myBean">
 *     <%-- Example 1 prints the result of myBean's getMyBeanProperty() method. --%>
 *     <s:property value="myBeanProperty" />
 *     <%-- Example 2 prints the result of myBean's getMyBeanProperty() method and if it is null, print 'a default value' instead. --%>
 *     <s:property value="myBeanProperty" default="a default value" />
 * </s:push>
 * </pre>
 *
 * <pre>
 * <%-- i18nExample --%>
 * <s:property value="getText('some.key')" />
 * </pre>
 */
public class PropertyTag implements IterationTag, TryCatchFinally {

  public PropertyTag() {
    this.evalTag = new EvalTag();
  }

  private final EvalTag evalTag;

  private String defaultValue = "";

  public void setDefault(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  private @Nullable String value;

  public void setValue(String value) {
    this.value = value;
  }

  public void setEscapeHtml(boolean escapeHtml) throws JspException {
    evalTag.setHtmlEscape(escapeHtml);
  }

  public void setEscapeJavaScript(boolean escapeJavaScript) throws JspException {
    evalTag.setJavaScriptEscape(escapeJavaScript);
  }

  public void setEscapeXml(boolean escapeXml) {
    throw new UnsupportedOperationException();
  }

  public void setEscapeCsv(boolean escapeCsv) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setPageContext(PageContext pageContext) {
    evalTag.setPageContext(pageContext);
  }

  @Override
  public void setParent(Tag tag) {
    evalTag.setParent(tag);
  }

  @Override
  public Tag getParent() {
    return evalTag.getParent();
  }

  @Override
  public int doStartTag() throws JspException {
    return evalTag.doStartTag();
  }

  @Override
  public int doEndTag() throws JspException {
    var expression = "action." + (value == null ? defaultValue : value);
    evalTag.setExpression(expression);
    return evalTag.doEndTag();
  }

  @Override
  public void release() {
    evalTag.release();
  }

  @Override
  public void doCatch(Throwable throwable) throws Throwable {
    evalTag.doCatch(throwable);
  }

  @Override
  public void doFinally() {
    evalTag.doFinally();
  }

  @Override
  public int doAfterBody() throws JspException {
    return evalTag.doAfterBody();
  }
}
