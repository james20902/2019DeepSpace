package frc.team5115.systems;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class startSystems extends CommandGroup {

    public startSystems(){
        addParallel(new ArmSystem());
        addParallel(new WristSystem());
        addParallel(new DrivetrainSystem());
    }

}