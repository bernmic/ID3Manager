package de.b4.jfx.handler;

import de.b4.jfx.Main;
import javafx.beans.value.ObservableValue;

public abstract class SelectedHandler extends Handler {
  SelectedHandler() {
    Main.somethingSelected.addListener(this::selectionChanged);
    if (menuItem != null)
      menuItem.setDisable(true);
    if (toolbarButton != null)
      toolbarButton.setDisable(true);
  }

  void selectionChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    if (menuItem != null)
      menuItem.setDisable(!newValue);
    if (toolbarButton != null)
      toolbarButton.setDisable(!newValue);
  }
}
