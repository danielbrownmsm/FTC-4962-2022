package org.firstinspires.ftc.teamcode.commandframework;

import java.util.ArrayList;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The Command Scheduler class
 * Inspired by WPILib's/FTCLib's implementations, but much simpler
 * This is the class in charge of actually making stuff happen in
 * the command-based framework
 */
public class CommandScheduler2 {
   private static ArrayList<Command> toInit = new ArrayList<>();
   private static ArrayList<Command> toExecute = new ArrayList<>();
   private static ArrayList<Command> toRemove = new ArrayList<>();
   private static ArrayList<Subsystem> subsystems = new ArrayList<>();
   private static ArrayList<Runnable> buttons = new ArrayList<>();
   private static Telemetry telemetry;

   // actually why do I even have this?
   public CommandScheduler2(Telemetry telemetry) {
      this.telemetry = telemetry;
   }
   
   /**
    * Resets the CommandScheduler to its initial state and adds a
    * telemetry instance so the CommandScheduler can add telemetry data
    * @param telem the telemetry instance
    */
   public static void init(Telemetry telem) {
      telemetry = telem;
      toInit.clear();
      toExecute.clear();
      toRemove.clear();
      subsystems.clear();
      buttons.clear();
   }

   /**
    * adds a telemetry instance so the CommandScheduler can add telemetry data
    * @param telem the telemetry instance
    */   
   public static void addTelemetry(Telemetry telem) {
      telemetry = telem;
   }

   /**
    * Registers a subsystem to have its periodic() called each iteration
    * @param subsystem the subsystem to register
    */
   public static void registerSubsystem(Subsystem subsystem) {
      subsystems.add(subsystem);
   }
   
   /**
    * Schedules the given command for execution
    * @param command the command to schedule
    */
   public static void schedule(Command command) {
      toInit.add(command);
   }
   
   /**
    * Cancels the given command, removing it from execution
    * @param command the command to cancel
    */
   public static void cancel(Command command) {
      toInit.remove(command);
      toExecute.remove(command);
      
      command.end(false);
   }
   
   /**
    * Registers a button to be polled for commands each iteration
    * @param button the button to add
    */
   public static void addButton(Runnable button) {
      buttons.add(button);
   }
   
   /**
    * The actual loop method that makes everything tick
    * Calls all periodic()s, initializes commands, executes commands,
    * ends and removes finished commands, adds telemetry, the whole shebang
    */
   public static void loop() {
      // for each subsystem
      for (Subsystem subsystem : subsystems) {
         // call the periodic of that subsystem
         subsystem.periodic();
      }
      telemetry.addData("registered subsystems", subsystems);
      
      // for each button that we have registered
      for (Runnable button : buttons) {
         // run the button (this polls the button and makes it schedule or cancel commands as specified)
         button.run();
      }
      telemetry.addData("registered buttons", buttons);
      
      // initialize all the commands that we need to initialize
      telemetry.addData("to initialize", toInit);
      for (Command command : toInit) {
         command.init();
         toExecute.add(command); // and then add them to be executed
      }
      toInit.clear(); // since they've all been initialized, clear the list
      
      // now execute all the commands we need to execute
      telemetry.addData("to execute", toExecute);
      for (Command command : toExecute) {
         command.execute();
         
         // if a command is finished
         if (command.isFinished()) {
            command.end(false); // end it
            toRemove.add(command); // mark it to be removed
         }
      }
      
      // for each of the commands that we need to remove
      for (Command command : toRemove) {
         toExecute.remove(command); // remove them
      }
      toRemove.clear(); // and clear the list now that all of them have been removed
   }
}