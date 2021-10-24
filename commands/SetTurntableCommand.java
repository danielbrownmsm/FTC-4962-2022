package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.commandframework.Command;

/**
 * A SetTurntableCommand
 */
public class SetTurntableCommand extends Command {
   private final Arm arm;
   private double angle;
   
   /**
    * Makes a new SetTurntableCommand command.
    * Sets the turntable to the given angle
    * @param arm the arm subsystem for the command to control
    * @param angle the angle to set the turntable to, in degrees
    */
   public SetTurntableCommand(Arm arm, double angle) {
      this.arm = arm;
      this.angle = angle;
   }
   
   @Override
   public void execute() {
      arm.rotateTurntable(angle);
   }
   
   @Override
   public boolean isFinished() {
      // we're done when we've reached the setpoint
      return arm.turntableAtSetpoint();
   }
}