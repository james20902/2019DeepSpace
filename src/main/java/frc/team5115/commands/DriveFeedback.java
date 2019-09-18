package frc.team5115.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.robot.Robot;
import frc.team5115.systems.DrivetrainSystem;

public class DriveFeedback extends Command {

    protected void initialize() {
        DrivetrainSystem.changeState(DrivetrainSystem.driveState.VISION);
    }

    protected void end(){
        DrivetrainSystem.changeState(DrivetrainSystem.driveState.DRIVE);
    }

    @Override
    protected boolean isFinished() {
        return Robot.camera.targetReached();
    }
}
