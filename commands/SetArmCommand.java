package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.commandframework.Command;

public class SetArmCommand extends Command {
   private final Arm arm;
   private double angle;
   
   public SetArmCommand(Arm arm, double angle) {
      this.arm = arm;
      this.angle = angle;
   }
   
   @Override
   public void execute() {
      arm.setArm(angle);
   }
}