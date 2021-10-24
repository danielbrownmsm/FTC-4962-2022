package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.commandframework.CommandScheduler;
import org.firstinspires.ftc.teamcode.commandframework.SequentialCommand;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;


public class ParkInWarehouseAuto extends LinearOpMode {
   private Drivetrain drivetrain;
   private Arm arm;
   private Vision vision;
   
   private SequentialCommand autoCommand;

   @Override
   public void runOpMode() {
      // create our subsystems
      drivetrain = new Drivetrain(telemetry, hardwareMap);
      arm = new Arm(telemetry, hardwareMap);
      vision = new Vision(telemetry, hardwareMap);

      // the autonomous command      
      autoCommand = new SequentialCommand(
         new DriveDistance(drivetrain, 12) //TODO actually use the right distance
      );
      
      // initiallize all the subsystems
      drivetrain.init();
      arm.init();
      vision.init();

      // schedule the auto command
      CommandScheduler.getInstance().schedule(autoCommand);
      
      waitForStart();

      //TODO actually make and work and test and stuff
      arm.setOutOfTheWay(); // raise the arm up a bit so that we don't run over it

      // loop until we are finished or we must finish
      while (!autoCommand.isFinished() && !isStopRequested()) {
         // run the command scheduler (ie the auto command, as well as all the periodic()s)
         CommandScheduler.getInstance().loop();
         sleep(5); // sleep a bit??? idk
      }
   }
}