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
package org.knowm.memristor.discovery.gui.mvc.experiments.synapse.control;

import java.text.DecimalFormat;
import org.knowm.memristor.discovery.gui.mvc.experiments.ExperimentPreferences;
import org.knowm.memristor.discovery.gui.mvc.experiments.Model;
import org.knowm.memristor.discovery.gui.mvc.experiments.synapse.AHaHController_21.Instruction;
import org.knowm.memristor.discovery.gui.mvc.experiments.synapse.SynapsePreferences;
import org.knowm.memristor.discovery.utils.driver.Driver;
import org.knowm.memristor.discovery.utils.driver.HalfSine;
import org.knowm.memristor.discovery.utils.driver.QuarterSine;
import org.knowm.memristor.discovery.utils.driver.Sawtooth;
import org.knowm.memristor.discovery.utils.driver.Square;
import org.knowm.memristor.discovery.utils.driver.SquareSmooth;
import org.knowm.memristor.discovery.utils.driver.Triangle;

public class ControlModel extends Model {

  /** Events */
  public static final String EVENT_INSTRUCTION_UPDATE = "EVENT_INSTRUCTION_UPDATE";

  private final double[] waveformTimeData = new double[SynapsePreferences.CAPTURE_BUFFER_SIZE];
  private final double[] waveformAmplitudeData = new double[SynapsePreferences.CAPTURE_BUFFER_SIZE];
  /** Waveform */
  public SynapsePreferences.Waveform waveform;

  public Instruction instruction = Instruction.FFLV;
  public DecimalFormat ohmFormatter = new DecimalFormat("#,### Ω");
  private float amplitude;
  private int pulseWidth; // model store pulse width in nanoseconds
  private int pulseNumber;
  private double lastY;
  private int sampleRate;

  private boolean isStartToggled = false;

  /** Constructor */
  public ControlModel() {

    updateWaveformChartData();
  }

  @Override
  public void loadModelFromPrefs() {

    seriesResistance =
        experimentPreferences.getInteger(
            SynapsePreferences.SERIES_R_INIT_KEY, SynapsePreferences.SERIES_R_INIT_DEFAULT_VALUE);
    waveform =
        SynapsePreferences.Waveform.valueOf(
            experimentPreferences.getString(
                SynapsePreferences.WAVEFORM_INIT_STRING_KEY,
                SynapsePreferences.WAVEFORM_INIT_STRING_DEFAULT_VALUE));

    amplitude =
        experimentPreferences.getFloat(
            SynapsePreferences.AMPLITUDE_INIT_FLOAT_KEY,
            SynapsePreferences.AMPLITUDE_INIT_FLOAT_DEFAULT_VALUE);
    pulseWidth =
        experimentPreferences.getInteger(
            SynapsePreferences.PULSE_WIDTH_INIT_KEY,
            SynapsePreferences.PULSE_WIDTH_INIT_DEFAULT_VALUE);
    pulseNumber =
        experimentPreferences.getInteger(
            SynapsePreferences.NUM_PULSES_INIT_KEY,
            SynapsePreferences.NUM_PULSES_INIT_DEFAULT_VALUE);
    sampleRate =
        experimentPreferences.getInteger(
            SynapsePreferences.SAMPLE_RATE_INIT_KEY,
            SynapsePreferences.SAMPLE_RATE_INIT_DEFAULT_VALUE);

    swingPropertyChangeSupport.firePropertyChange(Model.EVENT_PREFERENCES_UPDATE, true, false);
  }

