package frc.team5115.commands.wrist;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.systems.WristSystem;

public class WristRight extends Command {
    protected void initialize() {
        WristSystem.changeState(WristSystem.wristState.RIGHT);
    }

    protected void end(){
        WristSystem.changeState(WristSystem.wristState.NEUTRAL);
    }

    protected boolean isFinished(){ return false; }
}
