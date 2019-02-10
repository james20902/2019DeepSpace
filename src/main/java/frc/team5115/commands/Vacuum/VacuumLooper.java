package frc.team5115.commands.Vacuum;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.subsystems.Vacuum;

public class VacuumLooper extends Command {

    Vacuum succ;

    protected void initialize(){
        succ = new Vacuum();
    }

    protected void execute(){
        succ.update();
    }

    protected void interrupted(){
        succ.setState("Stopped");
    }

    protected boolean isFinished(){
        return false;
    }

}