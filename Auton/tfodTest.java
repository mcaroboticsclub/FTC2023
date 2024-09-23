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
public class tfodTest extends LinearOpMode {
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor arm = null;
    CRServo clawL=null;
    CRServo clawR=null;
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

    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");
        DcMotor viper = hardwareMap.dcMotor.get("Viper_Slider");

        //DcMotor viperSlider = hardwareMap.dcMotor.get("Viper_Slider");
        //viperSlider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        DcMotor arm = hardwareMap.dcMotor.get("Slider_Rotation");
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        Servo clawL=hardwareMap.servo.get("Claw_Left");
        Servo clawR=hardwareMap.servo.get("Claw_Right");
        Servo plane=hardwareMap.servo.get("Plane_Launcher");
        CRServo wrist=hardwareMap.crservo.get("Claw_Wrist");

        DcMotor suspensionArm = hardwareMap.dcMotor.get("Suspension_Arm");
        //arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // Reverse Direction Of One Side's Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        initTfod();
        //setManualExposure(myExposure, myGain);
        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();
        int initial=arm.getCurrentPosition();
        int armPos=initial;
        //-152
        boolean closed=false;
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                armPos+=15*(gamepad2.left_trigger-gamepad2.right_trigger);
                armPos=Math.max(initial, armPos);
                armPos=Math.min(initial+2300, armPos);
                arm.setTargetPosition(armPos);
                telemetry.addData("Cur arm pos: ", arm.getCurrentPosition());
                telemetry.addData("Armpos target val: ", armPos);
                arm.setPower(-0.01*((arm.getCurrentPosition())-armPos));
                //arm.setPower((gamepad2.left_trigger-gamepad2.right_trigger)*0.2);
                wrist.setPower(gamepad2.left_stick_y);
                viper.setPower(gamepad2.right_stick_y);
                //wrist.setPower(-1);
                //viperSlider.setPower(gamepad2.right_stick_y);
                clawL.setPosition(closed?-1:0);
                clawR.setPosition(closed?1:0);
                clawL.setPosition((!closed)?1:0);
                clawR.setPosition((!closed)?-1:0);
                if (gamepad2.left_bumper) closed=true;
                if (gamepad2.right_bumper) closed=false;
                if (gamepad2.a == true) suspensionArm.setPower(1);
                    // If B Pressed, Put Suspension Gear Up
                else if (gamepad2.b == true) suspensionArm.setPower(-1);
                    // Else Set Power To 0
                else suspensionArm.setPower(0);
                if (gamepad2.dpad_down) plane.setPosition(1.5);
                else plane.setPosition(0.6);


                frontLeft.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x)*0.8);
                backLeft.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x)*0.8);
                frontRight.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x)*0.8);
                backRight.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x)*0.8);
                telemetry.update();

                telemetryTfod();
                telemetry.addData("a","hello");
                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.

                // Share the CPU.
                sleep(20);
            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

    }   // end runOpMode()

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

