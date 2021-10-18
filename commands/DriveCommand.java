package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class DriveCommand extends Command {
   private final Drivetrain drivetrain;
   private Gamepad gamepad;
   
   public DriveCommand(Drivetrain drivetrain, Gamepad gamepad) {
      this.drivetrain = drivetrain;
      this.gamepad = gamepad;
   }
   
   @Override
   public void execute() {
      drivetrain.driveTeleOp(gamepad.left_stick_y, gamepad.right_stick_y);
   }
}