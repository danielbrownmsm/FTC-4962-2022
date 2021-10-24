package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.commandframework.Command;

/**
 * An IncrementTableCommand command
 */
public class IncrementTableCommand extends Command {
   private final Arm arm;
   private double angle;
   
   /**
    * Makes a new IncrementTableCommand
    * @param arm the arm subsystem for the command to control
    * @param angle the amount to turn the table, in degrees
    */
   public IncrementTableCommand(Arm arm, double angle) {
      this.arm = arm;
      this.angle = angle;
   }
   
   @Override
   public void execute() {
      // turn the table by that much
      arm.incrementTurntable(angle);
   }
   
   @Override
   public boolean isFinished() {
      // we're done if the table has reached the setpoint
      return arm.turntableAtSetpoint();
   }
}