package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class TurnHeadingCommand extends Command {
   private final Drivetrain drivetrain;
   private double heading;
   
   public TurnHeadingCommand(Drivetrain drivetrain, double heading) {
      this.drivetrain = drivetrain;
      this.heading = heading;
   }
   
   @Override
   public void init() {
      drivetrain.prepareTurn();
   }
   
   @Override
   public void execute() {
      drivetrain.turnToHeading(heading);
   }
   
   @Override
   public boolean isFinished() {
      return drivetrain.atHeadingSetpoint() /*|| isTimedOut()*/;
   }
}