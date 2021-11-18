package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServoImplEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commandframework.Subsystem;

public class Wheel extends Subsystem {
    private CRServoImplEx wheel;
    private Telemetry telemetry;
    
    public Wheel(Telemetry telemetry, HardwareMap map) {
        this.telemetry = telemetry;
        
        wheel = map.get(CRServoImplEx.class, "wheel");
    }
    
    public void init() {
        wheel.setPower(0);
    }
    
    public void setPower(double power) {
        wheel.setPower(power);
    }
    
    @Override
    public void periodic() {
        
    }
}