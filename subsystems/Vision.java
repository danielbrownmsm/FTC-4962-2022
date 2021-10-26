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

import org.firstinspires.ftc.teamcode.control.PIDController;
import org.firstinspires.ftc.teamcode.Constants;

/**
 * The vision-processing subsystem
 */
public class Vision extends Subsystem {
        private OpenCvCamera camera;
        private Telemetry telemetry;

        private PIDController vision_PID;
        
        private TargetHubLevelPipeline hubLevelPipeline;
        
        //DOCUMENT
        public Vision(Telemetry telemetry, HardwareMap hardwareMap) {
                this.telemetry = telemetry;
                
                WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam1");
                OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName);
                assert camera != null; // if this _is_ actually the problem and it's just not working then I have
                // absolutely no freaking idea how to fix it or why it's like this like *AAAAGGGGHHHHH* but hopefully that's not the case

                vision_PID = new PIDController(Constants.visionPID);

                hubLevelPipeline = new TargetHubLevelPipeline();
        }
        
        //DOCUMENT
        public void init() {
          camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                    camera.startStreaming(752, 416);
                    camera.setPipeline(hubLevelPipeline);
            }
        
            @Override
            public void onError(int errorCode) {
                //TODO do something here or something
            }
          });
        }

        //DOCUMENT
        public double getTurnPower() {
                // why is this even here
                return vision_PID.calculate(0); //TODO actually make work
        }

        //DOCUMENT
        public boolean isAligned() {
                return false; //TODO actually make work
                //wait why do I have this
        }

        //DOCUMENT
        public void storeTargetHubLevel() {
                // wait maybe I don't need this anymore
        }

        //DOCUMENT
        public int getTargetAutoLeveL() {
                return hubLevelPipeline.getTargetAutoLeveL();
        }

        //DOCUMENT
        public boolean hasTargetLevel() {
                return hubLevelPipeline.getTargetAutoLeveL() > -1;
        }
        
        @Override
        public void periodic() {
                // honestly we don't do much here. Everything's already called automatically with the pipeline and stuff
                //TODO telemetry
        }
        
        //DOCUMENT
        protected class TargetHubLevelPipeline extends OpenCvPipeline {
                private int targetHubLevel = -1;

                private Scalar LOWER_GREEN = new Scalar(0, 0, 0);
                private Scalar UPPER_GREEN = new Scalar(255, 255, 255);
                private double MAX_WIDTH = 200; // in pixels
                private double MAX_HEIGHT = 200;

                //private Mat mask = new Mat(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT); //TODO make this have correct type

                public int getTargetAutoLeveL() {
                        return targetHubLevel;
                }

                @Override
                public Mat processFrame(Mat input) {
                        //TODO do actual vision processing
                        /**
                         Scalar LOWER = new Scalar(100, 100, 100);
                         Scalar UPPER = new Scalar(200, 200, 200);
                         Mat mask = Mat.eye(640, 480, input.type());
                         Core.inRange(input, LOWER, UPPER, mask);
                         Core.bitwise_and(input, input, mask);
                        */

                        //TODO actually update this based on actual processing
                        targetHubLevel = -1;

                        return input;
                }
                
        }
}