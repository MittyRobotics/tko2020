package com.github.mittyrobotics.shooter;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;

public class TurretGyro {
    //Global Variables
    private static double GRAVITIY_MS2 = 9.80665;
    private I2C device;

    //Scale Modifiers
    private static double ACCEL_SCALE_MODIFIER_2G = 16384.0;
    private static double ACCEL_SCALE_MODIFIER_4G = 8192.0;
    private static double ACCEL_SCALE_MODIFIER_8G = 4096.0;
    private static double ACCEL_SCALE_MODIFIER_16G = 2048.0;

    private static double GYRO_SCALE_MODIFIER_250DEG = 131.0;
    private static double GYRO_SCALE_MODIFIER_500DEG = 65.5;
    private static double GYRO_SCALE_MODIFIER_1000DEG = 32.8;
    private static double GYRO_SCALE_MODIFIER_2000DEG = 16.4;

    // Pre-defined Ranges
    public static byte ACCEL_RANGE_2G = 0x00;
    public static byte ACCEL_RANGE_4G = 0x08;
    public static byte ACCEL_RANGE_8G = 0x10;
    public static byte ACCEL_RANGE_16G = 0x18;

    public static byte GYRO_RANGE_250DEG = 0x00;
    public static byte GYRO_RANGE_500DEG = 0x08;
    public static byte GYRO_RANGE_1000DEG = 0x10;
    public static byte GYRO_RANGE_2000DEG = 0x18;

    // MPU-6050 Registers
    private static byte PWR_MGMT_1 = 0x6B;
    private static byte PWR_MGMT_2 = 0x6C;

    private static byte ACCEL_XOUT0 = 0x3B;
    private static byte ACCEL_YOUT0 = 0x3D;
    private static byte ACCEL_ZOUT0 = 0x3F;

    private static byte TEMP_OUT0 = 0x41;

    private static byte GYRO_XOUT0 = 0x43;
    private static byte GYRO_YOUT0 = 0x45;
    private static byte GYRO_ZOUT0 = 0x47;

    private static byte ACCEL_CONFIG = 0x1C;
    private static byte GYRO_CONFIG = 0x1B;
    private double gyroAngularSpeedOffsetX;
    private double gyroAngularSpeedOffsetY;
    private double gyroAngularSpeedOffsetZ;


    public TurretGyro() throws Exception{
        this.init();
    }

    private void init() throws Exception{
        this.device = new I2C(I2C.Port.kOnboard, (byte)0x68);
        // Wake up the MPU6050 from sleep since it starts in sleep mode by default.
        device.write(PWR_MGMT_1, (byte) 0x00);

    }

    //General i2c Communication Methods

    /**
     * Reads a word (16 bits) from the passed register and the register after. The data is combine.
     * @param register Place that is being read from.
     * @return The value of the word read from the register.
     * @throws Exception FIXME passes all errors onto the surrounding program.
     */
    public int read_i2c_word(int register) throws Exception {
        byte[] highData = new byte[1];
        byte[] lowData = new byte[1];
        device.read(register, 1, highData);
        device.read(register+1, 1, lowData);

        int high = highData[0];
        int low = lowData[0];

        int value = (high << 8) + low;

        if(value >= 0x8000) {
            return -((65535 - value) + 1);
        }
        else {
            return value;
        }
    }

    //MPU6050 Methods

    /**
     * Reads the temperature from the MPU6050's onboard sensor.
     * @return The temperature in degrees Celcius.
     * @throws Exception FIXME passes all errors onto the surrounding program.
     */
    public double get_temp() throws Exception{
        int rawTemp = read_i2c_word(TEMP_OUT0);
        double actualTemp = (rawTemp / 340) + 36.53;
        return actualTemp;
    }

    /**
     * Sets the range of the accelerometer.
     * @param accelRange The range that the accelerometer is set to. Using one of the predefined ranges is advised.
     * @throws Exception FIXME passes all errors onto the surrounding program.
     */
    public void set_accel_range(byte accelRange) throws Exception {
        device.write(ACCEL_CONFIG, (byte)0x00);
        device.write(ACCEL_CONFIG,accelRange);
    }

    /**
     * Reads the raw accelerometer range.
     * @return The raw value from the ACCEL_COMFIG register.
     * @throws Exception FIXME passes all errors onto the surrounding program.
     */
    public double read_raw_accel_range() throws Exception {
        byte[] data = new byte[1];
        device.read(ACCEL_CONFIG, 1, data);
        return data[0];
    }

