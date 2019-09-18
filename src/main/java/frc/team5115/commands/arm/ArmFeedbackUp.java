package frc.team5115.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.systems.ArmSystem;

public class ArmFeedbackUp extends Command {
    protected void initialize() {
        ArmSystem.changeState(ArmSystem.ArmState.MOVING);
    }

    protected boolean isFinished(){ return true; }
}
