package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.commandframework.Subsystem;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;


public class Vision extends Subsystem {
        private OpenCvCamera camera;
        private Telemetry telemetry;
        
        public Vision(Telemetry telemetry, HardwareMap hardwareMap) {
                this.telemetry = telemetry;
                
                WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam1");
                OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName);
        }
        
        public void init() {
                camera.openCameraDevice();
                camera.startStreaming(752, 416);
                camera.setPipeline(new RockettesPipeline());
        }
        
        @Override
        public void periodic() {
                
        }
        
        public class RockettesPipeline extends OpenCvPipeline {
                @Override
                public Mat processFrame(Mat input) {
                        /**
                         Scalar LOWER = new Scalar(100, 100, 100);
                         Scalar UPPER = new Scalar(200, 200, 200);
                         Mat mask = Mat.eye(640, 480, input.type());
                         Core.inRange(input, LOWER, UPPER, mask);
                         Core.bitwise_and(input, input, mask);
                        */
                        
                        return input;
                }
                
        }
}