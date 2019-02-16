package frc.team5115.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team5115.commands.drivetrain.DrivetrainLooper;

public class startLoopers extends CommandGroup {

    public startLoopers(){
        addParallel(new DrivetrainLooper());
    }

}