package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

// raises the arm in auto to the correct level (1, 2, or 3) based on stored level
// maybe better to just use the no wait nevermind nah that wouldn't work
public class VisionRaiseArm extends Command {
   private final Arm arm;
   private final Vision vision;
   
   //DOCUMENT
   public VisionRaiseArm(Arm arm, Vision vision) {
      this.arm = arm;
      this.vision = vision;
   }
   
   @Override
   public void execute() {
       // set the arm to the correct height based off of what vision says we need to go to and the arm-height constants
      arm.setArm(Constants.armHeight(vision.getLevel()));
   }
   
   @Override
   public boolean isFinished() {
      return arm.atArmSetpoint();
   }
}