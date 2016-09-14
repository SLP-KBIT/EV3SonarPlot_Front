package project.task;

import lejos.hardware.lcd.LCD;
import project.device.LeftMotor;
import project.device.RightMotor;

public class DriveTask implements Runnable {

	RightMotor motorR = RightMotor.getInstance();
	LeftMotor motorL = LeftMotor.getInstance();

	static final int ROTATE_PWM_VALUE = 30;
	static final int ROTATE_MOTOR_ANGLE = 680;

    /**
     * EV3本体の制御。
     */
    @Override
    public void run() {
    	LCD.clear();
    	LCD.drawString("Right:" + motorR.getTachoCount(), 0, 0);
    	LCD.drawString("Left:" + motorL.getTachoCount(), 0, 1);
    	LCD.drawString("Angle:" + calcAngle(), 0, 2);

    	if(motorR.getTachoCount() < ROTATE_MOTOR_ANGLE) {
    		motorRotate(ROTATE_PWM_VALUE);
    	} else {
    		motorStop();
    	}
    }

    private void motorRotate(int value) {
    	motorR.controlMotor(value);
    	motorL.controlMotor(-value);
    }

    private void motorStop() {
    	motorR.controlMotor(0);
    	motorL.controlMotor(0);
    }

    private int calcAngle() {
    	double comp = ROTATE_MOTOR_ANGLE;
    	double current = motorR.getTachoCount();
    	double percent = current /comp;

    	return (int)(360.0 * percent);
    }
}
