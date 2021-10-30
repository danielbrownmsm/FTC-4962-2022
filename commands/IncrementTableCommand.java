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
      // set the setpoint to increment by that much
      arm.incrementTurntable(angle);
   }
   
   @Override
   public boolean isFinished() {
      // only need to run this once because the periodic() handles power calculations
      return true;
   }
}