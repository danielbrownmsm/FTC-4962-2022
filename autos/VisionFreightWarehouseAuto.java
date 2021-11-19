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

      // the autonomous command
      /**
       * use vision to store the correct height placement thingy
       * drive to the wobble thing
       * raise the arm to the correct height
       * extend the linear slide
       * outake the preloaded cube
       * ???move linear slide back in???
       * drive back
       * turn to the warehouse
       * drive into the warehouse
       */
        //TODO maybe make some of these ParallelCommands so we don't take up too much time in auto
      autoCommand = new SequentialCommand(
        //new VisionStoreLevel(vision),
        new WaitCommand(3000), // wait 3 seconds
        new VisionRaiseArm(arm, vision),
        new DriveDistance(drivetrain, 5), //TODO use correct distance 
        new SetLinearSlide(arm, 6), //TODO use correct distance
        new IntakeCommand(arm, -1) //TODO this needs to have a timeout or something so it can last a while
        /*// or maybe have a WaitCommand() ???
        new SetLinearSlide(arm, 0), //TODO use correct distance
        new DriveDistance(drivetrain, -1), //TODO use correct distance
        new TurnHeadingCommand(drivetrain, 90), //TODO use correct angle
        new DriveDistance(drivetrain, 12) //TODO actually use the right distance*/
      );
      
      // initiallize all the subsystems
      drivetrain.init();
      arm.init();
      vision.init();

      // schedule the auto command
      CommandScheduler2.schedule(autoCommand);
      
      waitForStart();

      //TODO actually make and work and test and stuff
      //arm.setOutOfTheWay(); // raise the arm up a bit so that we don't run over it

      // loop until we are finished or we must finish
      while (!autoCommand.isFinished() && !isStopRequested()) {
         // run the command scheduler (ie the auto command, as well as all the periodic()s)
         CommandScheduler2.loop();
         sleep(5); // sleep a bit??? idk
      }
   }
}