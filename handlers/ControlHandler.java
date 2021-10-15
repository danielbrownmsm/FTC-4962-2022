package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.control.Publisher;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.control.Subscriber;

public class ControlHandler extends Publisher implements Subscriber {
   private String data;
   private Telemetry telemetry;
   
   public ControlHandler(Telemetry telemetry) {
      this.telemetry = telemetry;   
   }
   
   public void recieve(String data, Subscriber.MsgType msgType) {
      this.data = data;
   }
   
   public void notifyRockettes() {
      
   }
}