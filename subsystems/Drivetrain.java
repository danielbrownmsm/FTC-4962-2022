package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotorImpl;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.control.PIDController;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commandframework.Subsystem;


/**
 * A class to represent the functionality of the drivetraion of the robot
 * This includes the:
 *   4 drivetrain motors
 *   4 drivetrain encoders
 *   2 integrated (into REV hub) IMUs
 * 
 * This class includes a periodic() function, please call this function every
 * loop iteration to make the robot run optimally
 */
public class Drivetrain extends Subsystem {
   private DcMotorImplEx leftFront;
   private DcMotorImplEx leftBack;
   private DcMotorImplEx rightFront;
   private DcMotorImplEx rightBack;
   
   // 1 imu per expansion/control hub, for a total of 2
   private BNO055IMU imu1;
   private BNO055IMU imu2;
   
   //private BNO055Parameters parameters;
   
   private PIDController distancePID;
   private PIDController headingPID;
   private PIDController turnPID;
   
   //Pure-pursuit? idk what to do man
   
   private Telemetry telemetry;

   
   /**
    * Creates a new Drivetrain subsystem
    * 
    * @param map the hardware map instace provided in the opmode
    * @param telemetry the telemetry instance provided in the opmode
    */
    //TODO maybe some kind of simulation/testing support?
   public Drivetrain(Telemetry telemetry, HardwareMap map) {
      this.telemetry = telemetry;
      
      leftFront = map.get(DcMotorImplEx.class, "leftFront");
      leftBack = map.get(DcMotorImplEx.class, "leftBack");
      rightFront = map.get(DcMotorImplEx.class, "rightFront");
      rightBack = map.get(DcMotorImplEx.class, "rightBack");

      imu1 = map.get(BNO055IMU.class, "imu1");
      imu2 = map.get(BNO055IMU.class, "imu2");
      
      distancePID = new PIDController(Constants.distancePID);
      headingPID = new PIDController(Constants.headingPID);
      turnPID = new PIDController(Constants.turnPID);
   }
   
   // DOCUMENT
   public void init() {
      resetGyro();
      resetEncoders();
      distancePID.reset();
      headingPID.reset();
      turnPID.reset();
      // idk what else
      //TODO make this like zero all the sensors and calibrate and stuff
   }
   
   /**
    * Drives the robot tank-drive style, for use in tele-op
    * @param leftPower the speed for the left side of the drivetrain
    * @param rightPower the speed for the right side of the drivetrain
    */
   public void driveTeleOp(double leftPower, double rightPower) {
      leftFront.setPower(leftPower);
      leftBack.setPower(leftPower);
      rightFront.setPower(rightPower);
      rightBack.setPower(rightPower);
   }
   
   //DOCUMENT
   //TODO make actually work
   public void arcadeDrive(double power, double turn) {
      leftFront.setPower(power - turn);
      leftBack.setPower(power - turn);
      rightFront.setPower(power + turn);
      rightBack.setPower(power + turn);
   }
   
   // ???
   public void prepareTurn() {
      resetGyros();
      turnPID.reset();
   }
   
   //DOCUMENT
   public void prepareDistance() {
      resetEncoders();
      resetGyros();
      distancePID.reset();
      headingPID.reset();
      headingPID.setSetpoint(getHeading());
   }
   
   //DOCUMENT
   public void turnToHeading(double angle) {
      turnPID.setSetpoint(angle);
      arcadeDrive(0, turnPID.calculate(getHeading(), System.nanoTime()));
   }
   
   //TODO make this use inches and stuff
   public void driveDistance(double distance) {
      distancePID.setSetpoint(distance);
      arcadeDrive(distancePID.calculate(getAverageDistance(), System.nanoTime()), 
                  headingPID.calculate(getHeading(), System.nanoTime()));
   }
   
   //DOCUMENT
   public boolean atHeadingSetpoint() {
      return headingPID.atSetpoint();
   }
   
   //DOCUMENT
   public boolean atDistanceSetpoint() {
      return distancePID.atSetpoint();
   }
   
   //DOCUMENT
   public boolean atTurnSetpoint() {
      return turnPID.atSetpoint();
   }
   
   /**
    * Gets the heading of the robot, in degrees, from 0-360 
    * @return the heading of the robot
    */
   public double getHeading() {
      //TODO fuse this and make it more accurate
      //TODO use the other IMU
      //TODO correct for placement of hub on robot and center of rotation and all that
      return imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
   };
   
   /**
    * Gets the average total distance, in inches, the drivetrain has travelled
    * since last encoder reset
    * @return the average total drivetrain distance
    */
   public double getAverageDistance() {
      return (getLeftDistance() + getRightDistance()) / 2;
   };
   
   /**
    * Gets the average distance of the left-side drivetrain motor encoders, in inches
    * @return the distance, in inches, of the left side
    */
   public double getLeftDistance() {
      return (leftFront.getCurrentPosition() + leftBack.getCurrentPosition()) / 2;      
   };
   
   /**
    * Gets the average distance of the right-side drivetrain motor encoders, in inches
    * @return the distance, in inches, of the right side
    */
   public double getRightDistance() {
      return (rightFront.getCurrentPosition() + rightBack.getCurrentPosition()) / 2;      
   };
   
   /**
    * Resets (zeros) the drivetrain encoders
    */
   public void resetEncoders() {
      leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      
      leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
   };
   
   /**
    * Resets (zeros) the gyro. Do we even need this? Yeah kinda I guess...
    */
   public void resetGyros() {
      //TODO
      //imu1.reset();
      //imu2.reset();
      //???
   };
   
   @Override
   public void periodic() {
      //TODO telemetry stuff here
   }
}