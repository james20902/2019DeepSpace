package frc.team5115.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.Konstanten;
import frc.team5115.lib.GyroWrapper;
import frc.team5115.lib.PID;
import frc.team5115.lib.VictorWrapper;

public class ArmSystem extends Command {

    public enum ArmState{
        MOVING,
        UP,
        DOWN,
        NEUTRAL
    }

    private PID pid;
    private static ArmState systemState;
    private VictorWrapper DART;
    private GyroWrapper gyro;

    protected void initialize() {
        systemState = ArmState.NEUTRAL;
//        pid = new PID(1, 0, 0);
        DART = new VictorWrapper(Konstanten.DART_ID, Konstanten.TOP_SWITCH, Konstanten.BOTTOM_SWITCH);
        gyro = new GyroWrapper();
    }

    protected void execute(){
        switch(systemState){
            case UP:
                move(0.6);
                break;
            case DOWN:
                move(-0.5);
                break;
            case MOVING:
//                if(pid.isFinished() || DART.switchHit()){
//                  changeState(NEUTRAL);
//                }
//                move(pid.output());
                break;
            default:
                move(0);
                break;
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
