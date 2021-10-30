package org.firstinspires.ftc.teamcode.commandframework;

import java.util.ArrayList;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CommandScheduler2 {
   private static ArrayList<Command> toInit = new ArrayList<>();
   private static ArrayList<Command> toExecute = new ArrayList<>();
   private static ArrayList<Command> toRemove = new ArrayList<>();
   private static ArrayList<Subsystem> subsystems = new ArrayList<>();
   private static ArrayList<Runnable> buttons = new ArrayList<>();
   private static Telemetry telemetry;

   public CommandScheduler2(Telemetry telemetry) {
      this.telemetry = telemetry;
      // wait do we need to do anything here?
   }

   public static void registerSubsystem(Subsystem subsystem) {
      subsystems.add(subsystem);
   }
   
   public static void schedule(Command command) {
      toInit.add(command);
   }
   
   public static void cancel(Command command) {
      toInit.remove(command);
      toExecute.remove(command);
      
      command.end(false);
   }
   
   public static void addButton(Runnable button) {
      buttons.add(button);
   }
   
   public static void loop() {
      for (Subsystem subsystem : subsystems) {
         subsystem.periodic();
      }
      telemetry.addData("registered subsystems", subsystems);
      
      for (Runnable button : buttons) {
         button.run();
      }
      telemetry.addData("registered buttons", buttons);
      
      telemetry.addData("to initialize", toInit);
      for (Command command : toInit) {
         command.init();
         toExecute.add(command);
      }
      toInit.clear();
      
      telemetry.addData("to execute", toExecute);
      for (Command command : toExecute) {
         command.execute();
         
         if (command.isFinished()) {
            command.end(false);
            toRemove.add(command);
         }
      }
      
      for (Command command : toRemove) {
         toExecute.remove(command);
      }
      toRemove.clear();
   }
}