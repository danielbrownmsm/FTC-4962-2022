package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Arm;


@TeleOp(name="Robot Characterization")
public class RobotCharacterizationTeleOp extends OpMode {
   private Drivetrain drivetrain = new Drivetrain(telemetry, hardwareMap);
   private long startTime;
   private long currTime;
   private final long minTime = 100; //???
   
   
   /** DC motors follow equation
    * V = kS * sign(d') + kV * d' + kA * d''
    * where V is applied voltage, d is displacement, d' is velocity, and d'' is acceleration
    * kS is voltage needed to overcome static friction, no matter speed
    * kV is voltage needed to hold/cruise at a constant velocity
    * kA is voltage needed to induce a given acceleration to the shaft
    * most of these things are pretty much linear for FRC motors, hopefully this applies to FTC too
    * handlers
    * so that can be used for the drivetrain and such
    * Arms follow
    * V = kS * sign(theta) + kCos * cos(theta) + kV * theta' + kA * theta''
    * where theta is angular displacement and kCos corrects for gravity
    * taken from WPILib robot characterization docs
    * TODO add permalink here
    */
   
   @Override
   public void init() {
      //drivetrain.init();
   }
   
   @Override
   public void init_loop() {
   }
   
   @Override
   public void loop() {
      //drivetrain.driveTeleOp(voltage, voltage); // not actual voltage AGH this SUCKS
   }
   
   @Override
   public void stop() {
      
   }
}