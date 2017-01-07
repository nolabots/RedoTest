package org.usfirst.frc.team5953.robot;

import java.awt.Image;
import java.nio.ByteBuffer;

import org.usfirst.frc.team5953.controller.XboxController;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import edu.wpi.first.wpilibj.vision.USBCamera;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.internal.HardwareHLUsageReporting;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.livewindow.*;

//http://decibel.ni.com/content/servlet/JiveServlet/download/14730-3-26962/%5BFRC%202011%5D%20Line%20Following%20Tutorial.pdf
//https://www.chiefdelphi.com/forums/archive/index.php/t-91803.html

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public RobotDrive myRobot;
	XboxController xbox;
	Joystick stick;
	int autoLoopCounter;
	OI oi;
	boolean teleop;
	boolean auto;
	Servo servo;
	USBCamera camera;
	HardwareHLUsageReporting report;
	BuiltInAccelerometer accelMeter;
	//https://mililanirobotics.gitbooks.io/frc-electrical-bible/content/Sensors/roborioaccelerometer.html
	private Gyro gyro;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	myRobot = new RobotDrive(0,1);
    	xbox = new XboxController(0);
    	oi = new OI(this);
    	servo = new Servo(7);
    	camera = new USBCamera();
    	report = new HardwareHLUsageReporting();
    	accelMeter = new BuiltInAccelerometer();
    	gyro = new AnalogGyro(1);//http://wpilib.screenstepslive.com/s/3120/m/7912/l/85772-gyros-to-control-robot-driving-direction
    }
    
    
    public void vision (){
    	ByteBuffer buf = ByteBuffer.allocate(10000);//bytes per pixel, pixels per inch, inch LxW of video screen.
    	camera.getImageData(buf);
    }
    
    public void report (){
    	report.reportSmartDashboard();
    }
    
    public void accelerometer(){
    	accelMeter.getZ();
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
    		servo.setAngle(5);
			myRobot.drive(0.5, 1.0); 	// drive forwards half speed
			autoLoopCounter++;
		} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
			servo.setAngle(90);
		}
    	autoLoopCounter ++;
    	
    	if(autoLoopCounter < 300 && autoLoopCounter > 100){
    	}
    	    	
    	
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    	System.out.println("Teleop Enabled:  DRIVE AT YOUR OWN RISK");

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        myRobot.tankDrive(-xbox.getLeftStickY(), -xbox.getRightStickY());
        if(xbox.getAButton()){
        	servo.setAngle(0);
        } else {
        	servo.setAngle(90);
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    	
    public void driveForward(){ 
			myRobot.drive(0.5, 0.0); 	// drive forwards half speed
			autoLoopCounter++;

    }
    
    public void makeItStop(){
		myRobot.drive(0.0, 0.0); 	// stop robot
    }
    
}
