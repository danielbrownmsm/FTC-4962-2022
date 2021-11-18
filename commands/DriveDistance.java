package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

/**
 * A DriveDistance command.
 * Made for auto, drives us the given distance on a heading
 */
public class DriveDistance extends Command {
   private final Drivetrain drivetrain;
   private double distance;
   
   /**
    * Makes a new DriveDistance command. Made for use in auto.
    * Drives the drivetrain for the provided number of inches on the heading it had when the command started
    * @param drivetrain the drivetain subsystem for the command to control
    * @param distance the distance, in inches, to drive
    */
   public DriveDistance(Drivetrain drivetrain, double distance) {
      this.drivetrain = drivetrain;
      this.distance = distance;
   }
   
   @Override
   public void init() {
      // get everything ready to drive (reset encoders, etc)
      drivetrain.prepareDistance();
   }
   
   @Override
   public void execute() {
      // drive the distance
      drivetrain.driveDistance(distance);
   }
   
   @Override
   public boolean isFinished() {
      // finish if we have driven the distance
      return drivetrain.atDistanceSetpoint() && drivetrain.atHeadingSetpoint() /*|| isTimedOut()*/;
   }
}