  /** Given the state of the model, update the waveform x and y axis data arrays. */
  void updateWaveformChartData() {

    Driver driver;
    switch (waveform) {
      case Sawtooth:
        driver =
            new Sawtooth("Sawtooth", amplitude / 2, 0, amplitude / 2, getCalculatedFrequency());
        break;
      case QuarterSine:
        driver = new QuarterSine("QuarterSine", 0, 0, amplitude, getCalculatedFrequency());
        break;
      case Triangle:
        driver = new Triangle("Triangle", 0, 0, amplitude, getCalculatedFrequency());
        break;
      case Square:
        driver = new Square("Square", amplitude / 2, 0, amplitude / 2, getCalculatedFrequency());
        break;
      case SquareSmooth:
        driver = new SquareSmooth("SquareSmooth", 0, 0, amplitude, getCalculatedFrequency());
        break;
      default:
        driver = new HalfSine("HalfSine", 0, 0, amplitude, getCalculatedFrequency());
        break;
    }

    double stopTime = 1 / getCalculatedFrequency() * pulseNumber;
    double timeStep =
        1 / getCalculatedFrequency() / SynapsePreferences.CAPTURE_BUFFER_SIZE * pulseNumber;

    int counter = 0;
    for (double i = 0.0; i < stopTime; i = i + timeStep) {
      if (counter >= SynapsePreferences.CAPTURE_BUFFER_SIZE) {
        break;
      }
      waveformTimeData[counter] = i * SynapsePreferences.TIME_UNIT.getDivisor();
      waveformAmplitudeData[counter++] = driver.getSignal(i);
    }
  }

  // ///////////////////////////////////////////////////////////
  // GETTERS AND SETTERS //////////////////////////////////////
  // ///////////////////////////////////////////////////////////

  public float getAmplitude() {

    return amplitude;
  }

  public void setAmplitude(float amplitude) {

    this.amplitude = amplitude;
    swingPropertyChangeSupport.firePropertyChange(Model.EVENT_WAVEFORM_UPDATE, true, false);
  }

  public int getPulseWidth() {

    return pulseWidth;
  }

  public void setPulseWidth(int pulseWidth) {

    this.pulseWidth = pulseWidth;
    swingPropertyChangeSupport.firePropertyChange(Model.EVENT_WAVEFORM_UPDATE, true, false);
  }

  public double getCalculatedFrequency() {

    // System.out.println("pulseWidth = " + pulseWidth);
    return (1.0 / (2.0 * pulseWidth) * 1_000_000_000); // 50% duty cycle
  }

  public double[] getWaveformTimeData() {

    return waveformTimeData;
  }

  public double[] getWaveformAmplitudeData() {

    return waveformAmplitudeData;
  }

  public int getPulseNumber() {

    return pulseNumber;
  }

  public void setPulseNumber(int pulseNumber) {

    this.pulseNumber = pulseNumber;
    swingPropertyChangeSupport.firePropertyChange(Model.EVENT_WAVEFORM_UPDATE, true, false);
  }

  public SynapsePreferences.Waveform getWaveform() {

    return waveform;
  }

  public void setWaveform(SynapsePreferences.Waveform waveform) {

    this.waveform = waveform;
    swingPropertyChangeSupport.firePropertyChange(Model.EVENT_WAVEFORM_UPDATE, true, false);
  }

  public void setWaveform(String text) {

    this.waveform = Enum.valueOf(SynapsePreferences.Waveform.class, text);
    swingPropertyChangeSupport.firePropertyChange(Model.EVENT_WAVEFORM_UPDATE, true, false);
  }

  public Instruction getInstruction() {

    return instruction;
  }

  public void setInstruction(Instruction instruction) {

    this.instruction = instruction;
    swingPropertyChangeSupport.firePropertyChange(EVENT_INSTRUCTION_UPDATE, true, false);
  }

  public void setInstruction(String text) {

    this.instruction = Enum.valueOf(Instruction.class, text);
    swingPropertyChangeSupport.firePropertyChange(EVENT_INSTRUCTION_UPDATE, true, false);
  }

  public int getSampleRate() {
    return sampleRate;
  }

  public void setSampleRate(int sampleRate) {
    this.sampleRate = sampleRate;
  }

  public double getLastY() {

    return lastY;
  }

  public void setLastY(double lastY) {

    this.lastY = lastY;
  }

  public boolean isStartToggled() {

    return isStartToggled;
  }

  public void setStartToggled(boolean isStartToggled) {

    this.isStartToggled = isStartToggled;
  }

  @Override
  public ExperimentPreferences initAppPreferences() {

    return new SynapsePreferences();
  }
}
