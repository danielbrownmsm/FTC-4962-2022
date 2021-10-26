package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

//TODO why the heck does this exist (yes, I know I created it but still)
// what was I going to use this for again?
public class VisionStoreLevel extends Command {
   private final Vision vision;
   
   public VisionStoreLevel(Vision vision) {
      this.vision = vision;
   }
   
   @Override
   public void execute() {
      vision.storeTargetHubLevel();
   }
   
   @Override
   public boolean isFinished() {
      return vision.hasTargetLevel();
   }
}