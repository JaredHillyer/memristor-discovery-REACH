 # Conductance Experiment

This app allows you to drive a memristor in series with a resistor with continual square pulse waveforms until the memristor is set to the desired conductance value. An experiment can also be run that periodically reads the conductance of the memristor below the threshold voltage in order to capture the decay and endurance behavior of the memristors. Before setting the memristor to a desired conductance, make sure to reset the memristor using a negative potential pulse.

The reset response can be observed as either a time series (V1+/T and V2+/T), I-T or G-T plot.
The set response can be observed as either a G-T plot where the G value is determined programmatically from a below threshold read pulse and plotted as a single point.

## Connections

1.  Arbitrary Waveform Generator W1 (Analog Discovery 2's "W1" and "gnd" connectors) is connected across the memristor (M) and series resistor (R).
2.  Oscilloscope Probe V1+ (Analog Discovery 2's "1+" and "1-" connectors) is connected across the memristor (M) and series resistor (Rs).
3.  Oscilloscope Probe V2+ (Analog Discovery 2's "2+" and "2-" connectors) is connected across the series resistor (R).

## Memristor Selection

Use the toggle switches near the top of the app window to connect one or more individual memristors into the circuit.

## Series Resistor

The series resistor provide two important functions:

1.  Limits the maximum current through the memristor to prevent device damage (see memristor datasheet).
2.  Allows for a current measurement to be made with the oscilloscope (I = V2+/R).

## Controls

The control panel can be used to adjust the driver waveform of W1. The series resistor value control should correspond to the actual series resistance value used in the experimental setup in order to calculate an accurate current value.

## Conductance Plot

The conductance plot (G-V) uses a running average value, k, to smooth the data. A k value of 0 will eliminate all averaging. The larger k is, the more averaging will occur. If k is too big you may unknowingly hide important memristor behavior so it is best to keep k as low as possible.

## Exporting Data

Any plot can be right-clicked to export the data in either chart format (save As...) or comma-separated-values (Export As...), which can be opened in spreadsheet software. For "Export As..." a directory needs to be selected. In that directory, an individual CSV file will be created for each series in the plot.

## Preferences

The preferences window allows you to save your preferred experimental control parameters between sessions of using the app.