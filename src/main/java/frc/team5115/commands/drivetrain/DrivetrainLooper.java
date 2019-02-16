package frc.team5115.commands.drivetrain;

import frc.team5115.commands.Looper;
import frc.team5115.robot.Robot;

public class DrivetrainLooper extends Looper {

    protected void initialize(){
        system = Robot.drive;
    }

}
