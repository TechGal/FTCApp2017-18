package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Aila on 2018-01-30.
 */

@TeleOp
public class OpModeTele extends LinearOpMode {

    Felix vanilla = new Felix();

    @Override
    public void runOpMode() throws InterruptedException {

        double left;
        double right;

        double wheelL;
        double wheelR;

        boolean JUp = false;

        vanilla.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()){
            left = gamepad1.left_stick_y;
            right = -gamepad1.right_stick_y;
            vanilla.driveL.setPower(left);
            vanilla.driveR.setPower(right);

            wheelL = gamepad2.left_stick_y;
            wheelR = -gamepad2.right_stick_y;
            vanilla.intakeL.setPower(wheelL);
            vanilla.intakeR.setPower(wheelR);

            if (gamepad2.left_trigger > 0.01) {
                vanilla.glifter.setPower(-0.5);
            } else if (gamepad2.left_bumper){
                vanilla.glifter.setPower(0.5);
            } else {
                vanilla.glifter.setPower(0);
            }

            if(gamepad2.right_bumper && !JUp){
                vanilla.jewelL.setPosition(0);
                vanilla.jewelR.setPosition(1);
                JUp = true;
            } else if (gamepad2.right_bumper && JUp){
                vanilla.jewelL.setPosition(1);
                vanilla.jewelR.setPosition(0);
                JUp = false;
            }
        }


    }

}
