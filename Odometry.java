package org.firstinspires.ftc.teamcode.Objects;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Odometry
{
    final static double L = 10; // distance between left and right encoder
    final static double B = 10; // distance between midpoint of left and right and aux
    final static double inchesPerTick = 20; // inches Per Tick

    double yTraveled = 0.0;
    double xTraveled = 0.0;
    double degTraveled = 0.0;

    // last encoder positions
    private int lastLeftEncoder = 0;
    private int lastRightEncoder = 0;
    private int lastAuxEncoder = 0;

    int currentLeftEncoder = 0;
    int currentRightEncoder = 0;
    int currentAuxEncoder = 0;

    double x = 0.0;
    double y = 0.0;
    double theta = 0.0;

    public DcMotor encoderLeft;
    public DcMotor encoderRight;
    public DcMotor encoderAux;

    private HardwareMap hardwareMap;

    public Odometry(HardwareMap thisHardwareMap)
    {
        hardwareMap = thisHardwareMap;

        encoderLeft = hardwareMap.dcMotor.get("encoderLeft");
        encoderLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        encoderLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        encoderLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        encoderRight = hardwareMap.dcMotor.get("encoderLeft");
        encoderRight.setDirection(DcMotorSimple.Direction.FORWARD);
        encoderRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        encoderRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        encoderAux = hardwareMap.dcMotor.get("encoderLeft");
        encoderAux.setDirection(DcMotorSimple.Direction.FORWARD);
        encoderAux.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        encoderAux.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



    }

    public void update()
    {
        lastLeftEncoder = currentLeftEncoder;
        lastRightEncoder = currentRightEncoder;
        lastAuxEncoder = currentAuxEncoder;

        currentLeftEncoder = encoderLeft.getCurrentPosition();
        currentRightEncoder = encoderRight.getCurrentPosition();
        currentAuxEncoder = encoderAux.getCurrentPosition();

        double leftWheelDelta = (currentLeftEncoder - lastLeftEncoder);
        double rightWheelDelta = (currentRightEncoder - lastRightEncoder);
        double auxWheelDelta = (currentAuxEncoder - lastAuxEncoder);


        double dTheta = inchesPerTick*(leftWheelDelta - rightWheelDelta)/L;
        double dX = inchesPerTick*(leftWheelDelta + rightWheelDelta) / 2.0;
        double dY = inchesPerTick*(auxWheelDelta - (rightWheelDelta - leftWheelDelta) * B / L);

        double thisTheta = theta + (dTheta / 2.0);
        x += dX * Math.cos(thisTheta) - dY * Math.sin(thisTheta);
        y += dX * Math.sin(thisTheta) + dY * Math.cos(thisTheta);
        theta += dTheta;


    }
}