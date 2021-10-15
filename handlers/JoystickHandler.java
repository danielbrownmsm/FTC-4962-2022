package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.control.*;


public class JoystickHandler extends Publisher {
   private Gamepad gamepad1;
   private Gamepad gamepad2;
   
   public JoystickHandler(Gamepad gamepad1, Gamepad gamepad2) {
      this.gamepad1 = gamepad1;
      this.gamepad2 = gamepad2;
   }
   
   public void triggerPublish() {
      publish(getMessage(), Subscriber.MsgType.JOYSTICK);
   }
   
   public String getMessage() {
      String data = toString(gamepad1.right_bumper) + "." + 
                    toString(gamepad1.left_bumper) + "." + 
                    toString(gamepad1.x) + "." + 
                    toString(gamepad1.y) + "." + 
                    toString(gamepad1.a) + "." + 
                    toString(gamepad1.b) + "." + 
                    toString(gamepad1.left_stick_button) + "." + 
                    toString(gamepad1.right_stick_button) + "." + 
                    gamepad1.left_stick_x + "." + 
                    gamepad1.right_stick_x + "." + 
                    gamepad1.left_stick_y + "." + 
                    gamepad1.right_stick_y + "." + 
                    gamepad1.left_trigger + "." + 
                    gamepad1.right_trigger;
      return data;
   }
   
   public String toString(Boolean val) {
      if (val) {
         return "1";
      }
      return "0";
   }
}