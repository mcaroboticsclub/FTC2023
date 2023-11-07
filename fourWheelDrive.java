// Import Required Files
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

// Send Code And Operating Mode To Game Board
@TeleOp(name = "Robot Program", group = "24536 Codes")
public class fourWheelDrive extends LinearOpMode {

    // Define All Motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;

    @Override
    public void runOpMode() throws InterruptedException {

        // Hardware Map All Motors
        DcMotor frontLeft = hardwareMap.dcMotor.get("Left_Top");
        DcMotor frontRight = hardwareMap.dcMotor.get("Right_Top");
        DcMotor backLeft = hardwareMap.dcMotor.get("Left_Bottom");
        DcMotor backRight = hardwareMap.dcMotor.get("Right_Bottom");

        // Set Brake
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse Direction Of One Side's Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Only Start Code And Movement When Start Button Is Pressed
        waitForStart();

        while (opModeIsActive()) {

            // Gamepad 1 Controls Movement

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
        }
    }
}