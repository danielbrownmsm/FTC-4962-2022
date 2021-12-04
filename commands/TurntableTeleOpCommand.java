package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Arm;

//DOCUMENT
public class TurntableTeleOpCommand extends Command {
   private final Arm arm;
   private Gamepad gamepad;
   
   /**
    * @param drivetrain the drivetrain subsystem for the command to control
    * @param gamepad a gamepad instance provided by the opmode to access the sticks
    */
   public TurntableTeleOpCommand(Arm arm, Gamepad gamepad) {
      this.arm = arm;
      this.gamepad = gamepad;
   }
   
   @Override
   public void execute() {
      double power = 0;
      if (gamepad.dpad_left) {
         power = 0.3;
      } else if (gamepad.dpad_right) {
         power = -0.3;
      }
      arm.turntableTeleOp(power);
   }
   
   @Override
   public boolean isFinished() {
      return false; // never ends, should only be called in tele-op
   }
}