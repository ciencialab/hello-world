package org.ciencialabart.breadwinner.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.ciencialabart.breadwinner.Dinner;
import org.ciencialabart.breadwinner.util.DateDropTimeConverter;

class DinnersTab extends HorizontalLayout {
    
    private final Button newDinnerButton = new Button("New dinner");
    private final Grid dinnersListGrid = new Grid();
    private final DinnerForm dinnerForm = new DinnerForm();
    
    void configureComponents() {
        dinnerForm.configureComponents();
        
        newDinnerButton.addClickListener(e -> dinnerForm.newDinner(new Dinner()));

        dinnersListGrid.setContainerDataSource(new BeanItemContainer<>(Dinner.class));
        dinnersListGrid.getColumn("dinnerDate").setConverter(new DateDropTimeConverter());
        dinnersListGrid.setColumnOrder("dinnerDate", "soup", "meat", "starch", "vegetables");
        dinnersListGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        dinnersListGrid.addSelectionListener(event -> dinnerForm
                .modify((Dinner) dinnersListGrid.getSelectedRow()));
    }
    
    void buildLayout() {
        dinnerForm.buildLayout();
        
        VerticalLayout leftDinnersTabLayout = new VerticalLayout(
            newDinnerButton, 
            dinnersListGrid);
        dinnersListGrid.setSizeFull();
        leftDinnersTabLayout.setSizeFull();
        leftDinnersTabLayout.setExpandRatio(dinnersListGrid, 1);
        
        addComponents(leftDinnersTabLayout, dinnerForm);
        setSizeFull();
        setExpandRatio(leftDinnersTabLayout, 1);
    }
    
    Grid getDinnersGrid() {
        return dinnersListGrid;
    }
    
    DinnerForm getDinnerForm() {
        return dinnerForm;
    }

    @Override
    public BreadwinnerUI getUI() {
        return (BreadwinnerUI) super.getUI();
    }
    
}
