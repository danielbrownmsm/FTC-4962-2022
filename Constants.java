package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.PIDCoefficients;
import org.opencv.core.Scalar;


public class Constants {
   // Vision constants
   public static final int CAMERA_HEIGHT = 752; // pixels
   public static final int CAMERA_WIDTH = 416;
   public static final Scalar LOWER = new Scalar(100, 0, 0); // HSV
   public static final Scalar UPPER = new Scalar(200, 255, 255);
   
   // drivetrain constants
   public static final double TICKS_PER_REV = 537.7;
   public static final double WHEEL_DIAMETER = 3.77953;
   public static final double INCHES_PER_REV = Math.PI * WHEEL_DIAMETER;
   
   public static final PIDCoefficients headingPID = new PIDCoefficients(0.07, 0, 0.05);
   public static final PIDCoefficients distancePID = new PIDCoefficients(0.2, 0, 1);
   public static final PIDCoefficients turnPID = new PIDCoefficients(0.2, 0, 0.01);
   public static final PIDCoefficients visionPID = new PIDCoefficients(0, 0, 0);
   
   // arm constants
   public static final PIDCoefficients armPID = new PIDCoefficients(0.02, 0.0001, 0.01);
   public static final PIDCoefficients tablePID = new PIDCoefficients(0.01, 0, 0.01);
   public static final PIDCoefficients linearSlidePID = new PIDCoefficients(0.03, 0, 0.001);

   public static final double linearSlideDiameter = 3; // diameter of linear slide spool thingy, in inches
   public static final double armHeight[] = {5, 10, 30, 50}; // in degrees, from -1 to 3
   
   public static final double MAX_ARM = 90;
   public static final double MIN_ARM = -10;
   public static final double MAX_TURNTABLE = 100;
   public static final double MIN_TURNTABLE = -100;
   public static final double MAX_LINEAR_SLIDE = 0;
   public static final double MIN_LINEAR_SLIDE = -14;

   public static final double LINEAR_SLIDE_MAX_CURRENT = 2;
   public static final double TURNTABLE_MAX_CURRENT = 2;
   public static final double ARM_MAX_CURRENT = 2;
}