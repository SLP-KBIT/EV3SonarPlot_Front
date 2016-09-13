package project.device;

import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class SonarSensor {
	private static SonarSensor sonarSensor = new SonarSensor();

    private Port  SENSORPORT_SONAR     = SensorPort.S2;  // 超音波センサーポート

    // 超音波センサ
    static EV3UltrasonicSensor sonar;
    static SampleProvider distanceMode;  // 距離検出モード
    static float[] sampleDistance;

    private SonarSensor() {
        sonar = new EV3UltrasonicSensor(SENSORPORT_SONAR);
        distanceMode = sonar.getDistanceMode(); // 距離検出モード
        sampleDistance = new float[distanceMode.sampleSize()];
        sonar.enable();
    }

    public static SonarSensor getInstance() {
    	return sonarSensor;
    }

    public float getDistance() {
        distanceMode.fetchSample(sampleDistance, 0);
        return sampleDistance[0];
    }
}
