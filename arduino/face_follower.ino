int centerx;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

pinMode(6,OUTPUT);           //motor1       
pinMode(9,OUTPUT);           //motor1
pinMode(10,OUTPUT);          //motor2
pinMode(11,OUTPUT);          //motor2

}

void loop() {
	if(Serial.available()>0){
		char cx=Serial.read();
		centerx=(int)cx;
		Serial.print(centerx);
		 if(centerx==48)
		 {
			analogWrite(6,0);
			analogWrite(9,45);
			analogWrite(11,45);
			analogWrite(10,0);
		}
		else if (centerx==49){
			analogWrite(6,45);
			analogWrite(9,0);
			analogWrite(11,0);
			analogWrite(10,45);
		}

		else if(centerx==50)
		{
			analogWrite(6,0);
			analogWrite(9,0);
			analogWrite(11,0);
			analogWrite(10,0);
		}
    }
}

