
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
      
    }
    
  }

}
