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
   
   private OpenCvCamera phoneCam;
   
   private Boolean enoughTimeHasPassed = true; //TODO actually do code
   
   //private PathHandler path;
   //private AutoHandler auto;
   private JoystickHandler joystick;
   //private TelemetryHandler telem;
   
   @Override
   public void init() {
      //int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
      //phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
      //phoneCam.setPipeline(new SamplePipeline());
      
      
      //telem = new TelemetryHandler(telemetry);
      //control.onPublish(telem.handleControl);
      //hardware.onPublish(telem.handleHardware);
      //vision.onPublish(telem.handleVision);
      //joystick.onPublish(telem.handleJoystick);
       
      control = new ControlHandler(telemetry);
      hardware = new HardwareHandler(telemetry, hardwareMap);
      //vision = new VisionHandler(camera);
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