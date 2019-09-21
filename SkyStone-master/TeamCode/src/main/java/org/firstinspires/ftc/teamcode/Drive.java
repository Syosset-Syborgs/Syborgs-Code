package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {

    DcMotor FL, FR, BL, BR; //Drive Motors

    DcMotor LeftOdometry, StrafeOdometry, RightOdometry;

    HardwareMap hw;
/*
 *Rev Hub 1
 *  -Port 1: FL, LeftOdometry
 *  -Port 2: FR, StrafeOdometry
 *  -Port 3: BL, RightOdometry
 *  -Port 4: BR
 */

    int robotRadius = 216; //MilliMeters
    double odomentryWheelRadius = 28.5;
    int odomentryCPR = 360;
    double odoTicksPerMM = 28.5 / 360;

    void mapDevices()
    {
        //Map Motors
        FL = hw.get(DcMotor.class, "FL");
        FR = hw.get(DcMotor.class, "FR");
        BL = hw.get(DcMotor.class, "BL");
        BR = hw.get(DcMotor.class, "BR");
        //Odometry Wheels
        LeftOdometry = hw.get(DcMotor.class, "FL");
        StrafeOdometry = hw.get(DcMotor.class, "FR");
        RightOdometry = hw.get(DcMotor.class, "BL");
    }

    void forward(double mm, double speed) throws InterruptedException
    {
       double mmMoved = 0;
       double LeftRightOdoDiff = LeftOdometry.getCurrentPosition() - RightOdometry.getCurrentPosition();

       double LeftMM = (mm * odoTicksPerMM) + LeftRightOdoDiff + LeftOdometry.getCurrentPosition();
       double RightMM = (mm * odoTicksPerMM) + RightOdometry.getCurrentPosition();

       setForwardMode();

        while (mm < mmMoved)
       {
           setAllPower(speed);

           if (LeftMM > RightMM)
           {
               FL.setPower(speed-.1);
               BL.setPower(speed- .1);
           } else if (RightMM > LeftMM)
           {
               FR.setPower(speed-.1);
               BR.setPower(speed-.1);
           }
           wait(10);
           mmMoved = Math.abs((LeftMM - (LeftOdometry.getCurrentPosition() * odomentryCPR)) + (RightMM - (RightMM - (RightOdometry.getCurrentPosition() * odomentryCPR))) / 2);
       }
    }

    void StrafeLeft(double mm, double speed) throws InterruptedException
    {
        setLeftStrafeMode();

        double mmMoved = 0;
        double LeftRightOdoDiff = LeftOdometry.getCurrentPosition() - RightOdometry.getCurrentPosition();

        double LeftMM = (mm * odoTicksPerMM) + LeftRightOdoDiff + LeftOdometry.getCurrentPosition();
        double RightMM = (mm * odoTicksPerMM) + RightOdometry.getCurrentPosition();
        double strafeMM =(mm * odoTicksPerMM) + StrafeOdometry.getCurrentPosition();

        while (mmMoved < mm)
        {
            setAllPower(speed);

            if (LeftMM > RightMM)
            {
                FL.setPower(speed + .1);
                BL.setPower(speed - .1);
            } else if (RightMM > LeftMM)
            {
                FR.setPower(speed - .1);
                BR.setPower(speed + .1);
            }

            wait(10);

            mmMoved += Math.abs(mmMoved - (StrafeOdometry.getCurrentPosition() * odoTicksPerMM));
        }



    }

    void setForwardMode()
    {
        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);

        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void setLeftStrafeMode()
    {
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);

        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void setRightStrafeMode()
    {
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setDirection(DcMotorSimple.Direction.FORWARD);

        BR.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void setAllPower(double speed)
    {
        FL.setPower(speed);
        FR.setPower(speed);
        FR.setPower(speed);
        BR.setPower(speed);
    }

}
