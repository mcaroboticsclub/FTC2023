// Import Required Files
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
//import org.firstinspires.ftc.teamcode.util.Encoder;

// Send Code And Operating Mode To Game Board
@TeleOp(name = "Full Bot Drive", group = "MCA EAGLES PROGRAMS")
public class fullBotDrive extends LinearOpMode {

    double speedFactor = 1.0;

    // Define All Motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor viperSlider = null;
    DcMotor sliderArm = null;
    DcMotor suspensionArm = null;
    Servo clawWrist = null;
    Servo clawLeft = null;
    Servo clawRight = null;
    Servo planeLauncher = null;
    //Encoder leftEncoder, rightEncoder, frontEncoder;

    @Override
    public void runOpMode() throws InterruptedException {

        // Hardware Map All Motors
        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");
        //leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "Back_Right"));
        //rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "Front_Left"));
        //frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "Front_Right"));
        viperSlider = hardwareMap.dcMotor.get("Viper_Slider");
        sliderArm = hardwareMap.dcMotor.get("Slider_Rotation");
        suspensionArm = hardwareMap.dcMotor.get("Suspension_Arm");

        // Hardware Map All Servos
        planeLauncher = hardwareMap.servo.get("Plane_Launcher");
        clawLeft = hardwareMap.servo.get("Claw_Left");
        clawRight = hardwareMap.servo.get("Claw_Right");
        clawWrist = hardwareMap.servo.get("Claw_Wrist");

        // Set The Motors To Brake
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse Direction Of One Side's Wheel Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Reverse Direction Of One Claw
        clawRight.setDirection(Servo.Direction.REVERSE);

        // Set The Slider Arm To Reset The Motor To 0 At Start And Set The Initial Target To Start Position
        sliderArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sliderArm.setTargetPosition(0);

        // Set the motor to run to the position mode. (NOTE: YOU NEED A TARGET SET BEFORE THIS MODE IS ENGAGED TO PREVENT ERROR)
        sliderArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set the arm to the speedFactor power.
        sliderArm.setPower(speedFactor);

        // Only Start Code And Movement When Start Button Is Pressed
        waitForStart();

        while (opModeIsActive()) {
            //telemetry.addData("Left pod position: ", leftEncoder.getCurrentPosition());
            //telemetry.addData("Right pod position: ", rightEncoder.getCurrentPosition());
            //telemetry.addData("Back pod position: ", frontEncoder.getCurrentPosition());
            telemetry.update();
            // Set drivetrain to move based off of the inputs of the left and right sticks of gamepad 1.
            frontLeft.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x)*0.8);
            backLeft.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x)*0.8);
            frontRight.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x)*0.8);
            backRight.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x)*0.8);

            // Set the viper slider to the current power of gamepad 2's right stick's y.
            viperSlider.setPower(gamepad2.right_stick_y);

            // If the left trigger of gamepad 2 is activated, move the arm down. Set the target position up as the current position plus the value of our left trigger times a constant and our speedFactor.
            if (gamepad2.left_trigger != 0) {
                sliderArm.setTargetPosition((int) (sliderArm.getCurrentPosition() + speedFactor * 175 * gamepad2.left_trigger));
            }

            // Also do the same thing for the right trigger but subtract instead.
            else if (gamepad2.right_trigger != 0) {
                sliderArm.setTargetPosition((int) (sliderArm.getCurrentPosition() - speedFactor * 175 * gamepad2.right_trigger));
            }

            // If the arm tries to go below the initial, set to to 0, the initial.
            if (sliderArm.getTargetPosition() < 0) {
                sliderArm.setTargetPosition(0);
            }

            // If the a button is pressed on gamepad 2, lower the suspension arm.
            if (gamepad2.a == true) {
                suspensionArm.setPower(1);
            }

            // If the b button is pressed on gamepad 2, raise the suspension arm.
            else if (gamepad2.b == true) {
                suspensionArm.setPower(-1);
            }

            // Otherwise, stop the arm.
            else {
                suspensionArm.setPower(0);
            }

            // If the dpad down button is pressed, set the plane launcher to down position and lock in.
            if (gamepad2.dpad_down == true) {
                planeLauncher.setPosition(0.6);
            }

            // If the dpad up button is pressed, set the plane launcher to up position and release plane.
            else if (gamepad2.dpad_up == true) {
                planeLauncher.setPosition(1);
            }

            // IF DOING CR SERVO
            // Check if the left stick on gamepad 2 is active and set it to the corresponding power.
            if (gamepad2.left_stick_y != 0) {
                //clawWrist.setPower(speedFactor * gamepad2.left_stick_y);
            }
            telemetry.addData("Wrist current position: ", clawWrist.getPosition());



//             IF DOING REGULAR SERVO
//            // Check if the left stick on gamepad 2 is active.
//            if (gamepad2.left_stick_y != 0) {
//
//                // Check if the position to be set is less than 0 and set it to 0.
//                if (clawWrist.getPosition() + gamepad2.left_stick_y / 1000 < 0) {
//                    clawWrist.setPosition(0);
//                }
//
//                // Check if the position is set to more than 1 and set it to 1.
//                else if (clawWrist.getPosition() + gamepad2.left_stick_y / 1000 > 1) {
//                    clawWrist.setPosition(1);
//                }
//
//                // Otherwise, set the position to the current added to the value of the stick.
//                else {
//                    clawWrist.setPosition(clawWrist.getPosition() + gamepad2.left_stick_y / 1000);
//                }
//            }

            // If the left bumper on gamepad 2 is active, set the claw to open up.
            if (gamepad2.left_bumper == true) {
                clawLeft.setPosition(0.50);
                clawRight.setPosition(0.35);
            }

            // If the right bumper on gamepad 2 is active, set the claw to close.
            else if (gamepad2.right_bumper == true) {
                clawLeft.setPosition(0.25);
                clawRight.setPosition(0.15);
            }
        }
    }
}
