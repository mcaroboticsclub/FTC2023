// Import Required Files
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

// Send Code And Operating Mode To Game Board
@TeleOp(name = "Motors Testing + Drive", group = "24536 Codes")
public class motorsTest extends LinearOpMode {

    // Define All Motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor viperSlider = null;
    DcMotor sliderRotation = null;
    DcMotor suspensionArm = null;
    DcMotor planeLauncher = null;

    @Override
    public void runOpMode() throws InterruptedException {

        // Hardware Map All Motors
        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");
        viperSlider = hardwareMap.dcMotor.get("Viper_Slider");
        sliderRotation = hardwareMap.dcMotor.get("Slider_Rotation");
        suspensionArm = hardwareMap.dcMotor.get("Suspension_Arm");
        planeLauncher = hardwareMap.dcMotor.get("Plane_Launcher");

        // Set Brake
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        sliderRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        suspensionArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        planeLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse Direction Of One Side's Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Only Start Code And Movement When Start Button Is Pressed
        waitForStart();

        while (opModeIsActive()) {

            // Set Conditions For Activation Of Movement
            if (gamepad1.right_stick_y != 0.0 || gamepad1.right_stick_x != 0.0 || gamepad1.left_stick_x != 0.0 || gamepad1.left_stick_y != 0.0) {

                // If Joy Sticks Are Active, Move Robot
                frontLeft.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x);
                backLeft.setPower(-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x);
                frontRight.setPower(-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
                backRight.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);
            }

            // Or Else Set All To Power 0 To Stop Movement
            else {
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
            }

            // Set Viper Slider To Left Joystick Y Control
            if (gamepad2.right_stick_y != 0.0) {
                viperSlider.setPower(gamepad2.right_stick_y);
            }

            // Else Set To 0 To Stop
            else {
                viperSlider.setPower(0);
            }

            // If Right Trigger Pressed, Set To Turn Viper Down
            if (gamepad2.right_trigger != 0.0) {
                sliderRotation.setPower(-gamepad2.right_trigger);
            }

            // Else If Left Trigger Pressed, Set To Turn Viper Up
            else if (gamepad2.left_trigger != 0.0) {
                sliderRotation.setPower(gamepad2.left_trigger);
            }

            // Else Set Motor To 0
            else {
                sliderRotation.setPower(0);
            }

            // If A Pressed, Put Suspension Gear Down
            if (gamepad2.a == true) {
                suspensionArm.setPower(1);
            }

            // If B Pressed, Put Suspension Gear Up
            else if (gamepad2.b == true) {
                suspensionArm.setPower(-1);
            }

            // Else Set Power To 0
            else {
                suspensionArm.setPower(0);
            }

            // Active Plane Launcher Push With Right Bumper
            if (gamepad2.right_bumper == true) {
                planeLauncher.setPower(1);
            }

            // Activate Plane Launcher Retract With Left Bumper
            else if (gamepad2.left_bumper == true) {
                planeLauncher.setPower(-1);
            }

            // Stop Plane Launcher When None
            else {
                planeLauncher.setPower(0);
            }
        }
    }
}