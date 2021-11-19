package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServoImplEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commandframework.Subsystem;

//DOCUMENT
public class Wheel extends Subsystem {
    private CRServoImplEx wheel;
    private Telemetry telemetry;
    
    //DOCUMENT
    public Wheel(Telemetry telemetry, HardwareMap map) {
        this.telemetry = telemetry;
        
        wheel = map.get(CRServoImplEx.class, "wheel");
    }
    
    //DOCUMENT
    public void init() {
        wheel.setPower(0);
    }
    
    //DOCUMENT
    public void setPower(double power) {
        wheel.setPower(power);
    }
    
    @Override
    public void periodic() {
        
    }
}