package frc.team5115.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import frc.team5115.Konstanten;
import frc.team5115.lib.PID;
import frc.team5115.lib.VictorWrapper;

public class ArmSystem extends Command {

    public enum ArmState{
        MOVING,
        UPMANUAL,
        DOWNMANUAL,
        UP,
        DOWN,
        NEUTRAL
    }

    private PID pid;
    private double target = 0;

    private static ArmState systemState;
    private VictorWrapper DART;
    private AHRS gyro;
    private SerialPort.Port[] ports = {
            SerialPort.Port.kUSB,
            SerialPort.Port.kUSB1,
            SerialPort.Port.kUSB2
    };

    protected void initialize() {
        systemState = ArmState.NEUTRAL;
        pid = new PID(1, 0, 0);
        DART = new VictorWrapper(Konstanten.DART_ID, Konstanten.TOP_SWITCH, Konstanten.BOTTOM_SWITCH);
        while(gyro == null || !gyro.isConnected()){
            for(SerialPort.Port port : ports){
                gyro = new AHRS(port);
            }
        }
    }

    protected void execute(){
        System.out.println();
        switch(systemState){
            case UPMANUAL:
                move(0.6);
                break;
            case DOWNMANUAL:
                move(-0.5);
                break;
            case UP:
                if(!incrementTarget()){
                    changeState(ArmState.NEUTRAL);
                }
                changeState(ArmState.MOVING);
                break;
            case DOWN:
                if(!decrementTarget()){
                    changeState(ArmState.NEUTRAL);
                }
                changeState(ArmState.MOVING);
                break;
            case MOVING:
                if(pid.isFinished(.4, .2) || DART.switchHit()){
                  changeState(ArmState.NEUTRAL);
                }
                move(pid.output(target, gyro.getDisplacementZ(), gyro.getVelocityZ()));
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

    public boolean incrementTarget(){
        return true;
    }

    public boolean decrementTarget(){
        return true;
    }

    protected boolean isFinished() { return false; }

}
