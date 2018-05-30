package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Aila on 2018-02-05.
 */

@Autonomous(name = "RedJewelSafe", group = "Felix")
public class RedJewelSafe extends LinearOpMode {

    Felix chocolate = null;

    @Override
    public void runOpMode() throws InterruptedException {

        chocolate = new Felix();
        chocolate.init(hardwareMap);

        ColorSensor sensor = chocolate.colourSensorRight;

        double jewelDown = 0.2;

        float hsvValues[] = {0F, 0F, 0F};

        final float values[] = hsvValues;

        final double SCALE_FACTOR = 255;

        Color.RGBToHSV((int) (sensor.red() * SCALE_FACTOR),
                (int) (sensor.green() * SCALE_FACTOR),
                (int) (sensor.blue() * SCALE_FACTOR),
                hsvValues);

        waitForStart();

        chocolate.jewelR.setPosition(jewelDown);
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
            telemetry.addData("Drive", "Forwards");
            telemetry.update();

            sleep(2000);

            chocolate.drive(0.2);
            sleep(450);
            chocolate.stop();

            chocolate.jewelR.setPosition(1);
            sleep(1000);

            chocolate.drive(-0.2);
            sleep(500);
            chocolate.stop();

            chocolate.drive(0.7);
            sleep(300);
            chocolate.stop();

            sleep(1000);

            chocolate.output(0.7);
            sleep(300);
            chocolate.stop();

        }
        else if (driveCommand == 2){
            telemetry.addData("Drive", "Reverse");
            telemetry.update();

            sleep(2000);

            chocolate.drive(-0.2);
            sleep(550);
            chocolate.stop();

            chocolate.jewelR.setPosition(1);
            sleep(1000);

            chocolate.drive(0.2);
            sleep(600);
            chocolate.stop();

            chocolate.drive(0.7);
            sleep(800);
            chocolate.stop();

            sleep(1000);

            chocolate.output(0.7);
            sleep(300);
            chocolate.stop();

        }
        else {
            telemetry.addData("Drive", "Do Not Drive");

            chocolate.drive(0.7);
            sleep(600);
            chocolate.stop();
        }

    }
}
