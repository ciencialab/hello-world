package org.ciencialabart.breadwinner.ui;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import org.ciencialabart.breadwinner.DinnerComponent;

public class DinnerComponentForm extends FormLayout {

    private final Button saveButton = new Button("Save", this::save);
    
    private final TextField kind = new TextField("Dinner Component Type");
    private final TextField name = new TextField("Dinner Component");

    private final Button removeButton = new Button("Remove", this::remove);
    private final Button cancelButton = new Button("Cancel", this::cancel);
    
    private DinnerComponent dinnerComponent;

    private BeanFieldGroup<DinnerComponent> formFieldBindings;

    void configureComponents() {
        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        removeButton.setStyleName(ValoTheme.BUTTON_DANGER);
        kind.setEnabled(false);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    void buildLayout() {
        setSizeUndefined();
        setMargin(true);

        HorizontalLayout bottomActions = new HorizontalLayout(removeButton, cancelButton);
        bottomActions.setSpacing(true);

	addComponents(
            saveButton, 
            kind, 
            name, 
            bottomActions);
        setVisible(false);
    }

    public void save(Button.ClickEvent event) {
        try {
            formFieldBindings.commit();
            getUI().getService().save(dinnerComponent);

            Notification.show("Dinner Component saved", Notification.Type.TRAY_NOTIFICATION);
            getUI().refreshDinnersComponentsData();
        } catch (FieldGroup.CommitException e) {
            Notification.show("Dinner Component not valid", Notification.Type.TRAY_NOTIFICATION);
        }
    }

    public void remove(Button.ClickEvent event) {
        try {
            formFieldBindings.commit();
            getUI().getService().remove(dinnerComponent);

            Notification.show("Dinner Component removed", Notification.Type.TRAY_NOTIFICATION);
            getUI().refreshDinnersComponentsData();
        } catch (FieldGroup.CommitException e) {
            Notification.show("Dinner Component not valid", Notification.Type.TRAY_NOTIFICATION);
        }
    }

    public void cancel(Button.ClickEvent event) {
        Notification.show("Cancelled", Notification.Type.TRAY_NOTIFICATION);
        getUI().getDinnerComponentsGrid().select(null);
        setVisible(false);
    }

    void newDinnerComponent(DinnerComponent dinnerComponent) {
        this.dinnerComponent = dinnerComponent;
        if(dinnerComponent != null) {
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(dinnerComponent, this);
            name.focus();
        }
        setVisible(dinnerComponent != null);
    }

    void modify(DinnerComponent dinnerComponent) {
        this.dinnerComponent = dinnerComponent;
        if(dinnerComponent != null) {
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(dinnerComponent, this);
            kind.setEnabled(false);
            name.focus();
        }
        setVisible(dinnerComponent != null);
    }
    
    void setDinnerComponentType(String dinnerComponentType) {
        kind.setValue(dinnerComponentType);
        kind.setEnabled(false);
    }
    
    @Override
    public BreadwinnerUI getUI() {
        return (BreadwinnerUI) super.getUI();
    }

}
