package org.ciencialabart.meower.util;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

public class JSFComponentFinder {
    
    public static UIComponent findComponent(final String idToFind) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        UIViewRoot viewRoot = context.getViewRoot();
        ComponentVisitor componentsTreeVisitor = new ComponentVisitor(idToFind);

        viewRoot.visitTree(VisitContext.createVisitContext(context), componentsTreeVisitor);

        return componentsTreeVisitor.getFoundComponent();
    }
    
    private static class ComponentVisitor implements VisitCallback {
        private String idToFind;
        private UIComponent foundComponent;

        private ComponentVisitor(String idToFind) {
            this.idToFind = idToFind;
        }

        @Override
        public VisitResult visit(VisitContext context, UIComponent targetComponent) {
            if (targetComponent.getId().equals(idToFind)){
                foundComponent = targetComponent;
                return VisitResult.COMPLETE;
            }
            return VisitResult.ACCEPT;              
        }
        
        public UIComponent getFoundComponent() {
            return foundComponent;
        }
        
    }
}
