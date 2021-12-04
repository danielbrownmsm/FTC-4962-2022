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
   private double lastTime = 0;
   private double now = 0;
   
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
        new WaitCommand(2500), // wait 3 seconds (for the camera to get its lock and everything)
        new VisionRaiseArm(arm, vision), // raise the arm to the correct height
        new WaitCommand(500), // wait for like a second cause idk it's not working
        new SetTurntableCommand(arm, 32),
        new WaitCommand(500),
        new DriveDistance(drivetrain, -16.5), //TODO use correct distance
        new WaitCommand(500),
        //new DriveCommand(drivetrain, 0, 0),
        new SetLinearSlide(arm, 5), //TODO use correct distance
        new WaitCommand(500),
        new IntakeCommand(arm, 1), // deposit the freight
        new WaitCommand(1000), // wait for the freight to fall out
        new IntakeCommand(arm, 0), // stop the intake servos
        new SetLinearSlide(arm, 0.2), // bring the slide back in
        new WaitCommand(500),
        //new DriveDistance(drivetrain, -1), // back up TODO use correct distance
        //new TurnHeadingCommand(drivetrain, 90), // turn towards the warehouse TODO use correct angle
        new WaitCommand(1000)
        //new DriveDistance(drivetrain, 50) // drive into the warehouse and parkTODO actually use the right distance*/
      );

      // schedule the auto command
      CommandScheduler2.schedule(autoCommand);
      telemetry.addData("state", "waiting for start");
      telemetry.update();
      waitForStart();

      // loop until we are finished or we must finish
      while (!autoCommand.isFinished() && !isStopRequested()) {
         now = System.nanoTime() / 1e6;
         telemetry.addData("loop time", now - lastTime);
         lastTime = now;
         
         // run the command scheduler (ie the auto command, as well as all the periodic()s)
         CommandScheduler2.loop();
         telemetry.update();
         //sleep(5); // sleep a bit??? idk
      }
   }
}