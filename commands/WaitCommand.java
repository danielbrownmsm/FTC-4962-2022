package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.commandframework.Command;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

//DOCUMENT
public class WaitCommand extends Command {
   private double millis;
   private double startTime;
   
   public WaitCommand(double millis) {
      this.millis = millis;
   }
   
   @Override
   public void init() {
      startTime = System.nanoTime() / 1e6;
   }
   
   @Override
   public boolean isFinished() {
      return (System.nanoTime()/1e6 - startTime) > millis;
   }