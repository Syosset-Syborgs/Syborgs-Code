package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="Syborgs TeleOp", group="")
public class Syborgs_TeleOp_2022 extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FL, FR, RL, RR, arm, carousel;
    private CRServo intake, hand;

    public void init() {
        FL = hardwareMap.get(DcMotor.class, "Front Left");
        FR = hardwareMap.get(DcMotor.class, "Front Right");
        RL = hardwareMap.get(DcMotor.class, "Rear Left");
        RR = hardwareMap.get(DcMotor.class, "Rear Right");
        arm = hardwareMap.get(DcMotor.class, "arm");
        carousel = hardwareMap.get(DcMotor.class, "spin");
        intake = hardwareMap.get(CRServo.class, "intake");
        hand = hardwareMap.get(CRServo.class, "hand");

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

        if (gamepad2.right_bumper) {
            intake.setPower(0.4);
        }
        if (gamepad2.left_bumper) {
            hand.setPower(0.4);
        }
        if (gamepad2.a) {
            carousel.setPower(0.7);
        }
    }
}
