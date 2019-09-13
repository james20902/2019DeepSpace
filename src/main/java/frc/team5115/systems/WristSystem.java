package frc.team5115.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.Konstanten;
import frc.team5115.lib.VictorWrapper;

public class WristSystem extends Command {

    public enum wristState{
        LEFT,
        RIGHT,
        FORWARDS,
        BACKWARDS,
        NEUTRAL
    }

    private static wristState systemState;

    private VictorWrapper xAxis;
    private VictorWrapper yAxis;

    private DigitalInput left;
    private DigitalInput right;

    protected void initialize() {
        systemState = wristState.NEUTRAL;

        xAxis = new VictorWrapper(Konstanten.WRIST_X);
        yAxis = new VictorWrapper(Konstanten.WRIST_Y);

        left = new DigitalInput(Konstanten.LEFT_SWITCH);
        right = new DigitalInput(Konstanten.RIGHT_SWTICH);
    }

    public static void changeState(wristState state){
        state = systemState;
    }

    protected void execute(){
        switch(systemState){
            case LEFT:
                moveX(-0.6);
                break;
            case RIGHT:
                moveX(0.6);
                break;
            case FORWARDS:
                moveY(0.5);
                break;
            case BACKWARDS:
                moveY(-0.5);
            default:
                moveX(0);
                moveY(0);
                break;
        }
    }


    public void moveX(double speed){
        double temp = speed;
        DigitalInput limit = Math.signum(speed) == 1 ? right : left;
        if(!limit.get()){
            temp = 0;
        }
        xAxis.set(ControlMode.PercentOutput, temp);
    }

    public void moveY(double speed){
        yAxis.set(ControlMode.PercentOutput, speed);
    }

    protected boolean isFinished() { return false; }

}
