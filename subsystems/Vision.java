package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.commandframework.Subsystem;

import java.util.List;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
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
                camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName);
                
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
        public int getTargetAutoLevel() {
                return hubLevelPipeline.getTargetAutoLevel();
        }

        //DOCUMENT
        public boolean hasTargetLevel() {
                return hubLevelPipeline.getTargetAutoLevel() > -1;
        }
        
        @Override
        public void periodic() {
                // honestly we don't do much here. Everything's already called automatically with the pipeline and stuff
                //TODO telemetry
        }
        
        //DOCUMENT
        protected class TargetHubLevelPipeline extends OpenCvPipeline {
            boolean viewportPaused = false;
            Mat processed = new Mat(Constants.CAMERA_HEIGHT, Constants.CAMERA_WIDTH, 24);
            Mat mask = new Mat(Constants.CAMERA_HEIGHT, Constants.CAMERA_WIDTH, 24);
            Mat hierarchy = new Mat();
            List<MatOfPoint> contours = new ArrayList<>();
            Scalar LOWER = new Scalar(130, 110, 155);
            Scalar UPPER = new Scalar(140, 255, 255);
            Scalar color = new Scalar(0, 255, 0);
            Mat kernel = new Mat();
            Size size = new Size(3, 3);
            double maxVal = 0;
            int maxValId = 0;
            int targetHubAutoLevel = -1;
            
            public int getTargetAutoLevel() {
                return targetHubAutoLevel;
            }
    
            @Override
            public Mat processFrame(Mat input)
            {
                Imgproc.cvtColor(input, processed, Imgproc.COLOR_BGR2HSV);
                //Imgproc.blur(processed, processed, size);
                Core.inRange(processed, LOWER, UPPER, mask);
                Imgproc.cvtColor(processed, processed, Imgproc.COLOR_HSV2BGR);
                Imgproc.erode(mask, mask, kernel);
                Core.bitwise_and(processed, processed, input, mask);
                
                Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
                
                maxVal = 0;
                maxValId = 0;
                for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
                    //Imgproc.drawContours(processed, contours, contourIdx, color);
                    Rect rect = Imgproc.boundingRect(contours.get(contourIdx));
                    double area = rect.width * rect.height;
                    
                    if ((rect.height - rect.width * 1.2) < 50) {
                        //if (rect.y > 200) {
                            if (area > maxVal) {
                                maxVal = area;
                                maxValId = contourIdx;
                            }
                        //}
                    }
                }
                if (maxValId > 0 && contours.size() > 0 && maxValId < contours.size()) {
                    Rect largestRect = Imgproc.boundingRect(contours.get(maxValId));
                    Imgproc.rectangle(processed, largestRect, color, 5);
                    
                    if (largestRect.x > 530) {
                        targetHubAutoLevel = 3;
                    } else if (400 < largestRect.x && largestRect.x < 530) {
                        targetHubAutoLevel = 2;
                    } else if (100 < largestRect.x && largestRect.x < 300) {
                        targetHubAutoLevel = 1;
                    } else {
                        // set it to -1 here? but like flickering between -1 and actual?
                    }
                }
                contours.clear();
                //hierarchy.clear();
            
                return processed;
        }
    }
}