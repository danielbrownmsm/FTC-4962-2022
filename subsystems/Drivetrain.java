package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
      
      rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
      rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
      leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
      leftBack.setDirection(DcMotorSimple.Direction.FORWARD);

      //imu1 = map.get(BNO055IMU.class, "imu1");
      imu2 = map.get(BNO055IMU.class, "imu2");
      BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
      parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
      imu2.initialize(parameters);
      
      distancePID = new PIDController(Constants.distancePID);
      headingPID = new PIDController(Constants.headingPID, -180, 180, true);
      turnPID = new PIDController(Constants.turnPID, -180, 180, true);

      distancePID.setTolerance(0.75, 0.001);
      headingPID.setTolerance(3, 0.125);
      turnPID.setTolerance(10, 10);
   }
   
   // DOCUMENT
   public void init() {
      //leftFront.resetDeviceConfigurationForOpMode();
      //leftBack.resetDeviceConfigurationForOpMode();
      //rightFront.resetDeviceConfigurationForOpMode();
      //rightBack.resetDeviceConfigurationForOpMode();

      resetGyros();
      resetEncoders();
      distancePID.reset();
      headingPID.reset();
      turnPID.reset();
      
      leftFront.setPower(0);
      leftBack.setPower(0);
      rightFront.setPower(0);
      rightBack.setPower(0);
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
   public void arcadeDrive(double power, double turn) {
      leftFront.setPower(power - turn);
      leftBack.setPower(power - turn);
      rightFront.setPower(power + turn);
      rightBack.setPower(power + turn);
   }
   
   //DOCUMENT
   public void prepareTurn() {
      resetGyros();
      turnPID.reset();
      //TODO fix so turns are relative and not absolute
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
      telemetry.addData("turn setpoint", turnPID.getSetpoint());
      telemetry.addData("turn output", turnPID.calculate(getHeading()));
      arcadeDrive(0, turnPID.calculate(getHeading()));
   }
   
   public boolean simpleTurnToHeading(double angle) {
      telemetry.addData("turn setpoint", angle);
      telemetry.addData("turn output", (angle - getHeading()) * 0.005);
      
      arcadeDrive(0, (angle - getHeading()) * 0.005);
      
      if (Math.abs(angle - getHeading()) < 10) {
         arcadeDrive(0, 0);
         return true;
      }
      return false;
   }
   
   //DOCUMENT
   public void driveDistance(double distance) {
      distancePID.setSetpoint(distance);
      double power = distancePID.calculate(getAverageDistance());
      double turnPower = headingPID.calculate(getHeading());

      telemetry.addData("distance setpoint", distance);
      telemetry.addData("speed output", power);
      telemetry.addData("heading setpoint", headingPID.getSetpoint());
      telemetry.addData("turn output", turnPower);
      arcadeDrive(power, 0);
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
      //TODO use the other IMU as well
      //TODO correct for placement of hub on robot and center of rotation and all that
      return imu2.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
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
      return 
         ((leftFront.getCurrentPosition() + leftBack.getCurrentPosition()) / 2)
         / Constants.TICKS_PER_REV
         * Math.PI * Constants.WHEEL_DIAMETER;      
   };
   
   /**
    * Gets the average distance of the right-side drivetrain motor encoders, in inches
    * @return the distance, in inches, of the right side
    */
   public double getRightDistance() {
      return 
         ((rightFront.getCurrentPosition() + rightBack.getCurrentPosition()) / 2)
         / Constants.TICKS_PER_REV
         * Math.PI * Constants.WHEEL_DIAMETER;      
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
      //TODO make turning logic and math stuff work
      headingPID.setSetpoint(getHeading());
      //imu2.resetDeviceConfigurationForOpMode();
   };
   
   @Override
   public void periodic() {
      telemetry.addData("drivetrain distance", getAverageDistance());
      telemetry.addData("left distance", getLeftDistance());
      telemetry.addData("right distance", getRightDistance());
      telemetry.addData("gyro reading", getHeading());
      telemetry.addData("gyro setpoint", headingPID.getSetpoint());
   }
}