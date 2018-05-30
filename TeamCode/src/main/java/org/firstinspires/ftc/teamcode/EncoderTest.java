package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Aila on 2018-02-28.
 */

@Autonomous(name="EncoderTest", group="Felix")
public class EncoderTest extends LinearOpMode {

    Felix chai = null;
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    @Override
    public void runOpMode() throws InterruptedException {

        chai = new Felix();
        chai.init(hardwareMap);

        waitForStart();

        chai.drive(0.5);
        sleep(500);
        chai.stop();

        telemetry.addData("Left Encoder", chai.driveL.getCurrentPosition());
        telemetry.addData("Right Encoder", chai.driveR.getCurrentPosition());
        telemetry.update();

        sleep(500);

        chai.driveL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chai.driveR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /*
        int leftTarget = chai.driveL.getCurrentPosition() - 500;

        while (chai.driveL.getCurrentPosition() > leftTarget && opModeIsActive()) {
            chai.drive(0.5);
            sleep(10);
        }
        */

        encoderDrive(0.5, -10, 10, 30);
        sleep(200);
        encoderDrive(0.5, 5, 5, 5);
        sleep(200);
        encoderDrive(0.5, -5, -5, 5);
        sleep(200);
        encoderDrive(0.5, 10, -10, 30);

    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = chai.driveL.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = chai.driveR.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            chai.driveL.setTargetPosition(newLeftTarget);
            chai.driveR.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            chai.driveL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            chai.driveR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            chai.driveL.setPower(Math.abs(speed));
            chai.driveR.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (chai.driveL.isBusy() && chai.driveR.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Run Status", "Running");
                telemetry.update();
            }

            chai.driveL.setPower(0);
            chai.driveR.setPower(0);

        }
    }
}
