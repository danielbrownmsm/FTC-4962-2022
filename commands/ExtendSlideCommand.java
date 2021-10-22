package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.commandframework.Command;

public class ExtendSlideCommand extends Command {
   private final Arm arm;
   private double amount;
   
   public ExtendSlideCommand(Arm arm, double amount) {
      this.arm = arm;
      this.amount = amount;
   }
   
   @Override
   public void execute() {
      arm.incrementLinearSlide(amount);
   }
   
   @Override
   public boolean isFinished() {
      return true; // ???
   }
}