package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.handlers.*;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

@TeleOp(name="test tele op")
public class RockettesTeleOp extends OpMode {
   private HardwareHandler hardware;
   private ControlHandler control;
   private VisionHandler vision;
   
   private Boolean enoughTimeHasPassed = true; //TODO actually do code
   
   // Thanks SO, for this and so much more. Should be the amount of memory we have left until an OOM error
   private long presumableFreeMemory = Runtime.getRuntime().maxMemory() - (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
   
   //private PathHandler path;
   //private AutoHandler auto;
   private JoystickHandler joystick;

   @Override
   public void init() {
      //int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
      //phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
      //phoneCam.setPipeline(new SamplePipeline());
       
      control = new ControlHandler(telemetry);
      hardware = new HardwareHandler(telemetry, hardwareMap);
      vision = new VisionHandler(telemetry, hardwareMap);
      joystick = new JoystickHandler(gamepad1, gamepad2); // ??? might be better just to slap a class in this file honestly

      joystick.addCallback(hardware); // harware subs to joystick

      hardware.addCallback(control); // control subs to hardware
      control.addCallback(hardware); // hardware subs to control
   }
   
   @Override
   public void loop() {
      if (enoughTimeHasPassed) {
         //joystick.publish(gamepad1, gamepad2);
         //control.publish();
         joystick.triggerPublish();
         hardware.triggerPublish();
      }
   }
}