    /**
     * Reads the accelerometer range in terms of gravity.
     * @return Returns an integer: -1, 2, 4, 8, 16. If -1 an error occurred.
     * @throws Exception FIXME passes all errors onto the surrounding program.
     */
    public int read_accel_range() throws Exception {
        double rawData = read_raw_accel_range();

        if (rawData == ACCEL_RANGE_2G) {
            return 2;
        }
        else if (rawData == ACCEL_RANGE_4G) {
            return 4;
        }
        else if (rawData == ACCEL_RANGE_8G) {
            return 8;
        }
        else if (rawData == ACCEL_RANGE_16G) {
            return 16;
        }
        else {
            return -1;
        }
    }

    /**
     * Gets the x, y, and z accelerometer data.
     * @return Returns x, y, z in a double array [x, y, z]. All values are in m/s^2.
     * @throws Exception FIXME passes all errors onto the surrounding program.
     */
    public double[] get_accel_data() throws Exception{
        return get_accel_data(false);
    }

    /**
     * Gets the x, y, and z accelerometer data.
     * @param g If true: returned values are in terms of gravity(g). If false: returned values are in terms of m/s^2.
     * @return Returns x, y, z in a double array [x, y, z].
     * @throws Exception FIXME passes all errors onto the surrounding program.
     */
    public double[] get_accel_data(boolean g) throws Exception{
        double x = read_i2c_word(ACCEL_XOUT0);
        double y = read_i2c_word(ACCEL_YOUT0);
        double z = read_i2c_word(ACCEL_ZOUT0);

        double accel_scale_modifier;
        double accel_range = read_raw_accel_range();

        if (accel_range == ACCEL_RANGE_2G) {
            accel_scale_modifier = ACCEL_SCALE_MODIFIER_2G;
        }
        else if (accel_range == ACCEL_RANGE_4G) {
            accel_scale_modifier = ACCEL_SCALE_MODIFIER_4G;
        }
        else if (accel_range == ACCEL_RANGE_8G) {
            accel_scale_modifier = ACCEL_SCALE_MODIFIER_8G;
        }
        else if (accel_range == ACCEL_RANGE_16G) {
            accel_scale_modifier = ACCEL_SCALE_MODIFIER_16G;
        }
        else {
            System.out.println("Unkown range - accel_scale_modifier set to self.ACCEL_SCALE_MODIFIER_2G\"");
            accel_scale_modifier = ACCEL_SCALE_MODIFIER_2G;
        }

        x = x / accel_scale_modifier;
        y = y / accel_scale_modifier;
        z = z / accel_scale_modifier;

        if(g == false) {
            x = x * GRAVITIY_MS2;
            y = y * GRAVITIY_MS2;
            z = z * GRAVITIY_MS2;
        }

        return new double[] {x,y,z};
    }

    /**
     * Sets the range of the Gyroscope.
     * @param gyroRange The range to set the gyroscope to. Using a predefined range is advised.
     * @throws Exception FIXME passes all errors onto the surrounding program.
     */
    public void set_gyro_range(byte gyroRange) throws Exception {
        device.write(GYRO_CONFIG, (byte)0x00);
        device.write(GYRO_CONFIG, gyroRange);
    }

    /**
     * Reads the raw range of the gyroscope.
     * @return Returns the raw value from the GYRO_CONFIG register.
     * @throws Exception FIXME passes all errors onto the surrounding program.
     */
    public double read_raw_gyro_range() throws Exception {
        byte[] data = new byte[1];
        device.read(GYRO_CONFIG, 1, data);
        return data[0];
    }

    /**
     * Reads the range of the gyroscope.
     * @return Returns an integer: -1, 250, 500, 1000, and 2000. If -1 is returned something bad has happened.
     * @throws Exception FIXME passes all errors onto the surrounding program.
     */
    public double read_gyro_range() throws Exception {
        double rawData = read_raw_gyro_range();

        if(rawData == GYRO_RANGE_250DEG){
            return 250;
        }
        else if(rawData == GYRO_RANGE_500DEG) {
            return 500;
        }
        else if (rawData == GYRO_RANGE_1000DEG) {
            return 1000;
        }
        else if (rawData == GYRO_RANGE_2000DEG) {
            return 2000;
        }
        else {
            return -1;
        }
    }

