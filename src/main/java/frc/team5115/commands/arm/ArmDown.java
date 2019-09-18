package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.systems.ArmSystem;

public class ArmDown extends Command {
    protected void initialize() {
        ArmSystem.changeState(ArmSystem.ArmState.DOWN);
    }

    protected void end(){
        ArmSystem.changeState(ArmSystem.ArmState.NEUTRAL);
    }

    protected boolean isFinished(){ return false; }
}
