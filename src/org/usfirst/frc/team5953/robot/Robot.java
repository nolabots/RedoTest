package org.usfirst.frc.team5953.robot;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.usfirst.frc.team5953.controller.XboxController;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public RobotDrive myRobot;
	XboxController xboxBlack;
	XboxController xboxClear;
	Gyro gyro;
	Joystick stick;
	int counter;
	OI oi;
	boolean teleop;
	boolean auto;
	Servo servo;
	VictorSP ballControl;
	double angle;
	double rate;
	boolean driveLeft;
	double startAngle;
	static CameraServer camera;
	UsbCamera camera1;    
    DoubleSolenoid pneumatics;	
	DoubleSolenoid pneumatics2;
    Compressor c;
    boolean toggle;
    boolean solenoidHasBeenFired;
    double kp;
    double range;
    Ultrasonic ultra;
    AnalogInput ultraIn;
    double voltage;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	

	
    public void robotInit() {
    	myRobot = new RobotDrive(0,1);
    	xboxBlack = new XboxController(1);
    	xboxClear = new XboxController(0);
    	gyro = new ADXRS450_Gyro();
    	servo = new Servo(7);
    	ballControl = new VictorSP(4);
    	angle = 0.0;
    	rate = 0.0;
    	initCameraStream();
    	c = new Compressor(0);
    	pneumatics = new DoubleSolenoid(0, 1);
    	pneumatics2 = new DoubleSolenoid(2, 3);
    	toggle = true;
    	solenoidHasBeenFired = false;
    	kp = 0.03;
    }

public void initCameraStream(){
	
    new Thread(() -> {
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();//device number ?
        camera.setResolution(640, 480);
        
        CvSink cvSink = CameraServer.getInstance().getVideo();
        CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
        
        Mat source = new Mat();
        Mat output = new Mat();
        
        while(!Thread.interrupted()) {
            cvSink.grabFrame(source);
            Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
            outputStream.putFrame(output);
        }
    }).start();

}
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	// figure out why it takes so long to start running the script
	    driveLeft = true;
	    gyro.calibrate();
		startAngle = gyro.getAngle();
		counter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    // While(){
    public void autonomousPeriodic() {
    	// start position 1, 2, or 3
    	// 1
    	// shoot balls
    	
    	// 2
    	// gear start position
    	pneumatics.set(DoubleSolenoid.Value.kForward);
    	// drive straight 114.3 in.
    	if (counter < 100){
    		driveStraight(0.5);
    	}
    	// release gear
    	if (counter > 100 && counter < 125)
    	dropGear();
    	// drive backwards 100 in.
    	if (counter > 125 && counter < 225){
    		driveStraight(-0.5);
    	}
    	// rotate right 90 deg.
    	ninetyDegTurn(true);
    	if(gyro.getAngle() >= startAngle + 90 || angle <= gyro.getAngle() - 90){
    		counter = 0;
    	}
    	// drive straight 100 in.
    	if (counter < 100){
    		driveStraight(0.5);
    	}
    	
    	// 3
    	// put gear on back side
    	
        	
        counter ++;
    }
    
    public void driveStraight(double speed) {
    	angle = gyro.getAngle();
    	rate = gyro.getRate();
    	
		System.out.println("Rate: " + rate);
		System.out.println("Angle: " + angle); 
		
    	myRobot.drive(speed, -angle*kp);
    	Timer.delay(0.004);
 
    }
    
    public void dropGear() {
    	pneumatics.set(DoubleSolenoid.Value.kReverse);	
    	
    }
    
    public void ninetyDegTurn(boolean turnRight) {
    	angle = gyro.getAngle();
    	boolean stop = false;
    	
//    	if(angle >= startAngle + 90) {
//    		//stop
//    		turnRight = 2;
//    	}
//    	
//    	if(angle <= startAngle - 90) {
//    		//stop
//    		turnRight = 2;
//    	}
//    	
//    	if(turnRight == 0) {
//    		//turn left
//    		myRobot.tankDrive(-0.25, 0.25, false);
//    	}
//    	
//    	if(turnRight == 1) {
//    		//turn right
//    		myRobot.tankDrive(0.25, -0.25, false);
//    	}
//    	
//    	if(turnRight == 2) {
//    		//stop 
//    		myRobot.tankDrive(0,  0);
//    	}
    	
    	if(angle >= startAngle + 90) {
    		//stop
    		stop = true;
    	}
		
		if(angle <= startAngle - 90) {
			//stop
			stop = true;
		}
		
		if(!turnRight) {
			//turn left
			myRobot.tankDrive(-0.25, 0.25, false);
		}
		
		if(turnRight) {
			//turn right
			myRobot.tankDrive(0.25, -0.25, false);
		}
		
		if(stop) {
			//stop 
			myRobot.tankDrive(0,  0);
		}
    }
    
    public void gyroExample() {
    	
		//keep track of gyro's rate


		angle = gyro.getAngle();
		rate = gyro.getRate();
		
   	System.out.println("startAngle: " + startAngle);
		System.out.println("Rate: " + rate);
		System.out.println("Angle: " + angle); 
	    System.out.println("driveLeft: " + driveLeft);

	    //turn a certain distance
	    
	    if(angle >= startAngle + 90){
	    	driveLeft = false;
	    }
	    
	    if(angle <= startAngle - 90){
	    	driveLeft = true;
	    }
	    
	    if(driveLeft){
	    	myRobot.tankDrive(0.25, -0.25, false);
	    }
	    
	    if(!driveLeft){
	    	myRobot.tankDrive(-0.27, 0.27, false);
	    }      	
    	
    }
    
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    	System.out.println("Teleop Enabled:  DRIVE AT YOUR OWN RISK");
    	oi = new OI(this);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
     oi.checkButtons();
        
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
