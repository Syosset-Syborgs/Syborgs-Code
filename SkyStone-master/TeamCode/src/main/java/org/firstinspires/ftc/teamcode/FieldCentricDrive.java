
public class FieldCentricDrive extends LinearOpMode {

  DcMotor FL, FR, BL, BR;
  BNO055IMU imu;
  
  @Override
  public void runOpMode() throws InterruptedException {
    
    FL = hardwareMap.get(DcMotor.class, "FL");
    FR = hardwareMap.get(DcMotor.class, "FR");
    BL = hardwareMap.get(DcMotor.class, "BL");
    BR = hardwareMap.get(DcMotor.class, "BR");
    
    FL.setDirection(DcMotor.Direction.REVERSE);
    BL.setDirection(DcMotor.Direction.REVERSE);
    
    FL.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
    FR.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
    BL.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
    BR.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
    
    imu = hardwareMap.get(BNO055IMU.class, "imu");
    
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
    parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
    parameters.loggingEnabled = true;
    parameters.loggingTag = "IMU";
    
    imu.initialize(parameters);
    
    Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    telemetry.addAction(new Runnable() {
      @Override public void run() {
        // Acquiring the angles is relatively expensive; we don't want
        // to do that in each of the three items that need that info, as that's
        // three times the necessary expense.
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        }
      });
    double angle = 0;
    
    waitForStart();
    
    while (opModeIsActive()) {
      
      telemetry.addLine("angle: ", angle);
      telemetry.update();
      
      angle = angles.secondAngle;
      
      double LY = -gamepad1.left_stick_y * Math.cos(Math.toRadians(angle));
      double LX = gamepad1.left_stick_x * Math.sin(Math.toRadians(angle));
      double RX = gamepad1.right_stick_x;
      
      FL.setPower(LY + LX + RX);
      FR.setPower(LY - LX - RX);
      BL.setPower(LY - LX + RX);
      BR.setPower(LY + LX - RX);
      
    }
    
  }

}
