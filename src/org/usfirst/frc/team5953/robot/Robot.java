package org.usfirst.frc.team5953.robot;

import org.usfirst.frc.team5953.controller.XboxController;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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

	Joystick stick;
	int autoLoopCounter;
	OI oi;
	boolean teleop;
	boolean auto;
	Servo servo;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	myRobot = new RobotDrive(0,1);
    	xboxBlack = new XboxController(0);
    	xboxClear = new XboxController(1);

    	oi = new OI(this);
    	servo = new Servo(7);
    	
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
        myRobot.tankDrive(-xboxClear.getLeftStickY(), -xboxClear.getRightStickY());
        if(xboxClear.getAButton()){
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
