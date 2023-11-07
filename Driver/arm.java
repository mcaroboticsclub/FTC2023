// Import Required Files
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

// Send Code And Operating Mode To Game Board
@TeleOp(name = "Four wheel drive", group = "Robot")
public class FourWheelDrive extends LinearOpMode {

    // Define All Motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    CRServo armServo = null;
    CRServo clawL=null;
    CRServo clawR=null;
    @Override
    public void runOpMode() throws InterruptedException {
        
        // Hardware Map All Motors
        
        DcMotor frontLeft = hardwareMap.dcMotor.get("Left_Top");
        DcMotor frontRight = hardwareMap.dcMotor.get("Right_Top");
        DcMotor backLeft = hardwareMap.dcMotor.get("Left_Bottom");
        DcMotor backRight = hardwareMap.dcMotor.get("Right_Bottom");
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        CRServo armServo = hardwareMap.crservo.get("Arm");
        CRServo clawL=hardwareMap.crservo.get("Claw_Left");
        CRServo clawR=hardwareMap.crservo.get("Claw_Right");
        
        // Reverse Direction Of One Side's Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Only Start Code And Movement When Start Button Is Pressed
        waitForStart();
        while (opModeIsActive()) {
            
            armServo.setPower(gamepad1.left_trigger-gamepad1.right_trigger);
            clawL.setPower(gamepad1.left_bumper?-0.9:0);
            clawR.setPower(gamepad1.left_bumper?0.9:0);
            if (gamepad1.right_bumper){
                clawL.setPower(gamepad1.right_bumper?0.9:0);
                clawR.setPower(gamepad1.right_bumper?-0.9:0); 
            }

            //armServo.setPosition(2);
            frontLeft.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x)*0.45);
            backLeft.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x)*0.45);
            frontRight.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x)*0.45);
            backRight.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x)*0.45);
            telemetry.update();
            
        }
    }
}
