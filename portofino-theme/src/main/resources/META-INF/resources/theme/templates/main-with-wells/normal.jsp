<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"
%><%@ page import="com.manydesigns.portofino.pageactions.PageActionLogic"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes-dynattr.tld"
%><%@ taglib tagdir="/WEB-INF/tags" prefix="portofino"
%><%@ taglib prefix="mde" uri="/manydesigns-elements"
%><jsp:useBean id="actionBean" scope="request"
               type="com.manydesigns.portofino.pageactions.AbstractPageAction" /><%--
--%><stripes:layout-definition><%--
--%><c:set var="embedded" value="<%= PageActionLogic.isEmbedded(actionBean) %>" scope="page" /><%--
--%><c:if test="${embedded}"><%--
--%><div class="pageHeader">
        <stripes:layout-component name="pageHeader">
            <div class="pull-right">
                <stripes:form action="${actionBean.context.actionPath}" method="post">
                    <input type="hidden" name="returnUrl"
                           value="<c:out value="${actionBean.returnUrl}"/>"/>
                    <portofino:buttons list="pageHeaderButtons" cssClass="btn-xs" />
                </stripes:form>
            </div>
            <h4 class="pageTitle">
                <stripes:layout-component name="pageTitle">
                    <c:out value="${actionBean.pageInstance.description}"/>
                </stripes:layout-component>
            </h4>
        </stripes:layout-component>
    </div>
    <div class="pageBody">
        <stripes:layout-component name="pageBody" />
    </div>
    <div class="pageFooter">
        <stripes:layout-component name="pageFooter" />
    </div><%--
--%></c:if><%--
--%><c:if test="${not embedded}"><%--
--%><!DOCTYPE html>
<html lang="<%= request.getLocale() %>">
    <jsp:include page="/theme/head.jsp">
        <jsp:param name="pageTitle" value="${pageTitle}" />
    </jsp:include>
    <body>
    <jsp:include page="/theme/header.jsp">
        <jsp:param name="pageTitle" value="${pageTitle}" />
    </jsp:include>
    <div class="container">
        <div class="row">
            <div class="content col-md-12">
                <div class="contentHeader">
                    <stripes:layout-component name="contentHeader">
                        <mde:sessionMessages />
                        <jsp:include page="/theme/breadcrumbs.jsp" />
                    </stripes:layout-component>
                </div>
                <div class="pageHeader">
                    <stripes:layout-component name="pageHeader">
                        <div class="pull-right">
                            <stripes:form action="${actionBean.context.actionPath}"
                                          method="post">
                                <input type="hidden" name="returnUrl"
                                       value="<c:out value="${actionBean.returnUrl}"/>"/>
                                <portofino:buttons list="pageHeaderButtons" cssClass="btn-xs" />
                            </stripes:form>
                        </div>
                        <h3 class="pageTitle">
                            <stripes:layout-component name="pageTitle">
                                <c:out value="${actionBean.pageInstance.description}"/>
                            </stripes:layout-component>
                        </h3>
                    </stripes:layout-component>
                </div>
                <div class="row">
                    <div class="col-md-8">
                        <div class="pageBody">
                            <stripes:layout-component name="pageBody" />
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="embeddedPages">
                            <div class="${cssClass} embeddedPageActions" data-page-action-list="default">
                                <% actionBean.initEmbeddedPageActions(); %>
                                <c:forEach var="embeddedPageAction" items="${ actionBean.embeddedPageActions['default'] }">
                                    <portofino:embedded-page-action id="${embeddedPageAction.id}"
                                                                    path="${embeddedPageAction.path}"
                                                                    returnUrl="${actionBean.returnUrl}#${embeddedPageAction.id}"
                                                                    cssClass="well"
                                            />
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="pageFooter">
                    <stripes:layout-component name="pageFooter" />
                </div>
                <div class="contentFooter">
                    <stripes:layout-component name="contentFooter" />
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="/theme/footer.jsp">
        <jsp:param name="pageTitle" value="${pageTitle}" />
    </jsp:include>
    </body>
</html>
</c:if>
</stripes:layout-definition>