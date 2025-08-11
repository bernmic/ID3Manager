package de.b4.jfx.handler;

import de.b4.jfx.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public abstract class Handler {
    final MenuItem menuItem;
    final Button toolbarButton;
    final FontIcon icon;

    Handler() {
        this.icon = getFontIcon();
        menuItem = createMenuItem();
        if (hasToolbarEntry()) {
            toolbarButton = new Button();
            toolbarButton.setGraphic(this.icon);
            toolbarButton.setOnAction(getEventHandler());
            toolbarButton.setTooltip(new Tooltip(getTooltipText()));
            toolbarButton.setPrefSize(40, 40);
            toolbarButton.setMinSize(40, 40);
            toolbarButton.setMaxSize(40, 40);
            if (icon != null)
                icon.setStyle("-fx-icon-size: 32px;");
        } else {
            toolbarButton = null;
        }
    }

    public MenuItem createMenuItem() {
        MenuItem mi = new MenuItem(getMenuItemText());
        if (this.icon != null)
            mi.setGraphic(this.icon);
        mi.setOnAction(getEventHandler());
        KeyCombination acc = getKeyCodeCombination();
        if (acc != null)
            mi.setAccelerator(getKeyCodeCombination());

        return mi;
    }

    abstract FontIcon getFontIcon();
    abstract String getMenuItemText();
    abstract String getTooltipText();
    abstract EventHandler<ActionEvent> getEventHandler();
    abstract KeyCodeCombination getKeyCodeCombination();
    boolean hasToolbarEntry() {
        return false;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public Button getToolbarButton() {
        return toolbarButton;
    }

    String getIconCode(String code) {
        return Main.theApp.getConfiguration().getIconCode(code);
    }

    public void fireEvent() {
        if (menuItem != null) {
            menuItem.fire();
        } else if (toolbarButton != null) {
            toolbarButton.fire();
        }
    }
}
