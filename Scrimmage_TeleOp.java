/**
 * Purpose - Our first TeleOp code; will be used at Scrimmage.
 * Revision History:
 *      - 1: 11/6/19
 *      - 2: 11/15/19
 *      - 3: 11/18/19
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Scrimmage TeleOp", group="TeleOp")
//@Disabled
public class Scrimmage_TeleOp extends OpMode
{
    //Drive System
    private static DcMotor FL, FR, BL, BR;
    private double LY, RY, LT, RT;

    //Variables for reversing driving controls
    private boolean driveBackwards = false;
    private ElapsedTime eTime = new ElapsedTime();

    //Intake System
    private static DcMotor LIntake, RIntake;

    //Arm system
    private static DcMotor elevator;
    private CRServo dropIntake, clamp;
    private double elevate, clampOpen;

    @Override
    public void init()
    {
        //Mapping drive system hardware
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");

        //Reverse one wheel side
        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        //Immediately stop
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Mapping intake system hardware
        LIntake = hardwareMap.dcMotor.get("left_intake");
        RIntake = hardwareMap.dcMotor.get("right_intake");

        //Reverse one intake side
        LIntake.setDirection(DcMotorSimple.Direction.FORWARD);
        RIntake.setDirection(DcMotorSimple.Direction.REVERSE);

        //Immediately stop
        LIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Mapping intake system hardware
        elevator = hardwareMap.dcMotor.get("elevator");
        dropIntake = hardwareMap.crservo.get("dropIntake");
        clamp = hardwareMap.crservo.get("clamp");

    }

    @Override
    public void loop()
    {
        //Controller 1 inputs
        LY = -gamepad1.left_stick_y;
        RY = -gamepad1.right_stick_y;
        LT = gamepad1.left_trigger;
        RT = gamepad1.right_trigger;




        //Switch mode
        if(gamepad1.y)
        {
            driveBackwards = !driveBackwards;
            if(driveBackwards)
            {
                FR.setDirection(DcMotorSimple.Direction.FORWARD);
                BR.setDirection(DcMotorSimple.Direction.FORWARD);
                FL.setDirection(DcMotorSimple.Direction.REVERSE);
                BL.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            else
            {
                FL.setDirection(DcMotorSimple.Direction.FORWARD);
                BL.setDirection(DcMotorSimple.Direction.FORWARD);
                FR.setDirection(DcMotorSimple.Direction.REVERSE);
                BR.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            eTime.reset();

            //Slow the tick, preventing spamming.
            while(eTime.time() < .3);
        }

        //Tell user which side is front
        telemetry.addData("ModeL ", (driveBackwards ? "arm front" : "wires front"));

        //Reverse inputs if on backwards mode
        if(driveBackwards)
        {
            RY = -gamepad1.left_stick_y;
            LY = -gamepad1.right_stick_y;
        }

        //Mecanum wheel movement
        FL.setPower(LY + RT - LT);
        BL.setPower(LY - RT + LT);
        FR.setPower(RY + LT - RT);
        BR.setPower(RY - LT + RT);

        //Controller 2 inputs
        clamp.setPower(-gamepad2.right_stick_y);
        elevator.setPower(-gamepad2.left_stick_y);

        //Intake control
        if(gamepad2.a)
        {
            LIntake.setPower(1);
            RIntake.setPower(1);
        }
        else
        {
            LIntake.setPower(0);
            RIntake.setPower(0);
        }

        //Small movements with the Arm Controller's dpad; useful for minor adjustments
        if(gamepad2.dpad_up)
        {
            FL.setPower(.4);
            FR.setPower(.4);
            BL.setPower(.4);
            BR.setPower(.4);
        }
        else if(gamepad2.dpad_down)
        {
            FL.setPower(-.4);
            FR.setPower(-.4);
            BL.setPower(-.4);
            BR.setPower(-.4);
        }
        else if(gamepad2.dpad_left)
        {
            FL.setPower(-.4);
            FR.setPower(.4);
            BL.setPower(-.4);
            BR.setPower(.4);
        }
        else if(gamepad2.dpad_right)
        {
            FL.setPower(.4);
            FR.setPower(-.4);
            BL.setPower(.4);
            BR.setPower(-.4);
        }
    }

}
