package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class deliverBlocks_Autonomous extends LinearOpMode {
    /**
     * Syborgs 10696 Autonomous Code 2020
     * Authors: Harish Varadarajan, Emily Goldman, Tony Zheng Yu, Rohan Ghotra (computer vision)
     * Purpose: autonomous program for FTC Skystone
     * Iteration 5
     */
    private ElapsedTime runtime = new ElapsedTime();
    // declare motors for mapping
    private static DcMotor frontLeftMotor, frontRightMotor, rearLeftMotor, rearRightMotor;
    private static DcMotor elevator1, elevator2;
    private static Servo LHook, RHook, clamp, arm;
    //encoder values
    private static final int wheelRadius = 3, length = 18, width = 18, ticksPerRev = 1440;
    private static final double ticksPerInches = (ticksPerRev)/(2 * Math.PI * wheelRadius), turnRadius = Math.sqrt(Math.pow((double) length / 2, 2) + Math.pow((double) width / 2,2 ));
    private static final double circumference = (2 * Math.PI * turnRadius);
    private void riseArm(double power, long seconds) {
        elevator1.setPower(power);
        elevator2.setPower(power);
        sleep(seconds * 1000);
        elevator1.setPower(0);
        elevator2.setPower(0);
    }
    private void drive (double power, int inchOrDeg, String isInchOrDeg) {
        /* drive forward/backward (negative inches is driving backward) */
        if (isInchOrDeg.equals("inchForward")) {
            frontLeftMotor.setTargetPosition((int) ticksPerInches * inchOrDeg);
            rearLeftMotor.setTargetPosition((int) ticksPerInches * inchOrDeg);
            frontRightMotor.setTargetPosition((int) ticksPerInches * inchOrDeg);
            rearRightMotor.setTargetPosition((int) ticksPerInches * inchOrDeg);
            frontLeftMotor.setPower(power);
            frontRightMotor.setPower(power);
            rearRightMotor.setPower(power);
            rearLeftMotor.setPower(power);
            while (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || rearLeftMotor.isBusy() || rearRightMotor.isBusy());
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            rearLeftMotor.setPower(0);
            rearRightMotor.setPower(0);
        }
        if (isInchOrDeg.equals("strafeRight")) {
            frontLeftMotor.setTargetPosition((int) ticksPerInches * inchOrDeg);
            rearLeftMotor.setTargetPosition((int) ticksPerInches * inchOrDeg);
            frontRightMotor.setTargetPosition((int) ticksPerInches * inchOrDeg);
            rearRightMotor.setTargetPosition((int) ticksPerInches * inchOrDeg);
            frontLeftMotor.setPower(-power);
            frontRightMotor.setPower(power);
            rearRightMotor.setPower(-power);
            rearLeftMotor.setPower(power);
            while (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || rearLeftMotor.isBusy() || rearRightMotor.isBusy());
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            rearLeftMotor.setPower(0);
            rearRightMotor.setPower(0);
        }
        if (isInchOrDeg.equals("strafeLeft")) {
            frontLeftMotor.setTargetPosition((int) ticksPerInches * inchOrDeg);
            rearLeftMotor.setTargetPosition((int) ticksPerInches * inchOrDeg);
            frontRightMotor.setTargetPosition((int) ticksPerInches * inchOrDeg);
            rearRightMotor.setTargetPosition((int) ticksPerInches * inchOrDeg);
            frontLeftMotor.setPower(power);
            frontRightMotor.setPower(-power);
            rearRightMotor.setPower(power);
            rearLeftMotor.setPower(-power);
            while (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || rearLeftMotor.isBusy() || rearRightMotor.isBusy());
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            rearLeftMotor.setPower(0);
            rearRightMotor.setPower(0);
        }
        else if (isInchOrDeg.equals("leftDegree")){
            frontLeftMotor.setTargetPosition((-inchOrDeg/360) * ((int) circumference));
            rearLeftMotor.setTargetPosition((-inchOrDeg/360) * ((int) circumference));
            frontRightMotor.setTargetPosition((-inchOrDeg/360) * ((int) circumference));
            rearRightMotor.setTargetPosition((-inchOrDeg/360) * ((int) circumference));
            // turn left IN PLACE
            frontLeftMotor.setPower(-power);
            frontRightMotor.setPower(power);
            rearRightMotor.setPower(power);
            rearLeftMotor.setPower(-power);
            while (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || rearLeftMotor.isBusy() || rearRightMotor.isBusy());
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            rearLeftMotor.setPower(0);
            rearRightMotor.setPower(0);
        }
        else if (isInchOrDeg.equals("rightDegree")){
            frontLeftMotor.setTargetPosition((inchOrDeg/360) * ((int) circumference));
            rearLeftMotor.setTargetPosition((inchOrDeg/360) * ((int) circumference));
            frontRightMotor.setTargetPosition((inchOrDeg/360) * ((int) circumference));
            rearRightMotor.setTargetPosition((inchOrDeg/360) * ((int) circumference));
            // turn right IN PLACE
            frontLeftMotor.setPower(power);
            frontRightMotor.setPower(-power);
            rearRightMotor.setPower(-power);
            rearLeftMotor.setPower(power);
            while (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || rearLeftMotor.isBusy() || rearRightMotor.isBusy());
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            rearLeftMotor.setPower(0);
            rearRightMotor.setPower(0);
        }
        else {
            idle();
        }
    }
    private void deliverBlocks() {
        //unclamp - just to be safe - and then drive toward and grab a block
        clamp.setPosition(1);
        drive(0.4, 12, "inchForward");
        
        
        //Rohan - add scanning here
        clamp.setPosition(0.15);
        drive(-0.6, 16, "inchForward");
        drive(1, 90, "leftDegree");
        //cross alliance bridge and drop block onto foundation (assuming ally moved foundation to parking zone)
        drive(0.6, 54, "inchForward");
        //add scan here if you think that we should not assume that the ally moved the foundation
        riseArm(0.3, 2);
        clamp.setPosition(0.8);
        //go for the second block
        drive(-0.6, 54, "inchForward");
        drive(0.6, 90, "rightDegree");
        //lower arm in preparation to grab second block
        riseArm(-0.3, 2);
        drive(0.4, 12, "inchForward");
        //Rohan - add scanning here
        
        
        clamp.setPosition(0.15);
        drive(-0.6, 16, "inchForward");
        drive(1, 90, "leftDegree");
        drive(0.6, 54, "inchForward");
        riseArm(0.3, 4);
        clamp.setPosition(0.8);
        //park under alliance bridge
        drive(-0.6, 26, "inchForward");
        idle();
    }
    //initialize

    public void runOpMode() throws InterruptedException  {
        /* map motors */
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRight");
        rearLeftMotor = hardwareMap.get(DcMotor.class, "rearLeft");
        rearRightMotor = hardwareMap.get(DcMotor.class, "rearRight");
        // map elevators and arm
        elevator1 = hardwareMap.get(DcMotor.class, "elevator1");
        elevator2 = hardwareMap.get(DcMotor.class, "elevator2");
        arm = hardwareMap.get(Servo.class, "arm");
        clamp = hardwareMap.get(Servo.class, "clamp");
        LHook = hardwareMap.get(Servo.class, "leftHook");
        RHook = hardwareMap.get(Servo.class, "rightHook");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rearLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rearRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        elevator1.setDirection(DcMotorSimple.Direction.REVERSE);
        elevator2.setDirection(DcMotorSimple.Direction.REVERSE);

        //brake when power is 0
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        while (opModeIsActive()) {
            telemetry.clearAll();
            deliverBlocks();
        }
    }
}








