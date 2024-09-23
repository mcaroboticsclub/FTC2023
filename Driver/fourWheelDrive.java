// Import Required Files
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.*;

import com.qualcomm.robotcore.hardware.DcMotor;


import com.qualcomm.robotcore.hardware.DcMotorSimple;


// Send Code And Operating Mode To Game Board
@TeleOp(name = "Srijith Drive Code", group = "Robot")
public class fourWheelDrive extends LinearOpMode {

    // Define All Motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor arm = null;
    CRServo clawL=null;
    CRServo clawR=null;
    @Override
    public void runOpMode() throws InterruptedException {


        // Hardware Map All Motors


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


        // Only Start Code And Movement When Start Button Is Pressed
        waitForStart();
        int initial=arm.getCurrentPosition();
        int armPos=initial;
        //-152
        boolean closed=true;
        //22, 1599
        while (opModeIsActive()) {
            //arm.setPower(0.45*(gamepad2.left_trigger-gamepad2.right_trigger));
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
            clawL.setPosition(closed?-0.7:0);
            clawR.setPosition(closed?0.7:0);
            clawL.setPosition((!closed)?0.7:0);
            clawR.setPosition((!closed)?-0.7:0);

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


        }
    }
}

