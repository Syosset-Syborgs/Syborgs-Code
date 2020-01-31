package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name="Blue")
public class BlueAuto extends LinearOpMode {

    DcMotor FL, FR, BL, BR;
    CRServo claw;
    ModernRoboticsI2cRangeSensor front;
    ColorSensor cs;
    BNO055IMU imu;
    Orientation angles;

    final double LENGTH = 14.5, WIDTH = 15.5, TICKS_PER_REV = 383.6, WHEEL_RADIUS = 2, TICKS_PER_INCH = TICKS_PER_REV / (2 * Math.PI *
            WHEEL_RADIUS), TICKS_PER_DEGREE = TICKS_PER_REV / 360, TURN_RADIUS = Math.sqrt(Math.pow(LENGTH / 2, 2) + Math.pow(WIDTH / 2, 2));

    @Override
    public void runOpMode() throws InterruptedException {

        FL = hardwareMap.get(DcMotor.class, "FL");
        FR = hardwareMap.get(DcMotor.class, "FR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");

        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);

        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        claw = hardwareMap.get(CRServo.class, "claw");

        front = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "front");

        cs = hardwareMap.get(ColorSensor.class, "cs");
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";

        imu.initialize(parameters);

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        telemetry.addAction(new Runnable() {
            @Override public void run() {
                // Acquiring the angles is relatively expensive; we don't want
                // to do that in each of the three items that need that info, as that's
                // three times the necessary expense.
                angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            }
        });

        waitForStart();

        drive('z', 1000, .7);
        turnToAngle(0);

        double ydist = 0;

        int temp = FL.getCurrentPosition();
        while (front.cmOptical() > 2.5 && opModeIsActive()) {
            FL.setPower(.4);
            FR.setPower(.4);
            BL.setPower(.4);
            BR.setPower(.4);
        }

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);

        ydist += FL.getCurrentPosition() - temp;

        for (int i = 0; i < 3; i++) {
            if (cs.red() <= 1 && cs.blue() <= 1 && cs.green() <= 1) {
                break;
            }
            drive('x', 500, .6);
            turnToAngle(0);
        }

        claw.setPower(-.8);
        sleep(500);
        claw.setPower(0);

        drive('y', -400, .8);
        turnToAngle(0);

        sleep(500);

    }

    void turnToAngle(double target) {

        telemetry.update();

        while (opModeIsActive() && (angles.firstAngle < target - .1 || angles.firstAngle > target + .1)) {
            telemetry.update();
            FL.setPower((target - angles.firstAngle)/90);
            FR.setPower((angles.firstAngle - target)/90);
            BL.setPower((target - angles.firstAngle)/90);
            BR.setPower((angles.firstAngle - target)/90);
            telemetry.update();
        }

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);

    }

    /**
     * z - forward +, backwards -
     * x - right +, left -
     * y - clockwise +, counterclockwise -
     */
    void drive(char axis, double value, double speed) {

        int fl = FL.getTargetPosition() + (int) (axis == 'y' ? value : value);
        int fr = FR.getTargetPosition() + (int) (axis == 'y' ? -value : value * (axis == 'x' ? -1 : 1));
        int bl = BL.getTargetPosition() + (int) (axis == 'y' ? value : value * (axis == 'x' ? -1 : 1));
        int br = BR.getTargetPosition() + (int) (axis == 'y' ? -value : value);

        FL.setTargetPosition(fl);
        FR.setTargetPosition(fr);
        BL.setTargetPosition(bl);
        BR.setTargetPosition(br);

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setPower(speed);
        FR.setPower(speed);
        BL.setPower(speed);
        BR.setPower(speed);

        while (opModeIsActive() && (FL.isBusy() || FR.isBusy() || BL.isBusy() || BR.isBusy()));

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);

        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

}
