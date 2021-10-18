package org.firstinspires.ftc.teamcode.commandframework;

import java.util.ArrayList;

public class CommandScheduler {
   private ArrayList<Command> toInit = new ArrayList<>();
   private ArrayList<Command> toExecute = new ArrayList<>();
   private ArrayList<Subsystem> subsystems = new ArrayList<>();
   
   private static CommandScheduler instance;
   
   public void createInstance() {
      instance = new CommandScheduler();
   }
   
   public static CommandScheduler getInstance() {
      return instance;
   }
   
   public void registerSubsystem(Subsystem subsystem) {
      subsystems.add(subsystem);
   }
   
   public void schedule(Command command) {
      toInit.add(command);
   }
   
   public void loop() {
      for (Subsystem subsystem : subsystems) {
         subsystem.periodic();
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