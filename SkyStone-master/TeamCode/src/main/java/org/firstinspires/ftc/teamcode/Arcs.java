
class Arcs extends LinearOpMode {
  
  DcMotor FL, FR, BL, BR;
  
  final double LENGTH = 14.5, WIDTH = 15.5, TICKS_PER_REV = 383.6, WHEEL_RADIUS = 2, TICKS_PER_INCH = TICKS_PER_REV / (2 * Math.PI *
        WHEEL_RADIUS), TICKS_PER_DEGREE = TICKS_PER_REV / 360, TURN_RADIUS = Math.sqrt(Math.pow(LENGTH / 2, 2) + Math.pow(WIDTH / 2, 2));
  
  @Override
  public void runOpMode() throws InterruptedException {
    
    FL = hardwareMap.get(DcMotor.class, "FL");
    FR = hardwareMap.get(DcMotor.class, "FR");
    BL = hardwareMap.get(DcMotor.class, "BL");
    BR = hardwareMap.get(DcMotor.class, "BR");
    
    FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    
    waitForStart();
    
  }
  
  void arc(double arc, double degrees) {
    
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
    
  }
  
}
