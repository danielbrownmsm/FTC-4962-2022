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
      drivetrain = new Drivetrain(telemetry, hardwareMap);
      arm = new Arm(telemetry, hardwareMap);
      vision = new Vision(telemetry, hardwareMap);
      
      autoCommand = new SequentialCommand(
         new DriveDistance(drivetrain, 12)
      );
      
      CommandScheduler.getInstance().schedule(autoCommand);
      
      waitForStart();
      while (!autoCommand.isFinished() && !isStopRequested()) { //???
         CommandScheduler.getInstance().loop();
         sleep(10);
      }
   }
   
   //@Override
   //public void 
}