package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Aila on 2018-01-30.
 */

@Autonomous (name = "ColourSensorTest", group = "Felix")
public class ColourSensorTest extends LinearOpMode {

    Felix bot = null;

    @Override
    public void runOpMode() {

        bot = new Felix();
        bot.init(hardwareMap);

        ColorSensor sensor = bot.colourSensorLeft;

        float hsvValues[] = {0F, 0F, 0F};

        final float values[] = hsvValues;

        final double SCALE_FACTOR = 255;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        waitForStart();

        while (opModeIsActive()) {

            Color.RGBToHSV((int) (sensor.red() * SCALE_FACTOR),
                    (int) (sensor.green() * SCALE_FACTOR),
                    (int) (sensor.blue() * SCALE_FACTOR),
                    hsvValues);

            telemetry.addData("Alpha", sensor.alpha());
            telemetry.addData("Red  ", sensor.red());
            telemetry.addData("Green", sensor.green());
            telemetry.addData("Blue ", sensor.blue());
            telemetry.addData("Hue", hsvValues[0]);


            telemetry.update();
        }

    }
}
