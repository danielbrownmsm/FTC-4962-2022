package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

//TODO why the heck does this exist (yes, I know I created it but still)
// what was I going to use this for again?
public class VisionAlignCommand extends Command {
   private final Drivetrain drivetrain;
   private final Vision vision;
   
   //DOCUMENT
   public VisionAlignCommand(Drivetrain drivetrain, Vision vision) {
      this.drivetrain = drivetrain;
      this.vision = vision;
   }
   
   @Override
   public void execute() {
      drivetrain.arcadeDrive(0, vision.getTurnPower());
   }
   
   @Override
   public boolean isFinished() {
      return vision.isAligned();
   }
}