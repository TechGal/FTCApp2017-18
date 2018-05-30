package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Aila on 2018-03-05.
 */

@Autonomous(name="JewelKeyBlue", group="Felix")
public class JewelKeyBlue extends LinearOpMode{

    VuforiaLocalizer vuforia;
    int key = 0;
    int count = 0;
    RelicRecoveryVuMark vuMark;

    Felix herb = null;
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    @Override
    public void runOpMode() throws InterruptedException {
        
        herb = new Felix();
        herb.init(hardwareMap);
        
        //Vuforia
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


        //Jewel
        ColorSensor sensor = herb.colourSensorLeft;

        double jewelDown = 0.78;

        float hsvValues[] = {0F, 0F, 0F};

        final float values[] = hsvValues;

        final double SCALE_FACTOR = 255;

        Color.RGBToHSV((int) (sensor.red() * SCALE_FACTOR),
                (int) (sensor.green() * SCALE_FACTOR),
                (int) (sensor.blue() * SCALE_FACTOR),
                hsvValues);

        waitForStart();

        herb.jewelL.setPosition(jewelDown);
        sleep(500);

        int h = (int) hsvValues[0];

        telemetry.addData("Red  ", sensor.red());
        telemetry.addData("Green", sensor.green());
        telemetry.addData("Blue ", sensor.blue());
        telemetry.addData("HSV ", h);

        telemetry.update();

        sleep(2000);

        int driveCommand = AnalyzeJewelConfig.jewelConfig(sensor.red(), sensor.green(), sensor.blue());


        if (driveCommand == 1){
            telemetry.addData("Drive", "Reverse");
            telemetry.update();

            sleep(1000);

            /*
            herb.drive(-0.3);
            sleep(200);
            herb.stop();

            herb.jewelL.setPosition(0);
            sleep(1000);

            herb.drive(0.5);
            sleep(150);
            herb.stop();
            */

            encoderDrive(0.3, 0.8, 0.8, 10);
            sleep(1000);
            herb.jewelL.setPosition(0);
            encoderDrive(0.3, -0.8, -0.8, 10);

        }
        else if (driveCommand == 2){
            telemetry.addData("Drive", "Forward");
            telemetry.update();

            sleep(1000);

            /*
            herb.drive(0.3);
            sleep(200);
            herb.stop();

            herb.jewelL.setPosition(0);
            sleep(1000);

            herb.drive(-0.5);
            sleep(300);
            herb.stop();
            */

            encoderDrive(0.3, -0.8, -0.8, 10);
            sleep(1000);
            herb.jewelL.setPosition(0);
            encoderDrive(0.3, 0.8, 0.8, 10);

        }
        else {
            telemetry.addData("Drive", "Do Not Drive");
            telemetry.update();

        }
        
        sleep(1000);
        
        //Glyph
        if (key == 1){
            //Left coloumn
            encoderDrive(0.3, -0.5, -0.5, 5);
            sleep(200);
            encoderDrive(0.5, -16, 16, 20);
            sleep(200);

            herb.output(0.9);
            sleep(300);
            herb.stop();

            encoderDrive(0.3, 4, -4, 10);

        }
        else if (key == 2){
            //Centre column

            encoderDrive(0.3, -1, -1, 5);
            sleep(200);
            encoderDrive(0.5, -16, 16, 20);
            sleep(200);

            herb.output(0.9);
            sleep(300);
            herb.stop();

            encoderDrive(0.3, 4, -4, 10);

        }
        else if (key == 3){
            //Right column

            encoderDrive(0.3, -1.4, -1.4, 5);
            sleep(200);
            encoderDrive(0.5, -19, 19, 30);
            sleep(200);

            herb.output(0.9);
            sleep(300);
            herb.stop();

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
            newLeftTarget = herb.driveL.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = herb.driveR.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            herb.driveL.setTargetPosition(newLeftTarget);
            herb.driveR.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            herb.driveL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            herb.driveR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            herb.driveL.setPower(Math.abs(speed));
            herb.driveR.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (herb.driveL.isBusy() && herb.driveR.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Run Status", "Running");
                telemetry.update();
            }

            herb.driveL.setPower(0);
            herb.driveR.setPower(0);

        }
    }

}
