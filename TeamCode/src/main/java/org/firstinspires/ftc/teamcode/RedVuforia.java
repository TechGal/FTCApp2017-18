package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Aila on 2018-02-14.
 */

@Autonomous(name="RedVuforia", group="Felix")
public class RedVuforia extends LinearOpMode{

    VuforiaLocalizer vuforia;
    int key = 0;
    int count = 0;
    RelicRecoveryVuMark vuMark;

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

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = RC.VUFORIA_LICENSE_KEY;

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);

        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        waitForStart();

        relicTrackables.activate();

        while (opModeIsActive() && (count < 100000)) {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                if (vuMark == RelicRecoveryVuMark.LEFT){
                    key = 1;
                }
                else if (vuMark == RelicRecoveryVuMark.CENTER){
                    key = 2;
                }
                else if (vuMark == RelicRecoveryVuMark.RIGHT){
                    key = 3;
                }
            }
            else {
                key = 0;
            }
            count = count + 1;
        }

        telemetry.addData("VuMark", "%s", vuMark);

        telemetry.addData("Key", key);
        telemetry.update();

        sleep(1000);

        if (key == 1){
            //Left column

            encoderDrive(0.3, 0.9, 0.9, 5);
            sleep(200);
            encoderDrive(0.5, -19, 19, 30);
            sleep(200);

            chai.output(0.9);
            sleep(300);
            chai.stop();

            encoderDrive(0.3, 4, -4, 10);

        }
        else if (key == 2){
            //Centre column

            encoderDrive(0.3, 0.65, 0.65, 5);
            sleep(200);
            encoderDrive(0.5, -14, 14, 20);
            sleep(200);

            chai.output(0.9);
            sleep(300);
            chai.stop();

            encoderDrive(0.3, 4, -4, 10);

        }
        else if (key == 3){
            //Right column

            encoderDrive(0.3, 0.45, 0.45, 5);
            sleep(200);
            encoderDrive(0.5, -13, 13, 20);
            sleep(200);

            chai.output(0.9);
            sleep(300);
            chai.stop();

            encoderDrive(0.3, 4, -4, 10);

        }
        else {

        }
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
