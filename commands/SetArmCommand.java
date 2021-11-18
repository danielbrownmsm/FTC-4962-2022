package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.commandframework.Command;

/**
 * A SetArmCommand, mainly for use in auto
 */
public class SetArmCommand extends Command {
   private final Arm arm;
   private double angle;

   /**
    * Makes a new SetArmCommand
    * @param arm the arm subsystem for the command to control
    * @param angle the angle to set the arm to, in degrees
    */
   public SetArmCommand(Arm arm, double angle) {
      this.arm = arm;
      this.angle = angle;
   }
   
   @Override
   public void execute() {
      arm.setArm(angle);
   }
   
   @Override
   public boolean isFinished() {
      // we're done when the arm has reached the setpoint
      return arm.armAtSetpoint();
   }
}