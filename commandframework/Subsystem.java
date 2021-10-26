package org.firstinspires.ftc.teamcode.commandframework;

/** A class that represents a subsytem.
 * Pretty much only here so we can call periodic()
 */
public class Subsystem {
   public Subsystem() {
         CommandScheduler.getInstance().registerSubsystem(this);
   }
   
   /**
    * A method that will be called every loop iteration
    * Override and put basic things that need to happen every loop 
    * like telemetry or position PIDs in here
    */
   public void periodic() {
      
   }
}
