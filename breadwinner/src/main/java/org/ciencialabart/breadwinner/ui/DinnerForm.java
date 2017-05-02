package org.ciencialabart.breadwinner.ui;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;
import org.ciencialabart.breadwinner.Dinner;
import org.ciencialabart.breadwinner.DinnerComponent;

public class DinnerForm extends FormLayout {

    private final Button saveButton = new Button("Save", this::save);
    private final Button cloneDinnerButton = new Button("Clone", this::cloneDinner);
    
    private final DateField dinnerDate = new DateField("Dinner Date");
    private final ComboBox soup = new ComboBox("Soup");
    private final ComboBox meat = new ComboBox("Meat");
    private final ComboBox starch = new ComboBox("Starch");
    private final ComboBox vegetables = new ComboBox("Vegetables");

    private final Button removeButton = new Button("Remove", this::remove);
    private final Button cancelButton = new Button("Cancel", this::cancel);
    
    private Dinner dinner;

    private BeanFieldGroup<Dinner> formFieldBindings;

    void configureComponents() {
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        cloneDinnerButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        removeButton.setStyleName(ValoTheme.BUTTON_DANGER);
        
        dinnerDate.setResolution(Resolution.DAY);
        soup.setContainerDataSource(new BeanItemContainer<>(DinnerComponent.class));
        meat.setContainerDataSource(new BeanItemContainer<>(DinnerComponent.class));
        starch.setContainerDataSource(new BeanItemContainer<>(DinnerComponent.class));
        vegetables.setContainerDataSource(new BeanItemContainer<>(DinnerComponent.class));
    }

    void buildLayout() {
        setSizeUndefined();
        setMargin(true);

        HorizontalLayout topActions = new HorizontalLayout(saveButton, cloneDinnerButton);
        topActions.setSpacing(true);

        HorizontalLayout bottomActions = new HorizontalLayout(removeButton, cancelButton);
        bottomActions.setSpacing(true);

	addComponents(
            topActions, 
            dinnerDate, 
            soup, 
            meat, 
            starch, 
            vegetables, 
            bottomActions);
        setVisible(false);
    }

    public void save(Button.ClickEvent event) {
        if (dinnerDate.isEmpty()) {
            Notification.show("Dinner Date is missing", Notification.Type.TRAY_NOTIFICATION);
            return;
        }
        
        try {
            formFieldBindings.commit();
            getUI().getService().save(dinner);

            Notification.show("Dinner saved", Notification.Type.TRAY_NOTIFICATION);
            getUI().refreshDinnersData();
        } catch (FieldGroup.CommitException e) {
            Notification.show("Dinner not valid", Notification.Type.TRAY_NOTIFICATION);
        }
    }

    public void cloneDinner(Button.ClickEvent event) {
        dinnerDate.setEnabled(true);
        dinnerDate.focus();
    }
    
    public void remove(Button.ClickEvent event) {
        try {
            formFieldBindings.commit();
            getUI().getService().remove(dinner);

            Notification.show("Dinner removed", Notification.Type.TRAY_NOTIFICATION);
            getUI().refreshDinnersData();
        } catch (FieldGroup.CommitException e) {
            Notification.show("Dinner not valid", Notification.Type.TRAY_NOTIFICATION);
        }
    }
    
    public void cancel(Button.ClickEvent event) {
        Notification.show("Cancelled", Notification.Type.TRAY_NOTIFICATION);
        getUI().getDinnersGrid().select(null);
        setVisible(false);
    }
    
    void newDinner(Dinner dinner) {
        this.dinner = dinner;
        if(dinner != null) {
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(dinner, this);
            dinnerDate.focus();
        }
        setVisible(dinner != null);
    }
    
    void modify(Dinner dinner) {
        this.dinner = dinner;
        if(dinner != null) {
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(dinner, this);
            dinnerDate.setEnabled(false);
            soup.focus();
        }
        setVisible(dinner != null);
    }
    
    ComboBox getSoupComboBox() {
        return soup;
    }
    
    ComboBox getMeatComboBox() {
        return meat;
    }
    
    ComboBox getStarchComboBox() {
        return starch;
    }
    
    ComboBox getVegetablesComboBox() {
        return vegetables;
    }

    @Override
    public BreadwinnerUI getUI() {
        return (BreadwinnerUI) super.getUI();
    }

}
