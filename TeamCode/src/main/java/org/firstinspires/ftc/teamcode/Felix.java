package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Aila on 2018-01-30.
 */

public class Felix {

    public DcMotor driveL = null;
    public DcMotor driveR = null;

    public DcMotor glifter = null;

    public DcMotor lights = null;

    public DcMotor intakeL = null;
    public DcMotor intakeR = null;

    public Servo jewelL = null;
    public Servo jewelR = null;

    public ColorSensor colourSensorLeft;
    public ColorSensor colourSensorRight;
    public BNO055IMU imu;

    HardwareMap hwmap = null;


    public void init(HardwareMap hwMap){

        hwmap  = hwMap;

        driveL = hwmap.get(DcMotor.class, "driveL");
        driveR = hwmap.get(DcMotor.class, "driveR");

        glifter = hwmap.get(DcMotor.class, "glifter");

        lights = hwMap.get(DcMotor.class, "lights");

        intakeL = hwmap.get(DcMotor.class, "intakeL");
        intakeR = hwmap.get(DcMotor.class, "intakeR");

        jewelL = hwmap.get(Servo.class, "jewelL");
        jewelR = hwmap.get(Servo.class, "jewelR");

        driveL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        glifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        colourSensorLeft = hwmap.get(ColorSensor.class, "colourSensorLeft");
        colourSensorRight = hwmap.get(ColorSensor.class, "colourSensorRight");
        imu = hwmap.get(BNO055IMU.class, "imu");

        driveL.setPower(0);
        driveR.setPower(0);
        glifter.setPower(0);
        lights.setPower(1);
        intakeL.setPower(0);
        intakeR.setPower(0);

        jewelL.setPosition(0);
        jewelR.setPosition(1);

    }

    public void drive (double power) {
        driveL.setPower(-power);
        driveR.setPower(power);
    }

    public void intake (double power) {
        intakeL.setPower(power);
        intakeR.setPower(-power);
    }

    public void output (double power) {
        intakeL.setPower(-power);
        intakeR.setPower(power);
    }

    public void stop () {

        driveL.setPower(0);
        driveR.setPower(0);

        glifter.setPower(0);

        intakeL.setPower(0);
        intakeR.setPower(0);

        jewelL.setPosition(0);
        jewelR.setPosition(1);

    }

}