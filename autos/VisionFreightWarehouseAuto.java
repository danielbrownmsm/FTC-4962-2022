package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commandframework.CommandScheduler2;
import org.firstinspires.ftc.teamcode.commandframework.SequentialCommand;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;

@Autonomous(name="Vision Freight Warehouse", group="compauto")
public class VisionFreightWarehouseAuto extends LinearOpMode {
   private Drivetrain drivetrain;
   private Arm arm;
   private Vision vision;
   
   private SequentialCommand autoCommand;

   @Override
   public void runOpMode() {
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
      //TODO maybe make some of these ParallelCommands so we don't take up too much time in auto
      autoCommand = new SequentialCommand(
        new WaitCommand(3000), // wait 3 seconds (for the camera to get its lock and everything)
        new VisionRaiseArm(arm, vision), // raise the arm to the correct height
        new DriveDistance(drivetrain, 5), //TODO use correct distance 
        new SetLinearSlide(arm, 6), //TODO use correct distance
        new IntakeCommand(arm, -1), // deposit the freight
        new WaitCommand(2000), // wait for the freight to fall out
        new IntakeCommand(arm, 0), // stop the intake servos
        new SetLinearSlide(arm, 0)/*, // bring the slide back in
        new DriveDistance(drivetrain, -1), // back up TODO use correct distance
        new TurnHeadingCommand(drivetrain, 90), // turn towards the warehouse TODO use correct angle
        new DriveDistance(drivetrain, 12) // drive into the warehouse and parkTODO actually use the right distance*/
      );

      // schedule the auto command
      CommandScheduler2.schedule(autoCommand);
      telemetry.addData("state", "waiting for start");
      waitForStart();

      // loop until we are finished or we must finish
      while (!autoCommand.isFinished() && !isStopRequested()) {
         // run the command scheduler (ie the auto command, as well as all the periodic()s)
         CommandScheduler2.loop();
         sleep(5); // sleep a bit??? idk
      }
   }
}