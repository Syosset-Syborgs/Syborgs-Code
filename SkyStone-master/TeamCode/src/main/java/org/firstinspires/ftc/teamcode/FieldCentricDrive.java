
public class FieldCentricDrive extends LinearOpMode {

  DcMotor FL, FR, BL, BR;
  
  @Override
  public void runOpMode() throws InterruptedException {
    
    FL = hardwareMap.get(DcMotor.class, "FL");
    FR = hardwareMap.get(DcMotor.class, "FR");
    BL = hardwareMap.get(DcMotor.class, "BL");
    BR = hardwareMap.get(DcMotor.class, "BR");
    
    FR.setDirection(DcMotor.Direction.REVERSE);
    BR.setDirection(DcMotor.Direction.REVERSE);
    
    FL.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
    FR.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
    BL.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
    BR.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
    
    waitForStart();
    
    while (opModeIsActive()) {
      
      double LY = -gamepad1.left_stick_y;
      double LX = gamepad1.left_stick_x;
      double RX = gamepad1.right_stick_x;
      
      FL.setPower(LY + LX + RX);
      FR.setPower(LY - LX - RX);
      BL.setPower(LY - LX + RX);
      BR.setPower(LY + LX - RX);
      
    }
    
  }

}
