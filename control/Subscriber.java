package org.firstinspires.ftc.teamcode.control;


public interface Subscriber {
   public enum MsgType {
      CONTROL, HARDWARE, VISION, STATE, AUTO, PATH, JOYSTICK, OTHER
   }
   public void recieve(String data, MsgType msgType);
   public void notifyRockettes();
}