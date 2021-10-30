package org.firstinspires.ftc.teamcode.commandframework;

import java.util.ArrayList;

public class SequentialCommand extends Command {
   private ArrayList<Command> commands = new ArrayList<>();
   private int index = 0;
   
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
      Command currCommand = commands.get(index);
      
      currCommand.execute();
      
      if (currCommand.isFinished()) {
         index += 1;
         if (index < commands.size()) {
            commands.get(index).init();
         }
      }
   }
   
   @Override
   public void end(boolean interrupted) {
      commands.get(index).end(interrupted);
   }
   
   @Override
   public boolean isFinished() {
      return index == commands.size();
   }
}
