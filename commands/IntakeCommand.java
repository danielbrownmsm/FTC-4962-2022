package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Arm;

/**
 * An IntakeCommand command
 */
public class IntakeCommand extends Command {
   private final Arm arm;
   private double power;
   
   /**
    * Makes a new IntakeCommand command
    * sets the intake (the two CR servos at the end of the linear slide) to the given power
    * @param arm the arm subsystem for the command to control
    * @param power the speed (-1.0 to 1.0) to set the intake servos at. Positive is out, negative is in.
    */
   public IntakeCommand(Arm arm, double power) {
      this.arm = arm;
      this.power = power;
   }
   
   @Override
   public void execute() {
      // set the servos to the power
      arm.setIntake(power);
   }
   
   @Override
   public boolean isFinished() {
      return true; // we don't do anything fancy so there's no point to keep this going
   }
}