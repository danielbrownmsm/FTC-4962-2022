package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.commandframework.CommandScheduler2;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.commands.*;

@TeleOp(name="Just Tank-drive TeleOp", group="noncomp")
public class TankDriveTeleOp extends OpMode {
   private Drivetrain drivetrain;
   private DriveCommand driveTeleOp;
   
   @Override
   public void init() {
      CommandScheduler2.init(telemetry);

      drivetrain = new Drivetrain(telemetry, hardwareMap);
      driveTeleOp = new DriveCommand(drivetrain, gamepad1);

      drivetrain.init();

      CommandScheduler2.schedule(driveTeleOp);
   }
   
   @Override
   public void loop() {
      CommandScheduler2.loop();
   }
}