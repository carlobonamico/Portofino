<%
    // Avoid caching of dynamic pages
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0);
%><%@ page contentType="text/html;charset=ISO-8859-1" language="java"
           pageEncoding="ISO-8859-1"
%><%@ taglib prefix="s" uri="/struts-tags"
%><%@ taglib prefix="mdes" uri="/manydesigns-elements-struts2"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1"/>
    <meta http-equiv="Content-Script-Type" content="text/javascript"/>
    <meta http-equiv="Content-Style-Type" content="text/css"/>
    <link rel="stylesheet" type="text/css"
          href="<s:url value="/yui-2.8.1/build/reset-fonts-grids/reset-fonts-grids.css"/>"/>
    <link rel="stylesheet" type="text/css"
          href="<s:url value="/yui-2.8.1/build/base/base-min.css"/>"/>
    <link rel="stylesheet" type="text/css"
          href="<s:url value="/skins/default/portofino.css"/>"/>
    <script type="text/javascript"
            src="<s:url value="/yui-2.8.1/build/yuiloader-dom-event/yuiloader-dom-event.js"/>"></script>
    <script type="text/javascript"
            src="<s:url value="/skins/default/portofino.js"/>"></script>
    <title><s:property value="#request.navigation.selectedNavigationNode.description"/></title>
</head>
<body>
<div id="doc3" class="yui-t2">
    <s:url var="indexUrl" namespace="/" action="Index"/>
    <s:url var="profileUrl" namespace="/user" action="Profile"/>
    <s:url var="settingsUrl" namespace="/user" action="Settings"/>
    <s:url var="helpUrl" namespace="/user" action="Help"/>
    <s:url var="loginUrl" namespace="/user" action="Login"/>
    <s:url var="logoutUrl" namespace="/" action="Homepage"/>

    <div id="hd">
        <div id="globalLinks">
            Welcome, <s:a href="%{#profileUrl}">User Name</s:a> -
            <s:a href="%{#settingsUrl}">Settings</s:a> -
            <s:a href="%{#helpUrl}">Help</s:a> -
            <s:a href="%{#loginUrl}">Log in</s:a> - 
            <s:a href="%{#logoutUrl}">Log out</s:a>
        </div>
        <div style="position: absolute; left: 20em;">
            <mdes:sessionMessages/>
        </div>
        <h1><s:a href="%{#indexUrl}"><s:property value="#application.portofinoProperties['application.name']"/></s:a></h1>
    </div>
    <div id="bd">
        <div id="yui-main">
            <div id="content" class="yui-b">