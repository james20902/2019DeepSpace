package frc.team5115.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.Konstanten;
import frc.team5115.lib.VictorWrapper;

public class ArmSystem extends Command {

    public enum ArmState{
        MOVING,
        NEUTRAL
    }

    private static ArmState systemState;
    private VictorWrapper DART;

    protected void initialize() {
        systemState = ArmState.NEUTRAL;

        DART = new VictorWrapper(Konstanten.DART_ID, Konstanten.TOP_SWITCH, Konstanten.BOTTOM_SWITCH);
    }

    protected void execute(){
        if(systemState == ArmState.MOVING){
            //if(PID.finished),
            //move(PID.output);
        } else {
            move(0);
        }
    }

    private void move(double percent){
        DART.set(ControlMode.PercentOutput, percent);
    }

    public static void changeState(ArmState state){
        systemState = state;
    }


    protected boolean isFinished() { return false; }

}
