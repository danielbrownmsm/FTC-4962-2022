package org.firstinspires.ftc.teamcode.commandframework;

import java.util.ArrayList;

public class CommandScheduler {
   private ArrayList<Command> toInit = new ArrayList<>();
   private ArrayList<Command> toExecute = new ArrayList<>();
   private ArrayList<Subsystem> subsystems = new ArrayList<>();
   private ArrayList<Runnable> buttons = new ArrayList<>();
   
   private static CommandScheduler instance;
   
   public static CommandScheduler getInstance() {
      if (instance == null) {
         instance = new CommandScheduler();
      }
      
      return instance;
   }
   
   public void registerSubsystem(Subsystem subsystem) {
      subsystems.add(subsystem);
   }
   
   public void schedule(Command command) {
      toInit.add(command);
   }
   
   public void cancel(Command command) {
      toInit.remove(command);
      toExecute.remove(command);
      
      command.end(false);
   }
   
   public void addButton(Runnable button) {
      buttons.add(button);
   }
   
   public void loop() {
      for (Subsystem subsystem : subsystems) {
         subsystem.periodic();
      }
      
      for (Runnable button : buttons) {
         button.run();
      }
      
      for (Command command : toInit) {
         command.init();
         toExecute.add(command);
      }
      toInit.clear();
      
      for (Command command : toExecute) {
         command.execute();
         
         if (command.isFinished()) {
            command.end(false);
            toExecute.remove(command);
         }
      }
   }
}