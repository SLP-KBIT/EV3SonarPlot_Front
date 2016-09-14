package project.task;

import lejos.hardware.lcd.LCD;
import project.device.LeftMotor;
import project.device.RightMotor;

public class DriveTask implements Runnable {

	RightMotor motorR = RightMotor.getInstance();
	LeftMotor motorL = LeftMotor.getInstance();

	static final int ROTATE_PWM_VALUE = 30;
	static final int ROTATE_ANGLE = 680;

    /**
     * EV3本体の制御。
     */
    @Override
    public void run() {
    	LCD.clear();
    	LCD.drawString("Right:" + motorR.getTachoCount(), 0, 0);
    	LCD.drawString("Left:" + motorL.getTachoCount(), 0, 1);
    	if(motorR.getTachoCount() < ROTATE_ANGLE) {
    		rotate(ROTATE_PWM_VALUE);
    	} else {
    		rotate(0);
    	}

    }

    private void rotate(int value) {
    	motorR.controlMotor(value);
    	motorL.controlMotor(-value);
    }
}
