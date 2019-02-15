/**
 * Memristor-Discovery is distributed under the GNU General Public License version 3 and is also
 * available under alternative licenses negotiated directly with Knowm, Inc.
 *
 * <p>Copyright (c) 2016-2019 Knowm Inc. www.knowm.org
 *
 * <p>This package also includes various components that are not part of Memristor-Discovery itself:
 *
 * <p>* `Multibit`: Copyright 2011 multibit.org, MIT License * `SteelCheckBox`: Copyright 2012
 * Gerrit, BSD license
 *
 * <p>Knowm, Inc. holds copyright and/or sufficient licenses to all components of the
 * Memristor-Discovery package, and therefore can grant, at its sole discretion, the ability for
 * companies, individuals, or organizations to create proprietary or open source (even if not GPL)
 * modules which may be dynamically linked at runtime with the portions of Memristor-Discovery which
 * fall under our copyright/license umbrella, or are distributed under more flexible licenses than
 * GPL.
 *
 * <p>The 'Knowm' name and logos are trademarks owned by Knowm, Inc.
 *
 * <p>If you have any questions regarding our licensing policy, please contact us at
 * `contact@knowm.org`.
 */
package org.knowm.memristor.discovery.utils.driver;

/** @author timmolter */
public class Pulse extends Driver {

  private final double dutyCycle;

  /**
   * Constructor
   *
   * @param name
   * @param dcOffset
   * @param phase
   * @param amplitude
   * @param frequency
   * @param dutyCycle between 0 and 1
   */
  public Pulse(
      String name,
      double dcOffset,
      double phase,
      double amplitude,
      double frequency,
      double dutyCycle) {

    super(name, dcOffset, phase, amplitude, frequency);
    this.dutyCycle = dutyCycle;
  }

  @Override
  public double getSignal(double time) {

    double T = 1 / frequency;
    double remainderTime = (time + phase) % T * 0.5 / dutyCycle;

    // up phase
    if (0 <= remainderTime && remainderTime * T < .50 / frequency * T) {
      return amplitude + dcOffset;
    }

    // down phase
    else {
      return -1.0 * amplitude + dcOffset;
    }
  }

  public double getDutyCycle() {

    return dutyCycle;
  }

  @Override
  public String toString() {

    return "Pulse [dutyCycle="
        + dutyCycle
        + ", id="
        + id
        + ", dcOffset="
        + dcOffset
        + ", phase="
        + phase
        + ", amplitude="
        + amplitude
        + ", frequency="
        + frequency
        + "]";
  }
}
