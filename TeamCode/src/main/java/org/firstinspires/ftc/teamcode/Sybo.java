package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="Sybo", group="")
public class Sybo extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FL, FR, RL, RR;
    private CRServo arm;

    public void init() {
        FL = hardwareMap.get(DcMotor.class, "Front Left");
        FR = hardwareMap.get(DcMotor.class, "Front Right");
        RL = hardwareMap.get(DcMotor.class, "Rear Left");
        RR = hardwareMap.get(DcMotor.class, "Rear Right");
        arm = hardwareMap.get(CRServo.class, "arm");

        FR.setDirection(DcMotor.Direction.REVERSE);
        RR.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialised");
    }

    public void loop() {
        FL.setPower(gamepad1.left_stick_y - gamepad1.left_trigger + gamepad1.right_trigger);
        RL.setPower(gamepad1.left_stick_y + gamepad1.left_trigger - gamepad1.right_trigger);
        FR.setPower(gamepad1.right_stick_y + gamepad1.left_trigger - gamepad1.right_trigger);
        RR.setPower(gamepad1.right_stick_y - gamepad1.left_trigger + gamepad1.right_trigger);

        if (gamepad1.dpad_up) {
            arm.setPower(0.4);
        } else if (gamepad1.dpad_down) {
            arm.setPower(-0.4);
        }
    }
}
