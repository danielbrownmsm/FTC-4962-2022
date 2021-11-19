package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.CRServoImplEx;

import org.firstinspires.ftc.teamcode.control.PIDController;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commandframework.Subsystem;

/**
 * A class to represent the functionality of the manipulator on the robot
 * This includes the:
 *   turntable (horizontal angle)
 *   arm (vertical angle)
 *   linear slide (distance out/in)
 *   intake (the squishy wheels at the end, CR servos)
 * and their respective sensors
 * 
 * This class includes a periodic() function, please call this function every
 * loop iteration to make the robot run optimally
 */
public class Arm extends Subsystem {
   private DcMotorImplEx turntable;
   private DcMotorImplEx arm;
   private DcMotorImplEx linearSlide;
   
   private CRServoImplEx intake1;
   private CRServoImplEx intake2;
   
   private PIDController turntable_PID;
   private PIDController arm_PID;
   private PIDController linearSlide_PID;
   
   private Telemetry telemetry;
   
   // these are here as hard limits to ensure nothing breaks too badly
   private TouchSensor linearSlideSensor; //???
   private TouchSensor intakeSensor; //???
   
   private Boolean isExtended; //???
   private Boolean hasFreight = false; //???
   private double intakePower = 0;
   
   /**
    * Creates a new Arm subsystem
    * 
    * @param map the hardware map instace provided in the opmode
    */
    //TODO maybe some kind of simulation/testing support?
   public Arm(Telemetry telemetry, HardwareMap map) {
      this.telemetry = telemetry;
      
      turntable = map.get(DcMotorImplEx.class, "turntable");
      arm = map.get(DcMotorImplEx.class, "arm");
      linearSlide = map.get(DcMotorImplEx.class, "linearSlide");
      
      intake1 = map.get(CRServoImplEx.class, "intake1");
      intake2 = map.get(CRServoImplEx.class, "intake2");
      
      linearSlideSensor = map.get(TouchSensor.class, "linearSlideSensor");
      intakeSensor = map.get(TouchSensor.class, "intakeSensor");
      
      turntable_PID = new PIDController(Constants.tablePID);
      arm_PID = new PIDController(Constants.armPID);
      linearSlide_PID = new PIDController(Constants.linearSlidePID);
      
      turntable_PID.setTolerance(3, 1);
      arm_PID.setTolerance(3, 1);
      linearSlide_PID.setTolerance(0.5, 1);

      resetEncoders();
   }
   
   public void init() {
      //DOCUMENT
      turntable_PID.reset();
      arm_PID.reset();
      linearSlide_PID.reset();
      
      turntable_PID.setSetpoint(0);
      arm_PID.setSetpoint(0);
      linearSlide_PID.setSetpoint(0);

      resetEncoders();
      
      arm.setPower(0);
      turntable.setPower(0);
      linearSlide.setPower(0);
      
      intake1.setPower(0);
      intake2.setPower(0);

      arm.resetDeviceConfigurationForOpMode();
      turntable.resetDeviceConfigurationForOpMode();
      linearSlide.resetDeviceConfigurationForOpMode();
      intake1.resetDeviceConfigurationForOpMode();
      intake2.resetDeviceConfigurationForOpMode();
   }
   
   /**
    * Sets the angle for the turntable to PID to, in degrees.
    * 0 is facing towards the front of the robot, going to -180 in one 
    * direction and 180 in the other
    * @param angle the angle for the turntable to PID to
    */
   public void rotateTurntable(double angle) {
      // a limit on angle so we don't kill our wires
      if (Math.abs(angle) > Constants.MAX_TURNTABLE) {
         angle = Constants.MAX_TURNTABLE;
      }

      turntable_PID.setSetpoint(angle);
   }
   
   //DOCUMENT
   public void incrementTurntable(double amount) {
      double newSetpoint = turntable_PID.getSetpoint() + amount;
      if (newSetpoint < Constants.MIN_TURNTABLE) {
         turntable_PID.setSetpoint(Constants.MIN_TURNTABLE);
      } else if (newSetpoint > Constants.MAX_TURNTABLE) {
         turntable_PID.setSetpoint(Constants.MAX_TURNTABLE);
      } else {
         turntable_PID.setSetpoint(newSetpoint);
      }
   }
   
   //DOCUMENT
   public boolean armAtSetpoint() {
      return arm_PID.atSetpoint();
   }
   
   /**
    * Sets the angle for the arm to PID to, in degrees
    * @param angle the angle for the arm to PID to, in degrees
    */
   public void setArm(double angle) {
      // clamp so we can't run over our arm and just wreck it (with linear slide all the way in.
      //   this won't help you if you do that)
      if (angle < Constants.MIN_ARM) {
         angle = Constants.MIN_ARM;
      } else if (angle > Constants.MAX_ARM) {
         angle = Constants.MAX_ARM;
      }

      arm_PID.setSetpoint(angle);
      
      //TEMP
      arm.setPower(arm_PID.calculate(getArmAngle()));
   }
   
