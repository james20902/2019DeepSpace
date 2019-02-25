package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.team5115.joysticks.InputManager;
import frc.team5115.robot.Robot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Drivetrain extends Subsystem {


    //define motor objects
    TalonSRX frontleft;
    TalonSRX frontright;
    TalonSRX backleft;
    TalonSRX backright;

    //define gyroscope object
    AHRS navx;

    Trajectory.Config config;

    Trajectory path;

    EncoderFollower left;
    EncoderFollower right;

    TankModifier modifier;

    NetworkTableEntry throttleDisplay;
    NetworkTableEntry gyroDisplay;

    Map<String, Object> settings = new HashMap<String, Object>();


    public int direction = 1;

    public Drivetrain() {
        dictionary = new ArrayList<>(Arrays.asList("Driving",
                "Transition",
                "Follow",
                "Reset",
                "Stopped"));

        //instantiate the things
        frontleft = new TalonSRX(3);
        frontright = new TalonSRX(4);
        backleft = new TalonSRX(1);
        backright = new TalonSRX(2);

        navx = new AHRS(SPI.Port.kMXP);

        //front left and front right motors will do the same thing that the back left and back right motor does
        frontright.set(ControlMode.Follower, 2);
        frontleft.set(ControlMode.Follower, 1);

        //assign encoder data to back left and back right motors respectively
        backright.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
        backleft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 5);
        navx.reset();

        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 0.5, 0.2, 60.0);

        settings.put("min", 0);
        settings.put("max", 1);
        throttleDisplay = Robot.tab.add("Max Speed", 0.5)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(settings) // specify widget properties here
                .getEntry();
        gyroDisplay = Robot.tab.add("Gyro", 0)
                .withWidget(BuiltInWidgets.kGyro)
                .getEntry();
    }


    public void drive(double left, double right, double throttle) {
        throttleDisplay.setDouble(throttle);
        //if our joystick gets weird, and somehow surpasses 1.0
        //assume it's 1.0
        if (Math.abs(left) > 1) {
            left = Math.signum(left);
        }
        if (Math.abs(right) > 1) {
            right = Math.signum(right);
        }
        //set our "speed" or voltage output to left and right speeds
        backleft.set(ControlMode.PercentOutput, left * throttle);
        backright.set(ControlMode.PercentOutput, right * throttle);
    }

    public double rightDist() {
        System.out.println(backright.getSelectedSensorPosition(0));
        double rightDist = direction * backright.getSelectedSensorPosition(0);
        return rightDist / 1440 * 6 * Math.PI / 12;
    }

    public double rightSpeed() {
        double rightspeed = backright.getSelectedSensorVelocity(0);
        return ((rightspeed * 6 * Math.PI * 10) / (1440 * 12));
    }

    public double averageSpeed() {
        return rightSpeed();
    }

    public double getTurnVelocity() {
        return navx.getRate();
    }

    public void generateTrajectory(double xDifference, double yDifference){
        //define waypoints
        Waypoint[] test = new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(1, 0, 0)
        };
        //follow (test) points with (config) parameters
        path = Pathfinder.generate(test, config);
        System.out.println("trajectory generated");
    }

    public void update() {
        switch (this.state) {
            case "Driving":
                drive(InputManager.primary.getLeft(),
                        InputManager.primary.getRight(),
                        InputManager.primary.processThrottle());
                System.out.println("driving");
                break;
            case "Transition":
                generateTrajectory(1, 1);
                //parameter for modify is robot drivebase width (in meters)
                modifier = new TankModifier(path).modify(0.6096);

                left = new EncoderFollower(modifier.getLeftTrajectory());
                right = new EncoderFollower(modifier.getRightTrajectory());

                left.configureEncoder(backleft.getSelectedSensorPosition(), 1440, 0.1524);
                right.configureEncoder(backright.getSelectedSensorPosition(), 1440, 0.1524);
                //Proportional Integral Derivative Velocity Acceleration
                left.configurePIDVA(1.5, 0.0, 0, 1 / 4.3, 0);
                right.configurePIDVA(1.5, 0.0, 0, 1 / 4.3, 0);
                setState("Follow");
                break;
            case "Follow":
                if (left.isFinished() && right.isFinished()) {
                    setState("Stopped");
                }
                drive(left.calculate(backleft.getSelectedSensorPosition()),
                        right.calculate(backright.getSelectedSensorPosition()),
                        1);
                break;
            case "Stopped":
                drive(0, 0, 0);
                System.out.println("stopped");
                break;
        }
    }
}