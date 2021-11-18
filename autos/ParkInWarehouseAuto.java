package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commandframework.CommandScheduler2;
import org.firstinspires.ftc.teamcode.commandframework.*;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;

@Autonomous(name="Park in Warehouse", group="compauto")
public class ParkInWarehouseAuto extends LinearOpMode {
   private Drivetrain drivetrain;
   private Arm arm;
   private Vision vision;
   
   private Command autoCommand;

   @Override
   public void runOpMode() {
      // clear the lists and add the telemetry object
      CommandScheduler2.init(telemetry);
      
      // create our subsystems
      drivetrain = new Drivetrain(telemetry, hardwareMap);
      arm = new Arm(telemetry, hardwareMap);
      vision = new Vision(telemetry, hardwareMap);

      // initiallize all the subsystems
      drivetrain.init();
      arm.init();
      vision.init();

      // the autonomous command      
      autoCommand = new SequentialCommand(
         new SetArmCommand(arm, 20), // raise the arm off the ground a bit so we don't run over it
         new DriveDistance(drivetrain, 26) //TODO actually use the right distance
      );
      //autoCommand = new DriveDistance(drivetrain, 26);
      telemetry.addData("autoCommand", autoCommand);
      
      // schedule the auto command
      CommandScheduler2.schedule(autoCommand);
      telemetry.addData("state", "waiting for start, init-ed");
      telemetry.update();
      
      waitForStart();
      telemetry.addData("state", "starting. . .");
      telemetry.update();

      // loop until we are finished or we must finish
      do {
         // run the command scheduler (ie the auto command, as well as all the periodic()s)
         CommandScheduler2.loop();
         telemetry.addData("state", "executing. . .");
         telemetry.update();
         //sleep(5); // sleep a bit??? idk
         //sleep(10000);
      } while (!isStopRequested() && !autoCommand.isFinished());
      
      sleep(100);
   }
}