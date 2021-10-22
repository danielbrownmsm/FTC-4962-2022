package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.commandframework.Command;

public class SetLinearSlide extends Command {
   private final Arm arm;
   private double distance;
   
   public SetLinearSlide(Arm arm, double distance) {
      this.arm = arm;
      this.distance = distance;
   }
   
   @Override
   public void execute() {
      arm.setLinearSlide(distance);
   }
}