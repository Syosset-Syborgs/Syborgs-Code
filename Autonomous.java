// Are you sure that ticks_per_rev should be used in the variable declaration for ticks_per_inches?
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
*in ticks
*1440 ticks per 360 deg rotation
*motor.setTargetPosition(ticks);
*motors used: mechnum
*
*/

/*
* Syborgs 10696 Autonomous Code
* Purpose: create the route for the robot during the 30-second Autonomous period
* Authors: Harish Varadarajan and Tony Zheng Yu
* Iteration 2: Basic Routing with Encoders (Computer Vision coming soon)
*/

public class Autonomous extends LinearOpMode {
       /*
       hardware to map:
       * DcMotor - Front Left, Front Right, Rear Left, Rear Right
       * DcMotor - Left Intake and Right Intake
       * ARM - DcMotor Elevator, CRServo Drop Intake and Clamp
       */
    private ElapsedTime runtime = new ElapsedTime();
    // declare motors for mapping
    private static DcMotor frontLeftMotor, frontRightMotor, rearLeftMotor, rearRightMotor;
    private static DcMotor LIntake, RIntake;
    private static DcMotor elevator;
    private static CRServo dropIntake, clamp;
    private static Servo LHook, RHook;
    //encoder values
    private static final int wheelRadius = 3, length = 18, width = 18, ticksPerRev = 1440;
    private static final double ticksPerInches = (ticksPerRev)/(2 * Math.PI * wheelRadius), turnRadius = Math.sqrt(Math.pow((double) length / 2, 2) + Math.pow((double) width / 2,2 )), ticksPerDegree = ticksPerRev/360;
    private static final double circumference = (2 * Math.PI * turnRadius);

    private void mapHardware () {
        // map motors
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRight");
        rearLeftMotor = hardwareMap.get(DcMotor.class, "rearLeft");
        rearRightMotor = hardwareMap.get(DcMotor.class, "rearRight");
        // map intakes and arm
        LIntake = hardwareMap.get(DcMotor.class, "leftIntake");
        RIntake = hardwareMap.get(DcMotor.class, "rightIntake");
        elevator = hardwareMap.get(DcMotor.class, "elevator");
        dropIntake = hardwareMap.get(CRServo.class, "dropIntake");
        clamp = hardwareMap.get(CRServo.class, "clamp");
        LHook = hardwareMap.get(Servo.class, "leftHook");
        RHook = hardwareMap.get(Servo.class, "rightHook");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rearLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rearRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    private void drive (double power, int inch) {
        //drive forward/backward
        frontLeftMotor.setTargetPosition((int) ticksPerInches * inch);
        rearLeftMotor.setTargetPosition((int) ticksPerInches * inch);
        frontRightMotor.setTargetPosition((int) ticksPerInches * inch);
        rearRightMotor.setTargetPosition((int) ticksPerInches * inch);
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        rearRightMotor.setPower(power);
        rearLeftMotor.setPower(power);
        while (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || rearLeftMotor.isBusy() || rearRightMotor.isBusy());
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        rearRightMotor.setPower(0);
        rearLeftMotor.setPower(0);
    }
    private void turnLeft(int deg) {
        frontLeftMotor.setTargetPosition((-deg/360) * ((int) circumference));
        rearLeftMotor.setTargetPosition((-deg/360) * ((int) circumference));
        frontRightMotor.setTargetPosition((-deg/360) * ((int) circumference));
        rearRightMotor.setTargetPosition((-deg/360) * ((int) circumference));
        // turn left IN PLACE
        frontLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        rearRightMotor.setPower(1);
        rearLeftMotor.setPower(-1);
        while (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || rearLeftMotor.isBusy() || rearRightMotor.isBusy());
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        rearRightMotor.setPower(0);
        rearLeftMotor.setPower(0);
    }
    private void turnRight(int deg) {
        frontLeftMotor.setTargetPosition((deg/360) * ((int) circumference));
        rearLeftMotor.setTargetPosition((deg/360) * ((int) circumference));
        frontRightMotor.setTargetPosition((deg/360) * ((int) circumference));
        rearRightMotor.setTargetPosition((deg/360) * ((int) circumference));
        // turn right IN PLACE
        frontLeftMotor.setPower(1);
        rearLeftMotor.setPower(1);
        frontRightMotor.setPower(-1);
        rearRightMotor.setPower(-1);
        while (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || rearLeftMotor.isBusy() || rearRightMotor.isBusy());
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        rearLeftMotor.setPower(0);
        rearRightMotor.setPower(0);
    }
    private void STOP() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        rearLeftMotor.setPower(0);
        rearRightMotor.setPower(0);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dropIntake.setPower(0);
        clamp.setPower(0);
        LHook.setPosition(0);
        RHook.setPosition(0);
    }
    //initialize
    @Override
    public void runOpMode() {
        mapHardware();

        // run encoders for motors
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // reset encoders for motors
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {
            //unclamping just to be safe
            clamp.setPower(-1);
            // driving to stone
            drive(0.6, 30);
            // getting the stone
            dropIntake.setPower(-0.5);
            clamp.setPower(1);
            // facing alliance bridge
            turnRight(90);
            drive(0.6, 121);
            // facing foundation
            turnLeft(90);
            LHook.setPosition(0.5);
            RHook.setPosition(0.5);
            drive(-0.6, -4);
            // facing toward landing zone
            turnRight(90);
            drive(0.6, 9);
            //unhooking and placing block on foundation
            LHook.setPosition(0);
            RHook.setPosition(0);
            clamp.setPower(-1);
            // crossing alliance bridge
            drive(-0.6, -116);
            turnLeft(90);
            //getting the second block
            drive(0.6, 25);
            clamp.setPower(1);
            // facing toward foundation
            turnRight(90);
            dropIntake.setPower(-0.6);
            drive(0.6, 121);
            // get the block on top of the foundation
            dropIntake.setPower(0.4);
            clamp.setPower(-1);
            dropIntake.setPower(-0.4);
            //park under alliance bridge
            drive(-0.6, -65);
            STOP();
            idle();
        }
    }
}





