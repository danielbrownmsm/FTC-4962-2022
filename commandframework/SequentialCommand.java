package org.firstinspires.ftc.teamcode.commandframework;

import java.util.ArrayList;

/**
 * A SequentialCommand class, made for when you want multiple
 * commands to execute in sequence, for instance in auto
 */
public class SequentialCommand extends Command {
   private ArrayList<Command> commands = new ArrayList<>();
   private int index = 0;
   
   /**
    * Makes a new SequentialCommand
    * @param commands an arbitrarily large list of command instances, in order
    */
   public SequentialCommand(Command... commands) {
      for (Command command : commands) {
         this.commands.add(command);
      }
   }
   
   @Override
   public void init() {
      index = 0;
      commands.get(0).init();
   }
   
   @Override
   public void execute() {
      commands.get(index).execute();

      if (commands.get(index).isFinished()) {
         commands.get(index).end(false);
         index += 1;
         if (index < commands.size()) {
            commands.get(index).init();
         }
      }
   }
   
   @Override
   public void end(boolean interrupted) {
      commands.get(index-1).end(interrupted);
   }
   
   @Override
   public boolean isFinished() {
      return index == commands.size();
   }
}