package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Driver - Arm (Old)", group = "24536 Code")
public class FourWheelDrive extends LinearOpMode {
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    CRServo armServo = null;
    CRServo clawL = null;
    CRServo clawR = null;

    @Override
    public void runOpMode() throws InterruptedException {
        CRServo armServo = hardwareMap.crservo.get("Arm");
        CRServo clawL = hardwareMap.crservo.get("Claw_Left");
        CRServo clawR = hardwareMap.crservo.get("Claw_Right");

        waitForStart();

        while (opModeIsActive()) {
            armServo.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
            clawL.setPower(gamepad1.left_bumper ? -0.9 : 0);
            clawR.setPower(gamepad1.left_bumper ? 0.9 : 0);
            if (gamepad1.right_bumper) {
                clawL.setPower(gamepad1.right_bumper ? 0.9 : 0);
                clawR.setPower(gamepad1.right_bumper ? -0.9 : 0);
            }
        }
    }
}
