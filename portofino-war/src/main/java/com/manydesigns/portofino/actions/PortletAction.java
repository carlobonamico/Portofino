package com.manydesigns.portofino.actions;

import com.manydesigns.elements.reflection.ClassAccessor;
import com.manydesigns.portofino.annotations.InjectApplication;
import com.manydesigns.portofino.annotations.InjectDispatch;
import com.manydesigns.portofino.annotations.InjectHttpRequest;
import com.manydesigns.portofino.context.Application;
import com.manydesigns.portofino.dispatcher.CrudNodeInstance;
import com.manydesigns.portofino.dispatcher.Dispatch;
import com.manydesigns.portofino.dispatcher.SiteNodeInstance;
import com.manydesigns.portofino.model.site.CrudNode;
import com.manydesigns.portofino.model.site.EmbeddableNode;
import com.manydesigns.portofino.model.site.SiteNode;
import com.manydesigns.portofino.navigation.ResultSetNavigation;
import com.manydesigns.portofino.util.ShortNameUtils;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.StripesConstants;
import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class PortletAction extends AbstractActionBean {

    public final MultiMap portlets = new MultiHashMap();
    @InjectDispatch
    public Dispatch dispatch;
    public String returnToParentTarget;
    @InjectApplication
    public Application application;

    @InjectHttpRequest
    public HttpServletRequest request;

    //--------------------------------------------------------------------------
    // Navigation
    //--------------------------------------------------------------------------

    protected ResultSetNavigation resultSetNavigation;

    public boolean isEmbedded() {
        return getContext().getRequest().getAttribute(
                StripesConstants.REQ_ATTR_INCLUDE_PATH) != null;
    }

    public void setupReturnToParentTarget() {
        SiteNodeInstance[] siteNodeInstancePath =
                dispatch.getSiteNodeInstancePath();
        SiteNodeInstance thisNodeInstance = dispatch.getLastSiteNodeInstance();
        boolean hasPrevious = siteNodeInstancePath.length > 1;
        returnToParentTarget = null;
        if (hasPrevious) {
            int previousPos = siteNodeInstancePath.length - 2;
            SiteNodeInstance previousNode = siteNodeInstancePath[previousPos];
            if (previousNode instanceof CrudNodeInstance) {
                CrudNodeInstance crudNodeInstance =
                        (CrudNodeInstance) previousNode;
                if (CrudNode.MODE_SEARCH.equals(crudNodeInstance.getMode())) {
                    returnToParentTarget = crudNodeInstance.getCrud().getName();
                } else if (CrudNode.MODE_DETAIL.equals(crudNodeInstance.getMode())) {
                    Object previousNodeObject = crudNodeInstance.getObject();
                    ClassAccessor previousNodeClassAccessor =
                            crudNodeInstance.getClassAccessor();
                    returnToParentTarget = ShortNameUtils.getName(
                            previousNodeClassAccessor, previousNodeObject);
                }
            }
        } else {
            if (thisNodeInstance instanceof CrudNodeInstance) {
                CrudNodeInstance crudNodeInstance =
                        (CrudNodeInstance) thisNodeInstance;
                if (CrudNode.MODE_DETAIL.equals(crudNodeInstance.getMode())) {
                    returnToParentTarget = "search";
                }
            }
        }
    }

    //--------------------------------------------------------------------------
    // Admin methods
    //--------------------------------------------------------------------------

    public Resolution updateLayout() {
        synchronized (application) {
            Enumeration parameters = request.getParameterNames();
            while(parameters.hasMoreElements()) {
                String parameter = (String) parameters.nextElement();
                if(parameter.startsWith("portletWrapper_")) {
                    String layoutContainer = parameter.substring("portletWrapper_".length());
                    String[] portletWrapperIds = request.getParameterValues(parameter);
                    updateLayout(layoutContainer, portletWrapperIds);
                }
            }
            application.getModel().init();
            application.saveXmlModel();
        }
        return new RedirectResolution(dispatch.getOriginalPath());
    }

    public Resolution reloadModel() {
        application.reloadXmlModel();
        return new RedirectResolution(dispatch.getOriginalPath());
    }

    protected void updateLayout(String layoutContainer, String[] portletWrapperIds) {
        SiteNodeInstance myself = dispatch.getLastSiteNodeInstance();
        for(int i = 0; i < portletWrapperIds.length; i++) {
            String current = portletWrapperIds[i];
            if("p".equals(current)) {
                myself.setLayoutContainer(layoutContainer);
                myself.setLayoutOrder(i);
            } else {
                String nodeId = current.substring(1); //current = c...
                SiteNodeInstance childNodeInstance = myself.findChildNode(nodeId);
                SiteNode childNode = childNodeInstance.getSiteNode();
                childNode.setLayoutContainerInParent(layoutContainer);
                childNode.setLayoutOrderInParent(i + "");
            }
        }
    }

    //--------------------------------------------------------------------------
    // Getters/Setters
    //--------------------------------------------------------------------------

    public Dispatch getDispatch() {
        return dispatch;
    }

    public String getReturnToParentTarget() {
        return returnToParentTarget;
    }

    public MultiMap getPortlets() {
        return portlets;
    }

    public boolean isMultipartRequest() {
        return false;
    }

    public ResultSetNavigation getResultSetNavigation() {
        return resultSetNavigation;
    }

    public void setResultSetNavigation(ResultSetNavigation resultSetNavigation) {
        this.resultSetNavigation = resultSetNavigation;
    }

    protected void setupPortlets(SiteNodeInstance siteNodeInstance, String myself) {
        PortletInstance myPortletInstance = new PortletInstance("p", siteNodeInstance.getLayoutOrder(), myself);
        portlets.put(siteNodeInstance.getLayoutContainer(), myPortletInstance);
        for(SiteNode node : siteNodeInstance.getChildNodes()) {
            if(node instanceof EmbeddableNode && node.getLayoutContainerInParent() != null) {
                PortletInstance portletInstance =
                        new PortletInstance(
                                "c" + node.getId(),
                                node.getActualLayoutOrderInParent(),
                                dispatch.getOriginalPath() + "/" + node.getId());
                portlets.put(node.getLayoutContainerInParent(), portletInstance);
            }
        }
        for(Object entryObj : portlets.entrySet()) {
            Map.Entry entry = (Map.Entry) entryObj;
            List portletContainer = (List) entry.getValue();
            Collections.sort(portletContainer);
        }
    }

    protected Resolution forwardToPortletPage(String nodeJsp, SiteNodeInstance siteNodeInstance) {
        setupPortlets(siteNodeInstance, nodeJsp);
        return new ForwardResolution("/layouts/portlet-page.jsp");
    }
}