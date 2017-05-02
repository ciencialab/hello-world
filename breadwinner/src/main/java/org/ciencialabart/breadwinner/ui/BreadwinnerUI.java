package org.ciencialabart.breadwinner.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import javax.servlet.annotation.WebServlet;
import org.ciencialabart.breadwinner.Dinner;
import org.ciencialabart.breadwinner.DinnerComponent;
import org.ciencialabart.breadwinner.DinnerService;

@Title("Breadwinner")
@Theme("valo")
public class BreadwinnerUI extends UI {

    private final TabSheet tabSheet = new TabSheet();
    private final DinnersTab dinnersTab = new DinnersTab();
    private final DinnerComponentsTab dinnerComponentsTab = new DinnerComponentsTab();

    private final DinnerService service = DinnerService.createService();
    
    private Container dinnersDataContainer;
    private Container dinnersComponentsDataContainer;
    private Container soupsDataContainer;
    private Container meatsDataContainer;
    private Container starchesDataContainer;
    private Container vegetablesDataContainer;

    @Override
    protected void init(VaadinRequest request) {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        dinnersTab.configureComponents();
        dinnerComponentsTab.configureComponents();
        
        refreshDinnersData();
        refreshDinnersComponentsData();
    }

    private void buildLayout() {
        dinnersTab.buildLayout();
        dinnerComponentsTab.buildLayout();
        
        tabSheet.addTab(dinnersTab, "Dinners");
        tabSheet.addTab(dinnerComponentsTab, "Dinner Components");
        tabSheet.setSizeFull();
        
        HorizontalLayout mainLayout = new HorizontalLayout(tabSheet);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(tabSheet, 1);
        
        setContent(mainLayout);
    }

    void refreshDinnersData() {
        dinnersDataContainer = new BeanItemContainer<>(Dinner.class, service.getDinners());
        getDinnersGrid().setContainerDataSource((Container.Indexed) dinnersDataContainer);
        dinnersTab.getDinnerForm().setVisible(false);
    }

    void refreshDinnersComponentsData() {
        DinnerForm dinnerForm = dinnersTab.getDinnerForm();
        
        soupsDataContainer = new BeanItemContainer<>(DinnerComponent.class, service
            .getDinnersComponents(DinnerComponent.DinnerComponentKind.SOUP.name()));
        dinnerForm.getSoupComboBox().setContainerDataSource((Container.Indexed) soupsDataContainer);
        
        meatsDataContainer = new BeanItemContainer<>(DinnerComponent.class, service
            .getDinnersComponents(DinnerComponent.DinnerComponentKind.MEAT.name()));
        dinnerForm.getMeatComboBox().setContainerDataSource((Container.Indexed) meatsDataContainer);
        
        starchesDataContainer = new BeanItemContainer<>(DinnerComponent.class, service
            .getDinnersComponents(DinnerComponent.DinnerComponentKind.STARCH.name()));
        dinnerForm.getStarchComboBox().setContainerDataSource((Container.Indexed) starchesDataContainer);
        
        vegetablesDataContainer = new BeanItemContainer<>(DinnerComponent.class, service
            .getDinnersComponents(DinnerComponent.DinnerComponentKind.VEGETABLES.name()));
        dinnerForm.getVegetablesComboBox().setContainerDataSource((Container.Indexed) vegetablesDataContainer);
        
        dinnersComponentsDataContainer = new BeanItemContainer<>(DinnerComponent.class, service
            .getDinnersComponents(dinnerComponentsTab.getSelectedType()));
        getDinnerComponentsGrid().setContainerDataSource((Container.Indexed) dinnersComponentsDataContainer);
        dinnerComponentsTab.getDinnerComponentForm().setVisible(false);
    }
    
    Grid getDinnersGrid() {
        return dinnersTab.getDinnersGrid();
    }
    
    Grid getDinnerComponentsGrid() {
        return dinnerComponentsTab.getDinnerComponentsGrid();
    }
    
    DinnerService getService() {
        return service;
    }
    
    @WebServlet(urlPatterns = "/*")
    @VaadinServletConfiguration(ui = BreadwinnerUI.class, productionMode = false)
    public static class BreadwinnerUIServlet extends VaadinServlet {
    }

}
