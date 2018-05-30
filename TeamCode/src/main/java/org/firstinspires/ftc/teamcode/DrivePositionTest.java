package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Aila on 2018-02-05.
 */

@Autonomous(name = "DrivePositionTest", group = "Felix")
public class DrivePositionTest extends LinearOpMode{

    Felix fudge = null;

    @Override
    public void runOpMode() throws InterruptedException {

        fudge = new Felix();
        fudge.init(hardwareMap);

        waitForStart();

        fudge.drive(0.2);
        sleep(200);
        fudge.drive(0);
        sleep(500);
        fudge.drive(-0.2);
        sleep(200);

        fudge.jewelR.setPosition(0);
        sleep(500);
        fudge.jewelR.setPosition(1);

        fudge.jewelL.setPosition(0);
        sleep(500);
        fudge.jewelL.setPosition(1);

    }
}
