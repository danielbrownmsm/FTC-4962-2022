package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.commandframework.Command;

/**
 * A SetLinearSlideCommand command
 */
public class SetLinearSlide extends Command {
   private final Arm arm;
   private double distance;
   
   /**
    * Makes a new SetLinearSlideCommand.
    * @param arm the arm subsystem for the command to control
    * @param amount the amount, in inches, to set the linear slide to
    */
   public SetLinearSlide(Arm arm, double distance) {
      this.arm = arm;
      this.distance = distance;
   }
   
   @Override
   public void execute() {
      // set the linear slide to that distance
      arm.setLinearSlide(distance);
   }
   
   @Override
   public boolean isFinished() {
      // we are done when the slide is at that distance
      return arm.linearSlideAtSetpoint();
   }
}