package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.commandframework.Command;

/**
 * A RaiseArmCommand
 */
public class RaiseArmCommand extends Command {
   private final Arm arm;
   private double angle;
   
   /**
    * Makes a new RaiseArmCommand
    * @param arm the arm subsystem for the command to control
    * @param angle the amount by which to raise the arm, in degrees
    */
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
      // we're finished when the arm has reached the setpoint
      return arm.atArmSetpoint();
   }
}