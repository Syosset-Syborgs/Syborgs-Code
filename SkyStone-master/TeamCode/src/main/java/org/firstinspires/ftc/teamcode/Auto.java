package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Auto extends LinearOpMode {

    Drive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive.mapDevices();

        waitForStart();




    }
}
