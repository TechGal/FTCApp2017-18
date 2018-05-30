package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Aila on 2018-02-09.
 */

@Autonomous(name="Servo", group="Felix")
public class Servo extends LinearOpMode {

    Felix bot = null;

    @Override
    public void runOpMode() throws InterruptedException {

        bot = new Felix();
        bot.init(hardwareMap);

        double position = 0;

        waitForStart();

        while (position < 1.1) {
            bot.jewelL.setPosition(position);
            telemetry.addData("Jewel: ", position);
            telemetry.update();
            sleep(2000);
            position = position + 0.1;
        }

        position = 1;

        while (position > 0) {
            bot.jewelR.setPosition(position);
            telemetry.addData("Jewel: ", position);
            telemetry.update();
            sleep(2000);
            position = position - 0.1;
        }

    }
}
