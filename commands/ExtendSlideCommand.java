package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.commandframework.Command;

/**
 * An ExtendSlideCommand command
 */
public class ExtendSlideCommand extends Command {
   private final Arm arm;
   private double amount;
   
   /**
    * Makes a new ExtendSlideCommand.
    * @param arm the arm subsystem for the command to control
    * @param amount the amount, in inches, to extend the linear slide of the arm. Negative is in, positive is out.
    */
   public ExtendSlideCommand(Arm arm, double amount) {
      this.arm = arm;
      this.amount = amount;
   }
   
   @Override
   public void execute() {
      // slide it out (or in) by that amount (being constrained of course by the actual length of the slide)
      arm.incrementLinearSlide(amount);
   }
   
   @Override
   public boolean isFinished() {
      // we are done if the linear slide has reached it's setpiont
      return arm.linearSlideAtSetpoint();
   }
}