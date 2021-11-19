package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.Constants;

//DOCUMENT
public class ArmPresetCommand extends Command {
   private final Arm arm;
   private int level;

   public ArmPresetCommand(Arm arm, int level) {
      this.arm = arm;
      this.level = level;
   }
   
   @Override
   public void execute() {
      arm.setArm(Constants.armHeight[level]);
   }
   
   @Override
   public boolean isFinished() {
      // this is more of a tele-op so this command should end immediately because periodic will take care of this for us
      return true;
   }
}