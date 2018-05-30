package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Aila on 2018-02-05.
 */

@Autonomous(name = "BlueJewelSafe", group = "Felix")
public class BlueJewelSafe extends LinearOpMode{

    Felix vanilla = null;

    @Override
    public void runOpMode() throws InterruptedException {

        vanilla = new Felix();
        vanilla.init(hardwareMap);

        ColorSensor sensor = vanilla.colourSensorLeft;

        double jewelDown = 0.78;

        float hsvValues[] = {0F, 0F, 0F};

        final float values[] = hsvValues;

        final double SCALE_FACTOR = 255;

        Color.RGBToHSV((int) (sensor.red() * SCALE_FACTOR),
                (int) (sensor.green() * SCALE_FACTOR),
                (int) (sensor.blue() * SCALE_FACTOR),
                hsvValues);

        waitForStart();

        vanilla.jewelL.setPosition(jewelDown);
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

            sleep(2000);

            vanilla.drive(-0.2);
            sleep(550);
            vanilla.stop();

            vanilla.jewelL.setPosition(0);
            sleep(1000);

            vanilla.drive(0.2);
            sleep(600);
            vanilla.stop();

            vanilla.drive(0.7);
            sleep(800);
            vanilla.stop();

            sleep(1000);

            vanilla.output(0.7);
            sleep(300);
            vanilla.stop();

        }
        else if (driveCommand == 2){
            telemetry.addData("Drive", "Forward");
            telemetry.update();

            sleep(2000);

            vanilla.drive(0.2);
            sleep(450);
            vanilla.stop();

            vanilla.jewelL.setPosition(0);
            sleep(1000);

            vanilla.drive(-0.2);
            sleep(500);
            vanilla.stop();

            vanilla.drive(0.7);
            sleep(300);
            vanilla.stop();

            sleep(1000);

            vanilla.output(0.7);
            sleep(300);
            vanilla.stop();

        }
        else {
            telemetry.addData("Drive", "Do Not Drive");
            telemetry.update();

        }

        telemetry.update();
        sleep(3000);

    }
}
