// autonomous program that drives bot forward a set distance, stops then
// backs up to the starting point using encoders to measure the distance.

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Backstage Parking", group = "Exercises")

public class backstageParkingFrontBlueENC extends LinearOpMode
{
    DcMotor leftMotor;
    DcMotor rightMotor;

    @Override
    public void runOpMode() throws InterruptedException
    {
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

        // reset encoder counts kept by motors.
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // set motors to run forward for 5000 encoder counts.
        backLeft.setTargetPosition(5000);
        backRight.setTargetPosition(5000);
        frontLeft.setTargetPosition(5000);
        frontRight.setTargetPosition(5000);
        // set motors to run to target encoder position and stop with brakes on.
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Mode", "waiting");
        telemetry.update();
        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

        // set both motors to 25% power. Movement will start. Sign of power is
        // ignored as sign of target encoder position controls direction when
        // running to position.

        backLeft.setPower(0.25);
        backRight.setPower(0.25);
        frontLeft.setPower(0.25);
        frontRight.setPower(0.25);
        // wait while opmode is active and left motor is busy running to position.

        while (opModeIsActive() && backLeft.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", backLeft.getCurrentPosition() + "  busy=" + backLeft.isBusy());
            telemetry.addData("encoder-fwd-right", backRight.getCurrentPosition() + "  busy=" + backRight.isBusy());
            telemetry.update();
            idle();
        }

        // set motor power to zero to turn off motors. The motors stop on their own but
        // power is still applied so we turn off the power.

        backLeft.setPower(0.0);
        backRight.setPower(0.0);
        frontLeft.setPower(0.0);
        frontRight.setPower(0.0);
        // wait 5 sec to you can observe the final encoder position.

        while (opModeIsActive() && getRuntime() < 5)
        {
            telemetry.addData("encoder-fwd-left-end", backLeft.getCurrentPosition());
            telemetry.addData("encoder-fwd-right-end", backRight.getCurrentPosition());
            telemetry.update();
            idle();
        }

        // From current position back up to starting point. In this example instead of
        // having the motor monitor the encoder we will monitor the encoder ourselves.

        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backLeft.setTargetPosition(0);
        backRight.setTargetPosition(0);

        // Power sign matters again as we are running without encoder.
        backLeft.setPower(-0.25);
        backRight.setPower(-0.25);

        while (opModeIsActive() && backLeft.getCurrentPosition() > backLeft.getTargetPosition())
        {
            telemetry.addData("encoder-back-left", backLeft.getCurrentPosition());
            telemetry.addData("encoder-back-right", backRight.getCurrentPosition());
            telemetry.update();
            idle();
        }

        // set motor power to zero to stop motors.

        backLeft.setPower(0.0);
        backRight.setPower(0.0);



        while (opModeIsActive() && getRuntime() < 5)
        {
            telemetry.addData("encoder-back-left-end", backLeft.getCurrentPosition());
            telemetry.addData("encoder-back-right-end", backRight.getCurrentPosition());
            telemetry.update();
            idle();
        }
    }
}