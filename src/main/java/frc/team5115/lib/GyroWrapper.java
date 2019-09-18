package frc.team5115.lib;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;

public class GyroWrapper {

    private AHRS gyro;

    public GyroWrapper(){
        gyro = new AHRS(SerialPort.Port.kUSB);
        if(!gyro.isConnected())
            gyro = new AHRS(SerialPort.Port.kUSB1);
        if(!gyro.isConnected())
            gyro = new AHRS(SerialPort.Port.kUSB2);
        if(!gyro.isConnected())
            DriverStation.reportError("Gyro not found on any interfaces!", false);
            gyro = null;
    }


}
