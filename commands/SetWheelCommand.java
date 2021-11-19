package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Wheel;
import org.firstinspires.ftc.teamcode.commandframework.Command;

//DOCUMENT
public class SetWheelCommand extends Command {
   private final Wheel wheel;
   private double power;

   public SetWheelCommand(Wheel wheel, double power) {
      this.wheel = wheel;
      this.power = power;
   }
   
   @Override
   public void execute() {
      wheel.setPower(power);
   }
   
   @Override
   public boolean isFinished() {
      return true;
   }
}