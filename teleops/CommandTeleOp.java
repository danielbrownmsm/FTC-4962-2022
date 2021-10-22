package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.commandframework.CommandScheduler;
import org.firstinspires.ftc.teamcode.commandframework.ButtonR;
import org.firstinspires.ftc.teamcode.commandframework.ButtonR.ButtonType;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;


@TeleOp(name="command based teleop")
public class CommandTeleOp extends OpMode {
   private Drivetrain drivetrain;
   private Arm arm;
   
   private IntakeCommand intakeIn;
   private IntakeCommand intakeOut;
   private DriveCommand driveTeleOp;
   
   private ButtonR xButton;
   
   @Override
   public void init() {
      drivetrain = new Drivetrain(telemetry, hardwareMap);
      arm = new Arm(telemetry, hardwareMap);
      
      intakeIn = new IntakeCommand(arm, -1);
      intakeOut = new IntakeCommand(arm, 1);
      
      armLeft = new SetArmCommand(arm, 90);
   
      //private JoystickButton xButton = new JoystickButton(gamepad1);
      driveTeleOp = new DriveCommand(drivetrain, gamepad1);
   

      //xButton.whenPressed(); // ???
      CommandScheduler.getInstance().schedule(driveTeleOp);
      
      xButton = new ButtonR(gamepad1, ButtonType.X);
      
      xButton.whileHeld(intakeIn);
   }
   
   @Override
   public void init_loop() {
   }
   
   @Override
   public void loop() {
      CommandScheduler.getInstance().loop();
   }
   
   @Override
   public void stop() {
      
   }
}