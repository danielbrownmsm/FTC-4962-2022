package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.PIDCoefficients;
import org.opencv.core.Scalar;


public class Constants {
   // Vision constants
   public static final int CAMERA_HEIGHT = 480; // pixels
   public static final int CAMERA_WIDTH = 640;
   public static final Scalar LOWER = new Scalar(100, 0, 0); // HSV
   public static final Scalar UPPER = new Scalar(200, 255, 255);
   
   // drivetrain constants
   public static final double TICKS_PER_REV = 0;
   public static final double WHEEL_DIAMETER = 0;
   public static final double INCHES_PER_REV = Math.PI * WHEEL_DIAMETER;
   
   public static final PIDCoefficients headingPID = new PIDCoefficients(0, 0, 0);
   public static final PIDCoefficients distancePID = new PIDCoefficients(0, 0, 0);
   public static final PIDCoefficients turnPID = new PIDCoefficients(0, 0, 0);
   public static final PIDCoefficients visionPID = new PIDCoefficients(0, 0, 0);
   
   // arm constants
   public static final PIDCoefficients armPID = new PIDCoefficients(0, 0, 0);
   public static final PIDCoefficients tablePID = new PIDCoefficients(0, 0, 0);
   public static final PIDCoefficients linearSlidePID = new PIDCoefficients(0, 0, 0);
   //prob some other stuff too
   
}