    public void calibrateSensors() {
        System.out.println("Calibration Started");
        int nbReadings = 50;

        // Gyroscope offsets
        gyroAngularSpeedOffsetX = 0.;
        gyroAngularSpeedOffsetY = 0.;
        gyroAngularSpeedOffsetZ = 0.;
        for(int i = 0; i < nbReadings; i++) {
            double[] angularSpeeds = new double[0];
            try {
                angularSpeeds = get_gyro_data();
            } catch (Exception e) {
                e.printStackTrace();
            }
            gyroAngularSpeedOffsetX += angularSpeeds[0];
            gyroAngularSpeedOffsetY += angularSpeeds[1];
            gyroAngularSpeedOffsetZ += angularSpeeds[2];
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gyroAngularSpeedOffsetX /= nbReadings;
        gyroAngularSpeedOffsetY /= nbReadings;
        gyroAngularSpeedOffsetZ /= nbReadings;
        System.out.println("Calibration Ended");
    }

    /**
     * Gets the data from the gyroscope.
     * @return Returns the values as a double array: [x, y, z].
     * @throws Exception FIXME passes all errors onto the surrounding program.
     */
    public double[] get_gyro_data() throws Exception{
        double x = read_i2c_word(GYRO_XOUT0);
        double y = read_i2c_word(GYRO_YOUT0);
        double z = read_i2c_word(GYRO_ZOUT0);

        double gyro_scale_modifier;
        double gyro_range = read_raw_gyro_range();

        if (gyro_range == GYRO_RANGE_250DEG) {
            gyro_scale_modifier = GYRO_SCALE_MODIFIER_250DEG;
        }
        else if (gyro_range == GYRO_RANGE_500DEG) {
            gyro_scale_modifier = GYRO_SCALE_MODIFIER_500DEG;
        }
        else if (gyro_range == GYRO_RANGE_1000DEG) {
            gyro_scale_modifier = GYRO_SCALE_MODIFIER_1000DEG;
        }
        else if (gyro_range == GYRO_RANGE_2000DEG) {
            gyro_scale_modifier = GYRO_SCALE_MODIFIER_2000DEG;
        }
        else {
            System.out.println("Unkown range - gyro_scale_modifier set to self.GYRO_SCALE_MODIFIER_250DEG");
            gyro_scale_modifier = GYRO_SCALE_MODIFIER_250DEG;
        }

        x = x / gyro_scale_modifier;
        y = y / gyro_scale_modifier;
        z = z / gyro_scale_modifier;

        return new double[] {x,y,z};
    }

    public void start(){
        lastUpdateTime = Timer.getFPGATimestamp();
    }

    public double getGyroAngularSpeedX() {
        return gyroAngularSpeedX;
    }

    public double getGyroAngularSpeedY() {
        return gyroAngularSpeedY;
    }

    public double getGyroAngularSpeedZ() {
        return gyroAngularSpeedZ;
    }

    private double gyroAngularSpeedX;
    private double gyroAngularSpeedY;
    private double gyroAngularSpeedZ;
    private double lastUpdateTime;

    public double getGyroAngleX() {
        return gyroAngleX;
    }

    public double getGyroAngleY() {
        return gyroAngleY;
    }

    public double getGyroAngleZ() {
        return gyroAngleZ;
    }

    private double gyroAngleX;
    private double gyroAngleY;
    private double gyroAngleZ;
    public void updateValues() {

        // Gyroscope
        double[] angularSpeeds = new double[0];
        try {
            angularSpeeds = get_gyro_data();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gyroAngularSpeedX = angularSpeeds[0] - gyroAngularSpeedOffsetX;
        gyroAngularSpeedY = angularSpeeds[1] - gyroAngularSpeedOffsetY;
        gyroAngularSpeedZ = angularSpeeds[2] - gyroAngularSpeedOffsetZ;
            // angular speed * time = angle
            double dt = Timer.getFPGATimestamp() - lastUpdateTime; // s
            double deltaGyroAngleX = gyroAngularSpeedX * dt;
            double deltaGyroAngleY = gyroAngularSpeedY * dt;
            double deltaGyroAngleZ = gyroAngularSpeedZ * dt;
            lastUpdateTime = Timer.getFPGATimestamp();

            gyroAngleX += deltaGyroAngleX;
            gyroAngleY += deltaGyroAngleY;
            gyroAngleZ += deltaGyroAngleZ;



    }
}
