package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Aila on 2018-02-05.
 */

public class AnalyzeJewelConfig extends LinearOpMode{

    private static final String TAG = "Jewel Config";

    public static int jewelConfig (int r, int g, int b) {

        //Will return 1 for red, 2 for blue, 0 for any errors

        float hsvValues[] = {0F, 0F, 0F};

        final float values[] = hsvValues;

        final double SCALE_FACTOR = 255;

        Color.RGBToHSV((int) (r * SCALE_FACTOR),
                (int) (g * SCALE_FACTOR),
                (int) (b * SCALE_FACTOR),
                hsvValues);

        int h = (int) hsvValues[0];

        if (h < 250) {
            if (h > 150) {
                Log.i(TAG, "Blue");
                return 2;
            }
            else if (h < 30) {
                Log.i(TAG, "Red");
                return 1;
            }
            else {
                Log.i(TAG, "None");
                return 0;
            }
        }
        else {
            Log.i(TAG, "Red");
            return 1;
        }

    }

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Running", "Program does not run, what are you doing?!?!?!");
            telemetry.update();
        }
    }
}
