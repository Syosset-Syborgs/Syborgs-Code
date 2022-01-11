package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Syborgs_TeleOp_2021_22 extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FrontLeft, FrontRight, RearLeft, RearRight, arm;
    private CRServo intake;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        FrontLeft = hardwareMap.get(DcMotor.class, "");
        FrontRight = hardwareMap.get(DcMotor.class, "");
        RearLeft = hardwareMap.get(DcMotor.class, "");
        RearRight = hardwareMap.get(DcMotor.class, "");
        arm = hardwareMap.get(DcMotor.class, "");
        intake = hardwareMap.get(CRServo.class, "");

        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        RearRight.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        FrontLeft.setPower(gamepad1.left_stick_y - gamepad1.left_trigger + gamepad1.right_trigger);
        RearLeft.setPower(gamepad1.left_stick_y + gamepad1.left_trigger - gamepad1.right_trigger);
        FrontRight.setPower(gamepad1.right_stick_y + gamepad1.left_trigger - gamepad1.right_trigger);
        RearRight.setPower(gamepad1.right_stick_y - gamepad1.left_trigger + gamepad1.right_trigger);

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

    @Override
    public void stop() {

    }
}
