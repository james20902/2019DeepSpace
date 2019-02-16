package frc.team5115.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team5115.Debug;
import frc.team5115.commands.startLoopers;
import frc.team5115.joysticks.InputManager;
//import frc.team5115.subsystems.Arm;
import frc.team5115.subsystems.Drivetrain;

public class Robot extends TimedRobot {

    public static InputManager im;
    public static ShuffleboardTab tab = Shuffleboard.getTab("debug");

    public static Drivetrain drive;

    Thread thread = new Thread(new Debug());

    public void robotInit() {
        im = new InputManager();

        drive = new Drivetrain();

        //start subsystem threads
        //Scheduler.getInstance().add(new startLoopers());
        thread.start();
    }

    public void teleopInit() {
        im.checkControllers();
    }

    public void teleopPeriodic() {
        drive.drive(InputManager.primary.getLeft(),
                InputManager.primary.getRight(),
                InputManager.primary.processThrottle());
        //Scheduler.getInstance().run();
    }

    public void autonomousInit(){
        drive.setState("Transition");
    }

    public void autonomousPeriodic(){
        drive.update();
    }

}

