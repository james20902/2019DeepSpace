package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.systems.ArmSystem;

public class ArmUp extends Command {
    protected void initialize() {
        ArmSystem.changeState(ArmSystem.ArmState.UP);
    }

    protected void end(){
        ArmSystem.changeState(ArmSystem.ArmState.NEUTRAL);
    }

    protected boolean isFinished(){ return false; }
}
