package org.firstinspires.ftc.teamcode;

//import org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.robot.RobotState; // agh why do all the good names already have to be used autimport sucks ASAAAAAAAAAAAAAAAAGHGHGHGHHHHHGGHGHGHGHGHGHGH
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ControlHandler extends org.firstinspires.ftc.teamcode.control.Node {
   private String data;
   private Telemetry telemetry;
   
   public ControlHandler(Telemetry telemetry) {
      this.telemetry = telemetry;
      topics.add("hardware"); // sub to hardware topic
   }
   
   public void give(String topic, String message) {
      RobotState state = RobotState.extract(message);
      
   }
   
   // essentially main loop of the node
   public void spin() {
      
   }
}