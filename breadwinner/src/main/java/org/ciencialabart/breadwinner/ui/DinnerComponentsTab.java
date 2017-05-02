package org.ciencialabart.breadwinner.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;
import java.util.Arrays;
import org.ciencialabart.breadwinner.DinnerComponent;

public class DinnerComponentsTab extends HorizontalLayout {
    
    private final ListSelect dinnerComponentTypesListSelect = new ListSelect("Dinner Component Types");
    
    private final Button newDinnerComponentButton = new Button("Add new Dinner Component");
    private final Grid dinnerComponentsGrid = new Grid();
    
    private final DinnerComponentForm dinnerComponentForm = new DinnerComponentForm();
    
    void configureComponents() {
        dinnerComponentForm.configureComponents();
        
        newDinnerComponentButton.addClickListener(e -> {
            dinnerComponentForm.newDinnerComponent(new DinnerComponent());
            dinnerComponentForm.setDinnerComponentType(getSelectedType());});
        dinnerComponentsGrid.setContainerDataSource(new BeanItemContainer<>(DinnerComponent.class));
        dinnerComponentsGrid.removeColumn("id");
        dinnerComponentsGrid.removeColumn("kind");
        dinnerComponentsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        dinnerComponentsGrid.addSelectionListener(event -> dinnerComponentForm
            .modify((DinnerComponent) dinnerComponentsGrid.getSelectedRow()));
        
        dinnerComponentTypesListSelect.addItems(Arrays
            .asList(DinnerComponent.DinnerComponentKind.values()).subList(0, 4));
        dinnerComponentTypesListSelect.setRows(4);
        dinnerComponentTypesListSelect.setNullSelectionAllowed(false);
        dinnerComponentTypesListSelect.select(DinnerComponent.DinnerComponentKind.SOUP);
        dinnerComponentTypesListSelect.addValueChangeListener(event -> {
            getUI().refreshDinnersComponentsData();
            dinnerComponentForm.setDinnerComponentType(getSelectedType());
            dinnerComponentForm.setVisible(false);});
    }
    
    void buildLayout() {
        dinnerComponentForm.buildLayout();
        VerticalLayout dinnerComponentsLayout = new VerticalLayout(
            newDinnerComponentButton, 
            dinnerComponentsGrid);
        dinnerComponentsGrid.setSizeFull();
        dinnerComponentsLayout.setSizeFull();
        dinnerComponentsLayout.setExpandRatio(dinnerComponentsGrid, 1);
        
        addComponents(dinnerComponentTypesListSelect, dinnerComponentsLayout, dinnerComponentForm);
        dinnerComponentTypesListSelect.setWidth(15, Sizeable.Unit.EM);
        setSizeFull();
        setExpandRatio(dinnerComponentsLayout, 1);
    }
    
    Grid getDinnerComponentsGrid() {
        return dinnerComponentsGrid;
    }
    
    DinnerComponentForm getDinnerComponentForm() {
        return dinnerComponentForm;
    }
    
    String getSelectedType() {
        return dinnerComponentTypesListSelect.getValue().toString();
    }

    @Override
    public BreadwinnerUI getUI() {
        return (BreadwinnerUI) super.getUI();
    }
    
}
