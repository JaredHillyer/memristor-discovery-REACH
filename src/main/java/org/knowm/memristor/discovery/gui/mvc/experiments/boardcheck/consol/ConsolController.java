/**
 * Memristor-Discovery is distributed under the GNU General Public License version 3
 * and is also available under alternative licenses negotiated directly
 * with Knowm, Inc.
 *
 * Copyright (c) 2016-2019 Knowm Inc. www.knowm.org
 *
 * This package also includes various components that are not part of
 * Memristor-Discovery itself:
 *
 * * `Multibit`: Copyright 2011 multibit.org, MIT License
 * * `SteelCheckBox`: Copyright 2012 Gerrit, BSD license
 *
 * Knowm, Inc. holds copyright
 * and/or sufficient licenses to all components of the Memristor-Discovery
 * package, and therefore can grant, at its sole discretion, the ability
 * for companies, individuals, or organizations to create proprietary or
 * open source (even if not GPL) modules which may be dynamically linked at
 * runtime with the portions of Memristor-Discovery which fall under our
 * copyright/license umbrella, or are distributed under more flexible
 * licenses than GPL.
 *
 * The 'Knowm' name and logos are trademarks owned by Knowm, Inc.
 *
 * If you have any questions regarding our licensing policy, please
 * contact us at `contact@knowm.org`.
 */
package org.knowm.memristor.discovery.gui.mvc.experiments.boardcheck.consol;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.knowm.memristor.discovery.gui.mvc.experiments.ExperimentControlModel;

public class ConsolController implements PropertyChangeListener {

  private final ConsolPanel consolPanel;
  private final ConsolControlModel consolModel;

  /**
   * Constructor
   *
   * @param plotPanel
   * @param plotModel
   */
  public ConsolController(ConsolPanel consolPanel, ConsolControlModel consolModel) {

    this.consolPanel = consolPanel;
    this.consolModel = consolModel;

    initGUIComponents();
    setUpViewEvents();
  }

  public void initGUIComponents() {

    initGUIComponentsFromModel();
  }

  private void initGUIComponentsFromModel() {

  }

  private void setUpViewEvents() {

  }

  /**
   * These property change events are triggered in the model in the case where the underlying model
   * is updated. Here, the controller can respond to those events and make sure the corresponding GUI
   * components get updated.
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {

    switch (evt.getPropertyName()) {

      case ExperimentControlModel.EVENT_PREFERENCES_UPDATE:

        initGUIComponentsFromModel();
        break;

      default:
        break;
    }
  }
}
