package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.Constants;

/**
 * A VisionRaiseArm command
 * raises the arm to the correct height to deposit the preloaded
 * freight in the right level on the hub, using the camera to detect
 * how high it needs to be
 */
public class VisionRaiseArm extends Command {
   private final Arm arm;
   private final Vision vision;

   /**
    * Makes a new VisionRaiseArm command
    * @param arm the arm subsystem to raise up
    * @param vision the vision subsystem to get the right level from
    */   
   public VisionRaiseArm(Arm arm, Vision vision) {
      this.arm = arm;
      this.vision = vision;
   }
   
   @Override
   public void execute() {
      // set the arm to the correct height based off of what vision says we need to go to and the arm-height constants
      arm.setArm(Constants.armHeight[vision.getTargetAutoLevel()]);
   }
   
   @Override
   public boolean isFinished() {
      return arm.armAtSetpoint();
   }
}