   //DOCUMENT
   public void incrementArm(double amount) {
      double newSetpoint = arm_PID.getSetpoint() + amount;
      if (newSetpoint < Constants.MIN_ARM) {
         arm_PID.setSetpoint(Constants.MIN_ARM);
      } else if (newSetpoint > Constants.MAX_ARM) {
         arm_PID.setSetpoint(Constants.MAX_ARM);
      } else {
         arm_PID.setSetpoint(newSetpoint);
      }
   }
   
   //DOCUMENT
   public boolean turntableAtSetpoint() {
      return turntable_PID.atSetpoint();
   }
   
   /**
    * Sets the distance for the linear slide to PID to, in inches
    * @param dist the distance for the linear slide to PID to
    */
   public void setLinearSlide(double dist) {
      // clamping so we don't absolutely wreck ourselves
      if (dist > Constants.MAX_LINEAR_SLIDE) {
         dist = Constants.MAX_LINEAR_SLIDE;
      } else if (dist < Constants.MIN_LINEAR_SLIDE) {
         dist = Constants.MIN_LINEAR_SLIDE;
      }

      //TODO can we even have/do we even need this kind of functionality? Maybe just make it a boolean thing?
      linearSlide_PID.setSetpoint(dist);
   }
   
   //DOCUMENT
   public void incrementLinearSlide(double amount) {
      double newSetpoint = linearSlide_PID.getSetpoint() + amount;
      if (newSetpoint < Constants.MIN_LINEAR_SLIDE) {
         linearSlide_PID.setSetpoint(Constants.MIN_LINEAR_SLIDE);
      } else if (newSetpoint > Constants.MAX_LINEAR_SLIDE) {
         linearSlide_PID.setSetpoint(Constants.MAX_LINEAR_SLIDE);
      } else {
         linearSlide_PID.setSetpoint(newSetpoint);
      }
   }
    
   //DOCUMENT
   public boolean linearSlideAtSetpoint() {
      return linearSlide_PID.atSetpoint();
   }
   
   /**
    * Sets the intake to run at the specified power
    * @param power the power for the intake to run at, between -1 and 1
    */
   public void setIntake(double power) {
      //intake1.setPower(power);
      //intake2.setPower(power);
      intakePower = power;
   }

   //DOCUMENT
   public double getTurntableAngle() {
      // degrees
      return turntable.getCurrentPosition() / Constants.TICKS_PER_REV * 360;
   }

   //DOCUMENT
   public double getArmAngle() {
      // degrees
      return arm.getCurrentPosition() / Constants.TICKS_PER_REV * 360 / 4; // gear ratio of 4:1
   }

   //DOCUMENT
   public double getLinearSlideDistance() {
      // degrees
      return linearSlide.getCurrentPosition() / Constants.TICKS_PER_REV * Constants.linearSlideDiameter * Math.PI;
   }
   
   public void resetEncoders() {
      turntable.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      
      turntable.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      linearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
   }


   /**
    * The subsystem's periodic function.
    * This makes all the PIDs go to their setpoints, and keeps
    * track of sensors so we don't run into hard limits. In the
    * future maybe also does other stuff?
    */
    @Override
   public void periodic() {
      if (linearSlide.getCurrent(CurrentUnit.AMPS) > Constants.LINEAR_SLIDE_MAX_CURRENT) {
         linearSlide.setPower(0);
      } else {
         linearSlide.setPower(linearSlide_PID.calculate(getLinearSlideDistance()));
         //linearSlide.setMotorDisable();
      }

      if (turntable.getCurrent(CurrentUnit.AMPS) > Constants.TURNTABLE_MAX_CURRENT) {
         turntable.setPower(0);
         //turntable.setMotorDisable();
      } else {
         turntable.setPower(turntable_PID.calculate(getTurntableAngle()));
      }

      if (arm.getCurrent(CurrentUnit.AMPS) > Constants.ARM_MAX_CURRENT) {
         arm.setPower(0);
         //arm.setMotorDisable();
      } else {
         arm.setPower(arm_PID.calculate(getArmAngle()));
      }
      
      telemetry.addData("arm reading", getArmAngle());
      telemetry.addData("turntable reading", getTurntableAngle());
      telemetry.addData("linear slide reading", getLinearSlideDistance());
      
      telemetry.addData("arm setpoint", arm_PID.getSetpoint());
      telemetry.addData("turntable setpoint", turntable_PID.getSetpoint());
      telemetry.addData("linear slide setpoint", linearSlide_PID.getSetpoint());
      
      telemetry.addData("intake power", intakePower);

      //???
      if (!intakeSensor.isPressed()) {
         intake1.setPower(intakePower);
         intake2.setPower(-intakePower);
      }
   }
}