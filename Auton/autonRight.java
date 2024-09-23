// autonomous program that drives bot forward a set distance, stops then
// backs up to the starting point using encoders to measure the distance.


package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Autonomous(name="Auton Right", group="Exercises")
//@Disabled
public class autonRight extends LinearOpMode
{
    DcMotor frontLeft, frontRight, backLeft, backRight;
    DcMotor rightMotor;
    DcMotor arm;

    double wheelDiameter=96/25.4; //wheelDiameter in inches
    double convertToRot(double inches){
        return (inches/(wheelDiameter*(Math.PI)))*537.6;
    }
    //convert inches to rotations

    public void move(double inches, int a, int b, int c, int d) {
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
        backLeft.setPower(0.9);
        backRight.setPower(0.9);
        frontLeft.setPower(0.9);
        frontRight.setPower(0.9);
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
        arm.setTargetPosition(arm.getCurrentPosition()+dist);
        // set motors to run to target encoder position and stop with brakes on.
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(0.25);
        // wait while opmode is active and left motor is busy running to position.


        while (opModeIsActive() && arm.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", arm.getCurrentPosition() + "  busy=" + arm.isBusy());
            telemetry.update();
            idle();
        }
        arm.setPower(0);
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
    @Override
    public void runOpMode() throws InterruptedException
    {
// Hardware Map All Motors
        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");
        arm = hardwareMap.dcMotor.get("Slider_Rotation");

        CRServo clawL=hardwareMap.crservo.get("Claw_Left");
        CRServo clawR=hardwareMap.crservo.get("Claw_Right");
        CRServo wrist=hardwareMap.crservo.get("Claw_Wrist");
        // Set Brake
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Reverse Direction Of One Side's Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);


        // reset encoder counts kept by motors.
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // set motors to run forward for 5000 encoder counts.


        telemetry.addData("Mode", "waiting");
        telemetry.update();
        waitForStart();
        telemetry.addData("Mode", "running");
        telemetry.update();

        clawL.setPower(1);
        clawR.setPower(-1);
        sleep(1000);
        wrist.setPower(-1);
        left(60.25);
        sleep(1000);
        forward(70);
        sleep(1000);
        right(28);
        forward(12);
        sleep(1000);
        moveArm(1500);
        wrist.setPower(0.3);
        sleep(400);
        clawL.setPower(-1);
        clawR.setPower(1);
        sleep(2000);
        moveArm(-1300);

        right(28);

        //release claw
    }
}

