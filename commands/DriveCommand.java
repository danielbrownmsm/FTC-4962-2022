package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

/**
 * A DriveCommand command
 * made for driving in tele-op
 */
public class DriveCommand extends Command {
   private final Drivetrain drivetrain;
   private Gamepad gamepad;
   
   /**
    * Make a new DriveCommand
    * @param drivetrain the drivetrain subsystem for the command to control
    * @param gamepad a gamepad instance provided by the opmode to access the sticks
    */
   public DriveCommand(Drivetrain drivetrain, Gamepad gamepad) {
      this.drivetrain = drivetrain;
      this.gamepad = gamepad;
   }
   
   @Override
   public void execute() {
      // drives us tank-drive style, for tele-op
      //drivetrain.arcadeDrive(gamepad.left_stick_y, gamepad.left_stick_x);
      drivetrain.driveTeleOp(gamepad.left_stick_y, gamepad.right_stick_y);
   }
   
   @Override
   public boolean isFinished() {
      return false; // never ends, should only be called in tele-op
   }
}