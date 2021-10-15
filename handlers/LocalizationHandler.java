package org.firstinspires.ftc.teamcode.handlers;

import org.firstinspires.ftc.teamcode.control.*;

public class LocalizationHandler extends Publisher implements Subscriber {
   public LocalizationHandler() {
      
   }
   
   public void recieve(String data, Subscriber.MsgType msgType) {
      if (msgType == Subscriber.MsgType.HARDWARE) {
         publish(getMessage(), Subscriber.MsgType.STATE);
      }
      
   }
   
   public String getMessage() {
      return "";
   }
   
   public void notifyRockettes() {
      
   }
}