package frc.team5115;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    //http://docs.limelightvision.io/en/latest/cs_estimating_distance.html

    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;
    private NetworkTableEntry tv;
    private NetworkTableEntry LED;
    private NetworkTableEntry CAM;
    private NetworkTableEntry pipeline;

    public Limelight(){
        NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
        tx = limelight.getEntry("tx");
        ty = limelight.getEntry("ty");
        ta = limelight.getEntry("ta");
        tv = limelight.getEntry("tv");
        LED = limelight.getEntry("ledMode");
        CAM = limelight.getEntry("camMode");
        pipeline = limelight.getEntry("pipeline");
    }

    public boolean isValid(){
        return tv.getNumber(0).equals(1);
    }

    public double getXOffset(){
        return tx.getDouble(0);
    }
    public double getYOffset(){
        return ty.getDouble(0);
    }
    public double getContourArea(){
        return ta.getDouble(0);
    }

    public void cameraMode(){
        LED.setNumber(1);
        CAM.setNumber(1);
    }

    public void scannerMode(){
        LED.setNumber(3);
        CAM.setNumber(0);
    }

    public void switchPipelines(int pipeline){
        this.pipeline.setNumber(pipeline);
    }

}

