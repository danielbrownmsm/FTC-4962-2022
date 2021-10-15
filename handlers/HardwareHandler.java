package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.*; // for Arm and Drivetrain
import org.firstinspires.ftc.teamcode.control.*; // for Publisher, Subscriber

public class HardwareHandler extends Publisher implements Subscriber {
   private String data;
   
   // motors - drivetrain, arm, turntable
   // servos - intake, linear slide?
   // sensors - idk what sensors we want to use
   // camera - camera? that prob need be in VisionHandler
   private Drivetrain drivetrain; // drivetrain
   private Arm arm; // turntable, up/down, linear slide, and intake
   
   public HardwareHandler(Telemetry telemetry, HardwareMap map) {
      drivetrain = new Drivetrain(map);
      arm = new Arm(map);
   }
   
   public String getMessage() {
      return "";
   }
   
   public void triggerPublish() {
      publish(getMessage(), Subscriber.MsgType.HARDWARE);
   }
   
   public void recieve(String data, Subscriber.MsgType msgType) {
      this.data = data;
   }
   
   public void notifyRockettes() {
      
   }
}