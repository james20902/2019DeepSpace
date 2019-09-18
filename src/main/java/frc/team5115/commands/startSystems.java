package frc.team5115.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team5115.systems.ArmSystem;
import frc.team5115.systems.DrivetrainSystem;
import frc.team5115.systems.WristSystem;

public class startSystems extends CommandGroup {

    public startSystems(){
        addParallel(new ArmSystem());
        addParallel(new WristSystem());
        addParallel(new DrivetrainSystem());
    }

}