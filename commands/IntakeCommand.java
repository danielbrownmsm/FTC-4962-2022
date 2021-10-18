package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class IntakeCommand extends Command {
   private final Arm arm;
   private double power;
   
   public IntakeCommand(Arm arm, double power) {
      this.arm = arm;
      this.power = power;
   }
   
   @Override
   public void execute() {
      arm.setIntake(power);
   }
}