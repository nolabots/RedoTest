package org.usfirst.frc.team5953.robot;

import org.usfirst.frc.team5953.controller.XboxController;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

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
	int autoLoopCounter;
	OI oi;
	boolean teleop;
	boolean auto;
	Servo servo;
	VictorSP ballControl;
	double angle;
	double rate;
	boolean driveLeft;
	
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	myRobot = new RobotDrive(0,1);
    	xboxBlack = new XboxController(0);
    	xboxClear = new XboxController(1);
    	gyro = new ADXRS450_Gyro();
    	oi = new OI(this);
    	servo = new Servo(7);
    	ballControl = new VictorSP(4);
    	angle = 0.0;
    	rate = 0.0;
    	
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
	    driveLeft = false;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
//    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
//		{
//    		servo.setAngle(5);
//			myRobot.drive(0.5, 1.0); 	// drive forwards half speed
//			autoLoopCounter++;
//		} else {
//			myRobot.drive(0.0, 0.0); 	// stop robot
//			servo.setAngle(90);
//		}
//    	autoLoopCounter ++;
//    	
//    	if(autoLoopCounter < 300 && autoLoopCounter > 100){
//    	}
//    	   
		//set gyro to 0
		gyro.calibrate();
		
    	while(true){

    		//keep track of gyro's rate
    		System.out.println("Rate: " + gyro.getRate());
    		System.out.println("Angle: " + gyro.getAngle()); 

    		angle = gyro.getAngle();
    		rate = gyro.getRate();
    	    		
    	    //turn a certain distance
    	    myRobot.drive(0.25, 1);

    	    
    	    if(angle >= 90){
    	    	driveLeft = true;
    	    }
    	    
    	    if(angle <= -90){
    	    	driveLeft = false;
    	    }
    	    
    	    while(driveLeft){
    	    	myRobot.drive(0.25, -1);
    	    }
    	    
    	    while(!driveLeft){
    	    	myRobot.drive(0.25, 1);
    	    }  
    	    
    	    //at the beginning, turn right
    	    //after 90 degrees, turn left
    	    //after -180 degrees, turn right or turn to 270/-90
    	    //reach a threshold and turn the other direction
    	    //myRobot.drive(0.25, -1);
    		
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
        myRobot.tankDrive(Math.pow(-xboxClear.getLeftStickY(), 3), 
        				  Math.pow(-xboxClear.getRightStickY(), 3));
        if(xboxClear.getAButton()){
        	servo.setAngle(0);
        } else {
        	servo.setAngle(90);
        }
        if(xboxClear.getRightBumper()){
        	ballControl.set(1);
        } else {
        	ballControl.set(0);
        }
        
        if(xboxClear.getLeftBumper()){
        	ballControl.set(-1);
        } else {
        	ballControl.set(0);
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
