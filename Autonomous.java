package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Autonomous extends LinearOpMode {
   /*
   Syborgs 10696 Autonomous Code
   hardware to map:
   * DcMotor - Front Left, Front Right, Rear Left, Rear Right
   * DcMotor - Left Intake and Right Intake
   * ARM - DcMotor Elevator, CRServo Drop Intake and Clamp
   */

    // declare motors for mapping
    private static DcMotor frontLeftMotor, frontRightMotor, rearLeftMotor, rearRightMotor;
    private static DcMotor LIntake, RIntake;
    private static DcMotor elevator;
    private static CRServo dropIntake, clamp;
    private static Servo LHook, RHook;



    ElapsedTime runtime = new ElapsedTime();

    public void mapHardware () {
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
    }
    public void drive (double power, int ticksPerInch) {
        runtime.reset();
        frontLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() + ticksPerInch);
        rearLeftMotor.setTargetPosition(rearLeftMotor.getCurrentPosition() + ticksPerInch);
        frontRightMotor.setTargetPosition(frontRightMotor.getCurrentPosition() + ticksPerInch);
        rearRightMotor.setTargetPosition(rearRightMotor.getCurrentPosition() + ticksPerInch);
        telemetry.update();
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        rearRightMotor.setPower(power);
        rearLeftMotor.setPower(power);
    }
    public void turnLeft() {
        frontLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        rearLeftMotor.setPower(-1);
        rearRightMotor.setPower(1);
        sleep(800);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        rearLeftMotor.setPower(0);
        rearRightMotor.setPower(0);
    }
    public void turnRight() {
        frontLeftMotor.setPower(1);
        rearLeftMotor.setPower(1);
        frontRightMotor.setPower(-1);
        rearRightMotor.setPower(-1);
        sleep(800);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        rearLeftMotor.setPower(0);
        rearRightMotor.setPower(0);
    }
    // initialize
    public void runOpMode() throws InterruptedException {
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
        while (opModeIsActive()) {
            //unclamping just to be safe
            clamp.setPower(-1);
            // driving to stone
            drive(0.6, 2000);
            sleep(500);
            // getting dropIntake into position
            dropIntake.setPower(-0.5);
            sleep(250);
            // getting the stone
            clamp.setPower(1);
            // facing alliance bridge
            turnRight();
            drive(0.6, 10500);
            // facing foundation
            turnLeft();
            LHook.setPosition(0.5);
            RHook.setPosition(0.5);
            drive(-0.6, 300);
            // facing toward landing zone
            turnRight();
            drive(0.6, 600);
            //unhooking and placing block on foundation
            LHook.setPosition(0);
            RHook.setPosition(0);
            clamp.setPower(-1);
            // crossing alliance bridge
            drive(-0.6, 11000);
            turnLeft();
            //getting the second block
            drive(0.6, 600);
            sleep(500);
            clamp.setPower(1);
            // facing toward foundation
            turnRight();
            dropIntake.setPower(-0.6);
            drive(0.6, 11000);
            sleep(375);
            // get the block on top of the foundation
            dropIntake.setPower(0.4);
            clamp.setPower(-1);
            dropIntake.setPower(-0.4);
            //park under alliance bridge
            drive(0.6, 5200);
            idle();
        }
    }
}
