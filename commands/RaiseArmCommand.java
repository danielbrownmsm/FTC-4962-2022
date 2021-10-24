package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.commandframework.Command;

public class RaiseArmCommand extends Command {
   private final Arm arm;
   private double angle;
   
   public RaiseArmCommand(Arm arm, double angle) {
      this.arm = arm;
      this.angle = angle;
   }
   
   @Override
   public void execute() {
      arm.incrementArm(angle);
   }

   @Override
   public boolean end() {
      // so we're not constsantly running this and messing with other subsystems
      return true;
   }
}