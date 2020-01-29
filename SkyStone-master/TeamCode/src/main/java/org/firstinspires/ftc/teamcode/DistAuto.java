
@Autonomous(name="Blue")
public class DistAuto {
  
  DcMotor FL, FR, BL, BR;
  CRServo claw;
  ModernRoboticsI2cRangeSensor front;
  ColorSensor cs;
  BNO055IMU imu;
  
  @Override
  public void runOpMode() throws InterruptedException {
    
    FL = hardwareMap.get(DcMotor.class, "FL");
    FR = hardwareMap.get(DcMotor.class, "FR");
    BL = hardwareMap.get(DcMotor.class, "BL");
    BR = hardwareMap.get(DcMotor.class, "BR");
    
    FR.setDirection(DcMotor.Direction.REVERSE);
    BR.setDirection(DcMotor.Direction.REVERSE);
    
    FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    claw = hardwareMap.get(CRServo.class, "claw");
    
    cs = hardwareMap.get(ColorSensor.class, "cs");
    imu = hardwareMap.get(BNO055IMU.class, "imu");
    
    waitForStart();
    
  }
  
  /**
   * z - forward +, backwards -
   * x - right +, left -
   * y - clockwise +, counterclockwise -
   */
  void drive(char axis, double value, double speed) {
    
    int fl = FL.getTargetPosition() + (int) (axis == 'y' ? value / 360 * TURN_RADIUS / 2 * TICKS_PER_INCH : value * TICKS_PER_INCH);
    int fr = FR.getTargetPosition() + (int) (axis == 'y' ? -value / 360 * TURN_RADIUS / 2 * TICKS_PER_INCH : value * TICKS_PER_INCH * (axis == 'x' ? -1 : 1));
    int bl = BL.getTargetPosition() + (int) (axis == 'y' ? value / 360 * TURN_RADIUS / 2 * TICKS_PER_INCH : value * TICKS_PER_INCH * (axis == 'x' ? -1 : 1));
    int br = BR.getTargetPosition() + (int) (axis == 'y' ? -value / 360 * TURN_RADIUS / 2 * TICKS_PER_INCH : value * TICKS_PER_INCH);
    
    FL.setTargetPosition(fl);
    FR.setTargetPosition(fr);
    BL.setTargetPosition(bl);
    BR.setTargetPosition(br);
    
    FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    FL.setPower(speed);
    FR.setPower(speed);
    BL.setPower(speed);
    BR.setPower(speed);
    
    while (opModeIsActive() && (FL.isBusy() || FR.isBusy() || BL.isBusy() || BR.isBusy()));
    
    FL.setPower(0);
    FR.setPower(0);
    BL.setPower(0);
    BR.setPower(0);
    
    FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    
  }
  
}
