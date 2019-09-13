package frc.team5115.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.Konstanten;
import frc.team5115.lib.TalonWrapper;

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

    protected void initialize() {
        systemState = driveState.STOP;

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

    protected void execute(){
        if(systemState == driveState.DRIVE){
            drive(0, 0, 0);
        } else if(systemState == driveState.VISION){
            drive(0 ,0, 0);
        } else {
            drive(0, 0, 0);
        }
    }

    protected boolean isFinished() { return false; }

}
