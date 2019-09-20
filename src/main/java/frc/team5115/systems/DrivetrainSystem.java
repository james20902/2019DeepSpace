package frc.team5115.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.Konstanten;
import frc.team5115.lib.TalonWrapper;
import frc.team5115.robot.Robot;

public class DrivetrainSystem extends Command {

    public enum driveState{
        DRIVE,
        VISION,
        STOP,
    }

    private static driveState systemState;

    private TalonWrapper frontleft;
    private TalonWrapper frontright;
    private TalonWrapper backleft;
    private TalonWrapper backright;

    private double throttle = 0.5;

    protected void initialize() {
        systemState = driveState.DRIVE;

        frontleft = new TalonWrapper(Konstanten.FRONT_LEFT_DRIVE);
        frontright = new TalonWrapper(Konstanten.FRONT_RIGHT_DRIVE);
        backleft = new TalonWrapper(Konstanten.BACK_LEFT_DRIVE);
        backright = new TalonWrapper(Konstanten.BACK_RIGHT_DRIVE);

        frontleft.set(ControlMode.Follower, Konstanten.BACK_LEFT_DRIVE);
        frontright.set(ControlMode.Follower, Konstanten.BACK_RIGHT_DRIVE);

        frontright.setInverted(true);
        backright.setInverted(true);
    }

    public static void changeState(driveState state){
        systemState = state;
    }

    public void drive(double left, double right, double throttle){
        //set our "speed" or voltage output to left and right speeds
        backleft.set(ControlMode.PercentOutput, left*throttle);
        backright.set(ControlMode.PercentOutput, right*throttle);
    }

    public double processThrottle(){
        throttle += 0.01 *(Robot.controller.getRawAxis(3) - Robot.controller.getRawAxis(2));

        if (throttle > 1){
            throttle = 1;
        } else if(throttle < 0){
            throttle = 0;
        }
        return throttle;
    }

    public void tankDrive(){
        double left = -Robot.controller.getRawAxis(1) + Robot.controller.getRawAxis(4);
        double right = -Robot.controller.getRawAxis(1) - Robot.controller.getRawAxis(4);

        drive(left, right, processThrottle());
    }

    protected void execute(){
        if(systemState == driveState.DRIVE){
            tankDrive();
        } else if(systemState == driveState.VISION){
            drive(0 ,0, 0);
        } else {
            drive(0, 0, 0);
        }
    }

    protected boolean isFinished() { return false; }

}
