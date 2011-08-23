<%@ page contentType="text/html;charset=ISO-8859-1" language="java"
         pageEncoding="ISO-8859-1"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"
%><%@taglib prefix="mde" uri="/manydesigns-elements"
%><stripes:layout-render name="/skins/${skin}/modal-page.jsp">
    <stripes:layout-component name="customScripts">
        <script type="text/javascript" src="<stripes:url value="/ckeditor/ckeditor.js"/>"></script>
        <script type="text/javascript" src="<stripes:url value="/ckeditor/adapters/jquery.js"/>"></script>
    </stripes:layout-component>
    <jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.actions.TextAction"/>
    <stripes:layout-component name="contentHeader">
        <stripes:submit name="updateConfiguration" value="Save" class="contentButton"/>
        <stripes:submit name="cancel" value="Cancel" class="contentButton"/>
        <div class="breadcrumbs">
            <div class="inner">
                <mde:write name="breadcrumbs"/>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="portletHeader">
        <%@include file="../portlet-common-configuration.jsp" %>
    </stripes:layout-component>
    <stripes:layout-component name="portletBody">
        <stripes:textarea class="editor" name="content" value="${actionBean.content}"/>
        <div class="horizontalSeparator"></div>
        This document is saved in the storage directory as: <c:out value="${actionBean.textFile.name}"/>
        <input type="hidden" name="cancelReturnUrl" value="<c:out value="${actionBean.cancelReturnUrl}"/>"/>
    </stripes:layout-component>
    <stripes:layout-component name="portletFooter">
        <script type="text/javascript">
            $('textarea.editor').ckeditor({
                toolbar: 'Full',
                toolbarCanCollapse: false,
                filebrowserWindowWidth : '640',
                filebrowserWindowHeight : '480',
                filebrowserBrowseUrl : '<c:out value="${dispatch.absoluteOriginalPath}"/>?browse=',
                filebrowserUploadUrl : '<c:out value="${dispatch.absoluteOriginalPath}"/>?uploadAttachmentFromCKEditor='
            });
        </script>
    </stripes:layout-component>
    <stripes:layout-component name="contentFooter">
        <stripes:submit name="updateConfiguration" value="Save" class="contentButton"/>
        <stripes:submit name="cancel" value="Cancel" class="contentButton"/>
    </stripes:layout-component>
</stripes:layout-render>