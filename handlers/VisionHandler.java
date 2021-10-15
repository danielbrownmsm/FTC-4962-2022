package org.firstinspires.ftc.teamcode.handlers;

import org.firstinspires.ftc.teamcode.control.*;
import org.firstinspires.ftc.teamcode.Constants;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import org.openftc.easyopencv.OpenCvPipeline;


public class VisionHandler extends Publisher {
   private class VisionPipeline extends OpenCvPipeline {
      //declare data we want to pass on here
      private double xOffset = 0;
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
   
   public VisionHandler() {
      //TODO figure this out cause like idk
   }
   
   public String getMessage() {
      return "";
   }
   
   public void triggerPublish() {
      publish(getMessage(), Subscriber.MsgType.VISION);
   }
}