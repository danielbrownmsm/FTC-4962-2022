package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class DriveDistance extends Command {
   private final Drivetrain drivetrain;
   private double distance;
   
   public DriveDistance(Drivetrain drivetrain, double distance) {
      this.drivetrain = drivetrain;
      this.distance = distance;
   }
   
   @Override
   public void init() {
      drivetrain.prepareDistance();
   }
   
   @Override
   public void execute() {
      drivetrain.driveDistance(distance);
   }
   
   @Override
   public boolean isFinished() {
      return drivetrain.atDistanceSetpoint() /*|| isTimedOut()*/;
   }
}