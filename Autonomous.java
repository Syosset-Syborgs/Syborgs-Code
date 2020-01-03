package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Autonomous extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    // declare motors for mapping
    private static DcMotor frontLeftMotor, frontRightMotor, rearLeftMotor, rearRightMotor;
    private static DcMotor elevator1, elevator2;
    private static Servo LHook, RHook, clamp, arm;
    //encoder values
    private static final int wheelRadius = 3, length = 18, width = 18, ticksPerRev = 1440;
    private static final double ticksPerInches = (ticksPerRev)/(2 * Math.PI * wheelRadius), turnRadius = Math.sqrt(Math.pow((double) length / 2, 2) + Math.pow((double) width / 2,2 ));
    private static final double circumference = (2 * Math.PI * turnRadius);
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
        if (isInchOrDeg.equals("strafe")) {
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
    private void Red1() {
        //unclamp - just to be safe - and then drive toward and grab a block
        clamp.setPosition(1);
        drive(0.6, 12, "inchForward");
        clamp.setPosition(0.15);
        drive(-0.6, 16, "inchForward");
        drive(1, 90, "leftDegree");
        //cross alliance bridge and drop block onto foundation (assuming other team moved foundation to parking zone)
        drive(0.6, 54, "inchForward");
        elevator1.setPower(0.3);
        clamp.setPosition(0.8);
        //park under alliance bridge
        drive(-0.6, 26, "inchForward");
        idle();
    }
    private void Red2() {
        //unclamp - just to be safe - and then drive toward and grab the foundation
        clamp.setPosition(0.8);
        LHook.setPosition(0.5);
        RHook.setPosition(0.5);
        drive(0.6, 12, "inchForward");
        LHook.setPosition(1);
        RHook.setPosition(1);
        //move foundation into parking zone and park under alliance bridge
        drive(0.6, 6, "strafe");
        drive(1, 90, "turnLeft");
        drive(-0.6, 14, "strafe");
        drive(0.6, 20, "inchForward");
        LHook.setPosition(0.5);
        RHook.setPosition(0.5);
        drive(-0.6, 44, "inchForward");
        idle();
    }
    private void Blue1() {
        //unclamp - just to be safe - and then drive toward and grab a block
        clamp.setPosition(0.8);
        drive(0.6, 12, "inchForward");
        clamp.setPosition(0.15);
        drive(-0.6, 16, "inchForward");
        drive(1, 90, "rightDegree");
        //cross alliance bridge and drop block onto foundation (assuming other team moved foundation to parking zone)
        drive(0.6, 54, "inchForward");
        elevator1.setPower(0.3);
        clamp.setPosition(0.8);
        //park under alliance bridge
        drive(-0.6, 26, "inchForward");
        idle();
    }
    private void Blue2() {
        //unclamp - just to be safe - and then drive toward and grab the foundation
        clamp.setPosition(0.8);
        LHook.setPosition(0.5);
        RHook.setPosition(0.5);
        drive(0.6, 12, "inchForward");
        LHook.setPosition(1);
        RHook.setPosition(1);
        //move foundation into parking zone and park under alliance bridge
        drive(-0.6, 6, "strafe");
        drive(1, 90, "turnRight");
        drive(0.6, 14, "strafe");
        drive(0.6, 20, "inchForward");
        LHook.setPosition(0.5);
        RHook.setPosition(0.5);
        drive(-0.6, 44, "inchForward");
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

        }
    }
}








