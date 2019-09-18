package frc.team5115.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team5115.Limelight;
import frc.team5115.commands.DriveFeedback;
import frc.team5115.commands.arm.ArmDown;
import frc.team5115.commands.arm.ArmUp;
import frc.team5115.commands.startSystems;
import frc.team5115.commands.wrist.*;
import frc.team5115.lib.ButtonWrapper;

public class Robot extends TimedRobot {

    public static Limelight camera;
    public static Joystick controller;
    public ButtonWrapper Test;

    public void robotInit() {
        controller = new Joystick(0);
        camera = new Limelight();
        camera.cameraMode();
        Scheduler.getInstance().add(new startSystems());
        new ButtonWrapper(controller, 1).whenPressed(new DriveFeedback());
        new ButtonWrapper(controller, 0).whenPressed(new WristUp());
        new ButtonWrapper(controller, 180).whenPressed(new WristDown());
        new ButtonWrapper(controller, 270).whenPressed(new WristLeft());
        new ButtonWrapper(controller, 90).whenPressed(new WristRight());
        new ButtonWrapper(controller, 4).whenPressed(new ArmUp());
        new ButtonWrapper(controller, 2).whenPressed(new ArmDown());
    }

    public void robotPeriodic() {
        if (RobotState.isEnabled()) {
            Scheduler.getInstance().run();
        }
    }

}

