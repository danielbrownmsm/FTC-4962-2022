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
   //TODO telemetry
   private DcMotorImplEx turntable;
   private DcMotorImplEx arm;
   private DcMotorImplEx linearSlide;
   
   private CRServoImplEx intake1;
   private CRServoImplEx intake2;
   
   private PIDController turntable_PID;
   private PIDController arm_PID;
   private PIDController linearSlide_PID;
   
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
   }
   
   /**
    * Sets the angle for the turntable to PID to, in degrees.
    * 0 is facing towards the front of the robot, going to -180 in one 
    * direction and 180 in the other
    * @param angle the angle for the turntable to PID to
    */
   public void rotateTurntable(double angle) {
      //TODO continuous rotation? What about wires? Some kind of clamping?
      turntable_PID.setSetpoint(angle);
   }
   
   public void incrementTurntable(double amount) {
      turntable_PID.setSetpoint(amount + turntable_PID.getSetpoint());
   }
   
   /**
    * Sets the angle for the arm to PID to, in degrees
    * @param angle the angle for the arm to PID to, in degrees
    */
   public void setArm(double angle) {
      //TODO some kind of clamping?
      arm_PID.setSetpoint(angle);
   }
   
   /**
    * Sets the distance for the linear slide to PID to, in inches
    * @param dist the distance for the linear slide to PID to
    */
   public void setLinearSlide(double dist) {
      //TODO some kind of clamping?
      //TODO can we even have/do we even need this kind of functionality? Maybe just make it a boolean thing?
      linearSlide_PID.setSetpoint(dist);
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
   
   /**
    * The subsystem's periodic function.
    * This makes all the PIDs go to their setpoints, and keeps
    * track of sensors so we don't run into hard limits. In the
    * future maybe also does other stuff?
    */
    @Override
   public void periodic() {
      turntable.setPower(turntable_PID.calculate(turntable.getCurrentPosition(), System.nanoTime()));
      arm.setPower(arm_PID.calculate(arm.getCurrentPosition(), System.nanoTime()));
      linearSlide.setPower(linearSlide_PID.calculate(linearSlide.getCurrentPosition(), System.nanoTime()));
      
      //???
      if (!intakeSensor.isPressed()) {
         intake1.setPower(intakePower);
         intake2.setPower(intakePower);
      }
   }
}