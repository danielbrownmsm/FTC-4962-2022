package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

/**
 * A TurnToHeadingCommand command
 * Turns the drivetrain to the given heading, made for auto
 */
public class TurnHeadingCommand extends Command {
   private final Drivetrain drivetrain;
   private double heading;
   
   /**
    * Makes a new TurnHeadingCommand command
    * Turns the drivetrain by the specified heading or something. Made for use in auto
    * @param drivetrain the drivetrain subsystem for the command to control
    * @param heading the heading to turn the drivetrain to (or maybe just the angle to turn it by? idk)
    */
   public TurnHeadingCommand(Drivetrain drivetrain, double heading) {
      this.drivetrain = drivetrain;
      this.heading = heading;
   }
   
   @Override
   public void init() {
      // set us up to turn
      drivetrain.prepareTurn();
   }
   
   @Override
   public void execute() {
      drivetrain.turnToHeading(heading);
   }
   
   @Override
   public boolean isFinished() {
      // we're done once we've reached the setpoint
      return drivetrain.atHeadingSetpoint() /*|| isTimedOut()*/;
   }
}