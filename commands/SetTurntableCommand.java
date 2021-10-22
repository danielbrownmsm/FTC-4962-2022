package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.commandframework.Command;

public class SetTurntableCommand extends Command {
   private final Arm arm;
   private double angle;
   
   public SetTurntableCommand(Arm arm, double angle) {
      this.arm = arm;
      this.angle = angle;
   }
   
   @Override
   public void execute() {
      arm.rotateTurntable(angle);
   }
}