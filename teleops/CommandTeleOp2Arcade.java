package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.commandframework.CommandScheduler2;
import org.firstinspires.ftc.teamcode.commandframework.ButtonR;
import org.firstinspires.ftc.teamcode.commandframework.ButtonR.ButtonType;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;


@TeleOp(name="Competition TeleOp Arcade", group="comp")
public class CommandTeleOp2Arcade extends OpMode {
   private CommandScheduler2 scheduler;
   
   private Drivetrain drivetrain;
   private Arm arm;
   private Vision vision;
   private Wheel wheel;
   
   // arm commands
   private IntakeCommand intakeIn;
   private IntakeCommand intakeOut;
   private IntakeCommand stopIntake;
   
   private IncrementTableCommand tableLeft;
   private IncrementTableCommand tableRight;
   
   private RaiseArmCommand raiseArm;
   private RaiseArmCommand lowerArm;
   
   private ExtendSlideCommand extendSlide;
   private ExtendSlideCommand retractSlide;
   
   private ArmPresetCommand setArmLevel3;
   private ArmPresetCommand setArmLevel1;
   
   private SetWheelCommand turnWheel;
   private SetWheelCommand stopWheel;
   
   // drivetrain commands
   private ArcadeDriveCommand driveTeleOp;
   
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
   
   private ButtonR aButton1;
   private ButtonR bButton1;
   
   @Override
   public void init() {
      // scheduler
      CommandScheduler2.init(telemetry);
      
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
      
      aButton1 = new ButtonR(gamepad1, ButtonType.A);
      bButton1 = new ButtonR(gamepad1, ButtonType.B);
      
      // subsystems
      drivetrain = new Drivetrain(telemetry, hardwareMap);
      arm = new Arm(telemetry, hardwareMap);
      wheel = new Wheel(telemetry, hardwareMap);
      //vision = new Vision(telemetry, hardwareMap);
      
      drivetrain.init();
      arm.init();
      wheel.init();
      //vision.init();
      
      // commands
      intakeIn = new IntakeCommand(arm, -1);
      intakeOut = new IntakeCommand(arm, 1);
      stopIntake = new IntakeCommand(arm, 0);
      
      tableLeft = new IncrementTableCommand(arm, -2);
      tableRight = new IncrementTableCommand(arm, 2);
      
      raiseArm = new RaiseArmCommand(arm, 1.5);
      lowerArm = new RaiseArmCommand(arm, -1.5);
      
      extendSlide = new ExtendSlideCommand(arm, 1);
      retractSlide = new ExtendSlideCommand(arm, -1);
      
      setArmLevel3 = new ArmPresetCommand(arm, 3);
      setArmLevel1 = new ArmPresetCommand(arm, 1);
      
      // it's time to get wheel, cause things are getting wheely wheel around here
      turnWheel = new SetWheelCommand(wheel, 1);
      stopWheel = new SetWheelCommand(wheel, 0);

      driveTeleOp = new ArcadeDriveCommand(drivetrain, gamepad1);
      CommandScheduler2.schedule(driveTeleOp); // schedule our command already

      // bind commands
      dpadLeft2.whileHeld(tableRight);
      dpadRight2.whileHeld(tableLeft);
      
      dpadUp2.whileHeld(raiseArm);
      dpadDown2.whileHeld(lowerArm);
      
      leftBumper2.whileHeld(extendSlide);
      rightBumper2.whileHeld(retractSlide);
      
      aButton2.whileHeld(intakeIn);
      bButton2.whileHeld(intakeOut);
      aButton2.whenReleased(stopIntake);
      bButton2.whenReleased(stopIntake);
      
      yButton2.whenPressed(setArmLevel3);
      xButton2.whenPressed(setArmLevel1);
      
      aButton1.whenPressed(turnWheel);
      aButton1.whenReleased(stopWheel);
   }
   
   @Override
   public void init_loop() {
   }
   
   @Override
   public void loop() {
      CommandScheduler2.loop();
   }
   
   @Override
   public void stop() {
      
   }
}
