package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.commandframework.CommandScheduler;
import org.firstinspires.ftc.teamcode.commandframework.ButtonR;
import org.firstinspires.ftc.teamcode.commandframework.ButtonR.ButtonType;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;


@TeleOp(name="Competition TeleOp", group="comp")
public class CommandTeleOp extends OpMode {
   private Drivetrain drivetrain;
   private Arm arm;
   private Vision vision;
   
   // arm commands
   private IntakeCommand intakeIn;
   private IntakeCommand intakeOut;
   
   private IncrementTableCommand tableLeft;
   private IncrementTableCommand tableRight;
   
   private RaiseArmCommand raiseArm;
   private RaiseArmCommand lowerArm;
   
   private ExtendSlideCommand extendSlide;
   private ExtendSlideCommand retractSlide;
   
   // drivetrain commands
   private DriveCommand driveTeleOp;
   
   // buttons
   private ButtonR xButton2;
   private ButtonR yButton2;
   private ButtonR aButton2;
   private ButtonR bButton2;
   
   private ButtonR leftBumper2;
   private ButtonR rightBumper2;
   private ButtonR leftStick2;
   private ButtonR rightStick2;
   
   private ButtonR dpadUp2;
   private ButtonR dpadDown2;
   private ButtonR dpadLeft2;
   private ButtonR dpadRight2;
   
   @Override
   public void init() {
      // buttons
      xButton2 = new ButtonR(gamepad2, ButtonType.X);
      yButton2 = new ButtonR(gamepad2, ButtonType.Y);
      aButton2 = new ButtonR(gamepad2, ButtonType.A);
      bButton2 = new ButtonR(gamepad2, ButtonType.B);
   
      leftBumper2 = new ButtonR(gamepad2, ButtonType.LeftBumper);
      rightBumper2 = new ButtonR(gamepad2, ButtonType.RightBumper);
      leftStick2 = new ButtonR(gamepad2, ButtonType.LeftStick);
      rightStick2 = new ButtonR(gamepad2, ButtonType.RightStick);
   
      dpadUp2 = new ButtonR(gamepad2, ButtonType.DpadUp);
      dpadDown2 = new ButtonR(gamepad2, ButtonType.DpadDown);
      dpadLeft2 = new ButtonR(gamepad2, ButtonType.DpadLeft);
      dpadRight2 = new ButtonR(gamepad2, ButtonType.DpadRight);

      
      // subsystems
      drivetrain = new Drivetrain(telemetry, hardwareMap);
      arm = new Arm(telemetry, hardwareMap);
      vision = new Vision(telemetry, hardwareMap);
      
      drivetrain.init();
      arm.init();
      vision.init();
      
      
      // commands
      //TODO change values to be better
      intakeIn = new IntakeCommand(arm, -1);
      intakeOut = new IntakeCommand(arm, 1);
      
      tableLeft = new IncrementTableCommand(arm, 1);
      tableRight = new IncrementTableCommand(arm, -1);
      
      raiseArm = new RaiseArmCommand(arm, 1);
      lowerArm = new RaiseArmCommand(arm, -1);
      
      extendSlide = new ExtendSlideCommand(arm, 1);
      retractSlide = new ExtendSlideCommand(arm, -1);
      

      driveTeleOp = new DriveCommand(drivetrain, gamepad1);
      CommandScheduler.getInstance().schedule(driveTeleOp); // schedule our command already
      

      // bind commands
      rightBumper2.whileHeld(tableRight);
      leftBumper2.whileHeld(tableLeft);
      
      dpadUp2.whileHeld(raiseArm);
      dpadDown2.whileHeld(lowerArm);
      
      aButton2.whenPressed(intakeIn);
      bButton2.whenPressed(intakeOut);
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