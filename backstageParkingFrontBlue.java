// Import Required Files
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

// Send Code And Operating Mode To Game Board
@Autonomous(name = "backstageParkingFront", group = "24536 Code")
public class backstageParkingFrontBlue extends LinearOpMode {

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

        // Speed Factor
        double speedFactor = 1;

        // Only Start Code And Movement When Start Button Is Pressed
        waitForStart();

        while (opModeIsActive()) {

            // Move Robot To Right
            frontLeft.setPower(speedFactor);
            backLeft.setPower(-speedFactor);
            frontRight.setPower(-speedFactor);
            backRight.setPower(speedFactor);

            // Wait For A Set Amount Of Time
            sleep(500);

            // Stop All Motors
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);

            // Wait
            sleep(200);

            // Move Forward
            frontLeft.setPower(speedFactor);
            backLeft.setPower(speedFactor);
            frontRight.setPower(speedFactor);
            backRight.setPower(speedFactor);

            // Wait For Set Amount Of Time
            sleep(1500);

            // Stop All Motors
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);

            // Wait
            sleep(200);

            // Move Robot To Left
            frontLeft.setPower(-speedFactor);
            backLeft.setPower(speedFactor);
            frontRight.setPower(speedFactor);
            backRight.setPower(-speedFactor);

            // Wait For Set Amount Of Time
            sleep(700);

            // Stop All Motors
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);

            // Wait
            sleep(200);

            // Move Forward
            frontLeft.setPower(speedFactor);
            backLeft.setPower(speedFactor);
            frontRight.setPower(speedFactor);
            backRight.setPower(speedFactor);

            // Wait For Set Amount Of Time
            sleep(250);

            // Stop All Motors
            speedFactor = 0;
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }
    }
}