#include <Servo.h>
Servo myservo;                            
int pos = 90;   

// the setup function runs once when you press reset or power the board
void setup() {
	myservo.attach(5); // attaches the servo on pin 9 to the servo object
	pinMode(3,OUTPUT);   //motor1
	pinMode(10,OUTPUT);   //motor1
	pinMode(6,OUTPUT);  //motor2
	pinMode(11,OUTPUT);  //motor2
	Serial.begin(38400);
}

// the loop function runs over and over again forever
void loop() {
	if(Serial.available()>0){
		char i=Serial.read();
		int x=(int)i;
		Serial.println(x);
	    
		if(i=='1'){
			Serial.println(i);//FORWARD
			analogWrite(6,0);
			analogWrite(10,255);
			analogWrite(3,0);
			analogWrite(11,255);
		}
		
		else if(i=='2'){ 
			Serial.println(i);//BACKWARD
			analogWrite(6,255);
			analogWrite(10,0);
			analogWrite(3,255);
			analogWrite(11,0);  
		}

		else if(i=='3'){       //LEFT
			Serial.println(i);
			digitalWrite(6,0);
			digitalWrite(10,1);
			digitalWrite(3,1);
			digitalWrite(11,0);
		}

		else if(i=='7'){       //LEFT
			Serial.println(i);
			analogWrite(6,0);
			analogWrite(10,0);
			analogWrite(3,90);
			analogWrite(11,0);
		}
		
		else if(i=='4'){      //RIGHT
			Serial.println(i);
			digitalWrite(6,1);
			digitalWrite(10,0);
			digitalWrite(3,0);
			digitalWrite(11,1);
		}

		else if(i=='6'){     //RIGHT
			Serial.println(i);
			analogWrite(6,0);
			analogWrite(10,0);
			analogWrite(3,0);
			analogWrite(11,90);
		}
		 
		else if(i=='0'){       //STOP
			Serial.println(i);
			analogWrite(6,0);
			analogWrite(10,0);
			analogWrite(3,0);
			analogWrite(11,0);
		}


		else if(i=='5'){
			 for (pos = 90; pos >= 60; pos --) {    
				 myservo.write(pos);                  
				 delay(15);                           
			 }
			 for (pos = 30; pos <= 90; pos ++) {   // goes from 90 degrees to 0 degrees
				 myservo.write(pos);                   // tell servo to go to position in variable 'pos'
				 delay(15);                            // waits 15ms for the servo to reach the position
			 } 
		}

		else if(i=='6'){
			 for (pos = 90; pos <= 120; pos ++) {    
				 myservo.write(pos);                  
				 delay(15);                           
			 }
			 for (pos = 120; pos >= 90; pos --) {   // goes from 180 degrees to 0 degrees
				 myservo.write(pos);                   // tell servo to go to position in variable 'pos'
				 delay(15);                            // waits 15ms for the servo to reach the position
			 } 
		}
	}
}

