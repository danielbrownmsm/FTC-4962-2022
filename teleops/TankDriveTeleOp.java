package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.commandframework.CommandScheduler;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.commands.*;

@TeleOp(name="basic tank drive")
public class TankDriveTeleOp extends OpMode {
   private Drivetrain drivetrain = new Drivetrain(telemetry, hardwareMap);
   
   //private JoystickButton xButton = new JoystickButton(gamepad1);
   private DriveCommand driveTeleOp = new DriveCommand(drivetrain, gamepad1);
   
   @Override
   public void init() {
      //xButton.whenPressed(); // ???
      CommandScheduler.getInstance().schedule(driveTeleOp);
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