package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Authors: Harish and Tony
 * Editor: Emily Goldman
 * Edit Date: 12/20/19
 * Edit Version: 1.0
 * Edits:
 *      - got rid of mapHardware() method; placed method body in runOpMode() to save computing time.
 *      - added comments for easier understanding.
 *      - Added @Autonomous annotation so the code can be accessed on the phone.
 *      - Added @Disabled annotation, then commented it out for easy disabling.
 */

@Autonomous(name = "Scrimmage Autonomous", group = "Scrimmage Code")
//@Disabled
public class Scrimmage_Autonomous extends LinearOpMode {
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

    public void drive (double power, int ticksPerBot) {
        runtime.reset();
        while (opModeIsActive()) {
            frontLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() + ticksPerBot);
            rearLeftMotor.setTargetPosition(rearLeftMotor.getCurrentPosition() + ticksPerBot);
            frontRightMotor.setTargetPosition(frontRightMotor.getCurrentPosition() + ticksPerBot);
            rearRightMotor.setTargetPosition(rearRightMotor.getCurrentPosition() + ticksPerBot);
            telemetry.update();
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rearLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rearRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeftMotor.setPower(power);
            frontRightMotor.setPower(power);
            rearRightMotor.setPower(power);
            rearLeftMotor.setPower(power);
            while (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && rearRightMotor.isBusy() && rearLeftMotor.isBusy()) {

            }

            idle();
        }
    }
    public void turnLeft() {
        frontLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        rearLeftMotor.setPower(-1);
        rearRightMotor.setPower(1);
    }
    public void turnRight() {
        frontLeftMotor.setPower(1);
        rearLeftMotor.setPower(1);
        frontRightMotor.setPower(-1);
        rearRightMotor.setPower(-1);
    }
    public void upGrab() {
        dropIntake.setPower(-1);
        // using methods to make things easier and not drag processing power. Currently unfinished.
    }
    // initialize
    public void runOpMode() throws InterruptedException {
        /*
        Hardware is mapped here.
         */
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

        /*
        Encoders are run and reset.
         */

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
        // get into position
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        /*
        The robot starts to go through autonomous strategy.
         */

        drive(0.6, 6);
        LHook.setPosition(1);
        RHook.setPosition(1);
        drive(0, 1);
        drive(-0.6, 6);
        frontLeftMotor.setPower(1);
        rearLeftMotor.setPower(1);
        frontRightMotor.setPower(-1);
        rearRightMotor.setPower(-1);
        // crossing alliance bridge
        drive(0.6, 132*5672);
        frontLeftMotor.setPower(-1);
        rearLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        rearRightMotor.setPower(1);
        dropIntake.setPower(1);
        clamp.setPower(1);
        frontLeftMotor.setPower(-1);
        rearLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        rearRightMotor.setPower(1);
        drive(0.6, 132*5672);
        clamp.setPower(-1);
        frontLeftMotor.setPower(1);
        rearLeftMotor.setPower(1);
        frontRightMotor.setPower(-1);
        rearRightMotor.setPower(-1);
        drive(0.6, 126*5672);
        dropIntake.setPower(-1);
        frontLeftMotor.setPower(-1);
        rearLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        rearRightMotor.setPower(1);

        dropIntake.setPower(1);
        clamp.setPower(1);
        frontLeftMotor.setPower(-1);
        rearLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        rearRightMotor.setPower(1);
        dropIntake.setPower(-1);
        // 5672 is approximate measurement for ticks to inches.
        drive(0.6, 126*5672);
        dropIntake.setPower(0.8);
        sleep(1000);
        clamp.setPower(-1);
        drive(-0.6, 118*5672);
        dropIntake.setPower(-0.8);
        frontLeftMotor.setPower(1);
        rearLeftMotor.setPower(1);
        frontRightMotor.setPower(-1);
        rearRightMotor.setPower(-1);
        dropIntake.setPower(1);
        sleep(1000);
        clamp.setPower(1);
        frontLeftMotor.setPower(-1);
        rearLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        rearRightMotor.setPower(1);
        dropIntake.setPower(-1);
        drive(0.6, 118*5672);
        dropIntake.setPower(0.6);
        clamp.setPower(-1);
        drive(-0.6, 110*5672);
        frontLeftMotor.setPower(1);
        rearLeftMotor.setPower(1);
        frontRightMotor.setPower(-1);
        rearRightMotor.setPower(-1);
        dropIntake.setPower(1);
        sleep(1000);
        clamp.setPower(1);
        sleep(1000);
        dropIntake.setPower(-1);
        frontLeftMotor.setPower(-1);
        rearLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        rearRightMotor.setPower(1);
        drive(0.6, 110*5672);
        dropIntake.setPower(0.4);
        sleep(1000);
        clamp.setPower(-1);
        drive(-0.6, 102*5672);

        frontLeftMotor.setPower(1);
        rearLeftMotor.setPower(1);
        frontRightMotor.setPower(-1);
        rearRightMotor.setPower(-1);
        dropIntake.setPower(1);
        sleep(1000);
        clamp.setPower(1);
        sleep(1000);
        dropIntake.setPower(-1);
        frontLeftMotor.setPower(-1);
        rearLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        rearRightMotor.setPower(1);
        drive(0.6, 102*5672);
        dropIntake.setPower(0.2);
        sleep(1000);
        clamp.setPower(-1);
        drive(-0.6, 94*5672);
        frontLeftMotor.setPower(1);
        rearLeftMotor.setPower(1);
        frontRightMotor.setPower(-1);
        rearRightMotor.setPower(-1);
        dropIntake.setPower(1);
        sleep(1000);
        clamp.setPower(1);
        sleep(1000);
        dropIntake.setPower(-1);
        frontLeftMotor.setPower(-1);
        rearLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        rearRightMotor.setPower(1);
        drive(0.6, 94);
        dropIntake.setPower(0.1);
        sleep(1000);
        clamp.setPower(-1);
        frontLeftMotor.setPower(0);
        rearLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        rearRightMotor.setPower(0);

    }
}






