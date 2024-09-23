package org.firstinspires.ftc.teamcode;

import android.util.Size;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
/*
 * Test the dashboard gamepad integration.
 */
@Autonomous
public class armTestAuton extends LinearOpMode {
    DcMotor frontLeft, frontRight, backLeft, backRight;
    DcMotor suspensionArm;
    DcMotor arm;
    CRServo wrist;
    Servo clawL, clawR, plane;
    DcMotor viper;
    double wheelDiameter=96/25.4; //wheelDiameter in inches
    double convertToRot(double inches){
        return (inches/(wheelDiameter*(Math.PI)))*537.6;
    }
    //convert inches to rotations

    public void move(double inches, int a, int b, int c, int d) {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int dist = (int) convertToRot(inches);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + dist * a);
        backRight.setTargetPosition(backRight.getCurrentPosition() + dist * b);
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + dist * c);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + dist * d);
        // set motors to run to target encoder position and stop with brakes on.
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setPower(0.2);
        backRight.setPower(0.2);
        frontLeft.setPower(0.2);
        frontRight.setPower(0.2);
        // wait while opmode is active and left motor is busy running to position.


        while (opModeIsActive() && backLeft.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", backLeft.getCurrentPosition() + "  busy=" + backLeft.isBusy());
            telemetry.addData("encoder-fwd-right", backRight.getCurrentPosition() + "  busy=" + backRight.isBusy());
            telemetry.update();
            idle();
        }
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }
    public void moveWithEncoder(double e, int a, int b, int c, int d) {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int dist = (int)e;
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + dist * a);
        backRight.setTargetPosition(backRight.getCurrentPosition() + dist * b);
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + dist * c);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + dist * d);
        // set motors to run to target encoder position and stop with brakes on.
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setPower(0.2);
        backRight.setPower(0.2);
        frontLeft.setPower(0.2);
        frontRight.setPower(0.2);
        // wait while opmode is active and left motor is busy running to position.


        while (opModeIsActive() && backLeft.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", backLeft.getCurrentPosition() + "  busy=" + backLeft.isBusy());
            telemetry.addData("encoder-fwd-right", backRight.getCurrentPosition() + "  busy=" + backRight.isBusy());
            telemetry.update();
            idle();
        }
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }
    public void moveArm(int dist){
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        arm.setTargetPosition(arm.getCurrentPosition()+dist);
        // set motors to run to target encoder position and stop with brakes on.
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(0.3);
        // wait while opmode is active and left motor is busy running to position.


        while (opModeIsActive() && arm.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", arm.getCurrentPosition() + "  busy=" + arm.isBusy());
            telemetry.update();
            idle();
        }
        //arm.setPower(0);
    }
    public void moveViper(int dist){
        viper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        viper.setTargetPosition(viper.getCurrentPosition()+dist);
        // set motors to run to target encoder position and stop with brakes on.
        viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viper.setPower(0.25);
        // wait while opmode is active and left motor is busy running to position.


        while (opModeIsActive() && viper.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", viper.getCurrentPosition() + "  busy=" + viper.isBusy());
            telemetry.update();
            idle();
        }
        //arm.setPower(0);
    }
    public void forward(double dist){

        move(dist, 1,1,1,1);
    }
    public void backward(double dist){
        move(dist, -1, -1, -1, -1);
    }
    public void right(double dist){
        dist*=((double)1/0.9);
        move(dist, -1,1,1,-1);
    }
    public void left(double dist){
        dist*=((double)1/0.9);
        move(dist, 1,-1,-1,1);
    } //7,
    public void rotate(double angle){
        double val=((double)4290)*(angle/(double)360);
        moveWithEncoder(val,1,-1,1,-1);
    }
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "RedPropModel.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/RedPropModel.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    //rfwojrwifoj
    private static final String[] LABELS = {
            "Pixel",
    };

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
    public void placeBackboard(){
        clawL.setPosition(0.2);
        moveArm(800);
        moveViper(-800);
        wrist.setPower(-0.2);
        moveArm(-300);
        sleep(1000);
        //release pixel
        clawL.setPosition(0.5);
        sleep(1000);

        wrist.setPower(0.2);
        backward(3);
        clawL.setPosition(0);
        moveViper(800);
        moveArm(-800);
    }
    /*public void placeBackboard(){
        clawR.setPosition(1);
        moveArm(800);
        moveViper(-1000);
        wrist.setPower(-0.15);
        moveArm(-300);
        sleep(500);
        //release pixel
        clawR.setPosition(0.2);
        sleep(2000);
        wrist.setPower(0.2);
        clawR.setPosition(1);
        moveViper(1000);
        moveArm(-800);
    }*/
    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");
        viper = hardwareMap.dcMotor.get("Viper_Slider");

        DcMotor viperSlider = hardwareMap.dcMotor.get("Viper_Slider");
        viperSlider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm = hardwareMap.dcMotor.get("Slider_Rotation");
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        clawL=hardwareMap.servo.get("Claw_Left");
        clawR=hardwareMap.servo.get("Claw_Right");
        plane=hardwareMap.servo.get("Plane_Launcher");
        wrist=hardwareMap.crservo.get("Claw_Wrist");

        suspensionArm = hardwareMap.dcMotor.get("Suspension_Arm");
        //arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // Reverse Direction Of One Side's Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        //initTfod();
        //int propPos=findPropPos();
        // Save more CPU resources when camera is no longer needed.
        //visionPortal.close();
        //setManualExposure(myExposure, myGain);
        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();
        clawL.setPosition(0.25);
        //clawL.setPosition(0.5);
        int initial=arm.getCurrentPosition();
        int armPos=initial;
        moveArm(200);
        wrist.setPower(0.1);

        //-152
        placeBackboard();


        sleep(10000);




    }   // end runOpMode()
    private int findPropPos(){
        while (tfod.getRecognitions().size()==0) sleep(500);
        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());
        double bestX=0, bestY=0, bestConfidence=0;
        // Step through the list of recognitions and display info for each one.

        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
            double width=recognition.getRight()-recognition.getLeft();
            double confidence=recognition.getConfidence();
            if (confidence>bestConfidence){
                bestX=x;
                bestY=y;
            }
            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
            telemetry.addData("Angle to object", recognition.estimateAngleToObject(AngleUnit.DEGREES));
        }   // end for() loop0
        //int ans=1;
        int ans=1;
        if (bestX<=400) ans=0;
        else if (bestX<=1150) ans=1;
        else ans=2;

        return ans;
    }

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private static final String[] labels = {
            "RED_PROP"
    };
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load,
                // choose one of the following:
                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                //.setModelAssetName(TFOD_MODEL_ASSET)
                .setModelFileName(TFOD_MODEL_FILE)

                // The following default settings are available to un-comment and edit as needed to
                // set parameters for custom models.

                .setModelLabels(labels)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();
        tfod.setMinResultConfidence(0.6f);

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        builder.setCameraResolution(new Size(1920, 1080));
        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        //ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        //if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
        //    exposureControl.setMode(ExposureControl.Mode.Manual);
        //    sleep(50);
        //}
        //exposureControl.setExposure((long)25, TimeUnit.MILLISECONDS);
        // Set confidence threshold for TFOD recognitions, at any time.
        //tfod.setMinResultConfidence(0.75f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());
        double goalY=820;
        if (currentRecognitions.size()>=1 && gamepad2.dpad_up){
            Recognition pixel=currentRecognitions.get(0);
            double x = (pixel.getLeft() + pixel.getRight()) / 2 ;
            double y = (pixel.getTop()  + pixel.getBottom()) / 2 ;
            double width=pixel.getRight()-pixel.getLeft();
            double p=0.1*((double)(goalY-y))/goalY;
            while (!(goalY-50<=y && y<=goalY+50)){
                //magnitude based movement
                //move(dist, 1,1,1,1);
                frontLeft.setPower(p);
                frontRight.setPower(p);
                backLeft.setPower(p);
                backRight.setPower(p);

            }
        }
        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
            double width=recognition.getRight()-recognition.getLeft();

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
            telemetry.addData("Angle to object", recognition.estimateAngleToObject(AngleUnit.DEGREES));
        }   // end for() loop

    }   // end method telemetryTfod()
}
