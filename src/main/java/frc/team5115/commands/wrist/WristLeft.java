package frc.team5115.commands.wrist;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.systems.WristSystem;

public class WristLeft extends Command {
    protected void initialize() {
        WristSystem.changeState(WristSystem.wristState.LEFT);
    }

    protected void end(){
        WristSystem.changeState(WristSystem.wristState.NEUTRAL);
    }

    protected boolean isFinished(){ return false; }
}
