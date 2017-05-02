package org.ciencialabart.meower.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class JSFMessageIssuer {
    private static String BUNDLE_NAME = "bundle";

    public static void error(String targetComponentId, String translationKey) {
        issueMessage(targetComponentId, translationKey, FacesMessage.SEVERITY_ERROR);
    }

    private static void issueMessage(String targetComponentId, String translationKey, Severity severity) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, BUNDLE_NAME);
        String message = bundle.getString(translationKey);
        UIComponent targetComponent = JSFComponentFinder.findComponent(targetComponentId);
        
        context.addMessage(targetComponent.getClientId(context), new FacesMessage(severity, message, null));
    }
}
