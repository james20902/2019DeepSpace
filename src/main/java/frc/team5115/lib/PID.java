package frc.team5115.lib;

public class PID {

    private double kp;
    private double ki;
    private double kd;
    private double maxOutput;

    private double error;
    private double errorAccum = 0;
    private double dError;
    private double output;

    public PID(double p, double i, double d, double max) {
        kp = p;
        ki = i;
        kd = d;
        maxOutput = max;
    }

    public PID(double p, double i, double d) {
        this(p, i, d, 1);
    }

    //Runs the PID loop. Must be run every Constants.DELAY seconds. dReading should be in reading units per second
    public double getPID(double setpoint, double reading, double dReading) {
        error = setpoint - reading;
        dError = -dReading;		// result of taking the derivative of the equation above with respect to time

        output = kp * error + ki * errorAccum + kd * dError;
        // Do not integrate if the output exceeds max to avoid intergral windup. See youtu.be/fusr9eTceEo
        if (Math.abs(output) <= maxOutput) {
            errorAccum += error * 0.05;
        }

        // Do not return a value greater than the maximum, but make sure it's the right sign
        if (Math.abs(output) > maxOutput) {
            output = maxOutput * Math.signum(output);
        }
        return output;
    }

    public double getPID(double setpoint, double reading) {
        return getPID(setpoint, reading, 0);
    }

    public boolean isFinished(double tolerance, double dErrorTolerance) {
        return (Math.abs(error) < tolerance && Math.abs(dError) < dErrorTolerance);
    }

    public double getError() {
        return error;
    }
}
