package org.firstinspires.ftc.teamcode.handlers;

import org.firstinspires.ftc.teamcode.control.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Constants;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import org.openftc.easyopencv.OpenCvPipeline;


public class VisionHandler extends Publisher {
   private Telemetry telemetry;
   //private OpenCvCamera camera;
   
   public VisionHandler(Telemetry telemetry, HardwareMap map) {
      //TODO
   }
   
   public String getMessage() {
      return "";
   }
   
   public void triggerPublish() {
      publish(getMessage(), Subscriber.MsgType.VISION);
   }
   
   
   
   private class VisionPipeline extends OpenCvPipeline {
      //declare data we want to pass on here
      private double xOffset = 0;
      private double yOffset = 0;
      private int numObjects = 0; // ???
      //TODO
      
      @Override
      public Mat processFrame(Mat input) {
         //Mat gray = new Mat();
         //Imgproc.cvtColor(input, input, Imgproc.COLOR_BGR2GRAY);

         Mat mask = Mat.eye(640, 480, input.type()); // actually maybe don't do this every loop

         Core.inRange(input, Constants.LOWER, Constants.UPPER, mask); // get our mask
         Core.bitwise_and(input, input, mask); // and apply it
         //TODO actual vision processing
         
         
      
         return input; // return input (for display)
      }
   }
   
}