#include <SPI.h>
#include <MFRC522.h>

#define RST_PIN   9     // reset핀 설정
#define SS_PIN    10    // 데이터를 주고받는 역할의 핀( SS = Slave Selector )

MFRC522 mfrc(SS_PIN, RST_PIN);           // 이 코드에서 MFR522를 이용하기 위해 mfrc객체를 생성해 줍니다.

//int LED_Y = 3;                            // LED를 3번핀에 연결합니다.
int LED_R = 4;                            // LED를 4번핀에 연결합니다.
int buzzer = 6;                            // 부저를 6번핀에 연결합니다.

void setup() {
  Serial.begin(9600);                     // 시리얼 통신, 속도는 9600
  SPI.begin();                             // SPI 초기화(SPI : 하나의 마스터와 다수의 SLAVE(종속적인 역활)간의 통신 방식)
  
  //pinMode(LED_Y, OUTPUT);                 // 3번핀을 출력으로 설정
  pinMode(LED_R, OUTPUT);                 // 4번핀을 출력으로 설정
  pinMode(buzzer, OUTPUT);                 // 6번핀을 출력으로 설정
}

void loop() {
  if ( ! mfrc.PICC_IsNewCardPresent() || ! mfrc.PICC_ReadCardSerial() ) {    //  태그 접촉이 되지 않았을때 또는 아이디가 읽혀지지 않았을때
    delay(500);       
    return;                                   // return
  }

  for (byte i = 0; i < 4; i++) {               // 태그의 ID출력하는 반복문.태그의 ID사이즈(4)까지
  Serial.print(mfrc.uid.uidByte[i]);        // mfrc.uid.uidByte[0] ~ mfrc.uid.uidByte[3]까지 출력
  Serial.print(" ");                        // id 사이의 간격 출력
  }
  Serial.println(); 
    
  if(mfrc.uid.uidByte[0] == 181 && mfrc.uid.uidByte[1] == 100 
       && mfrc.uid.uidByte[2] == 1 && mfrc.uid.uidByte[3] == 109) {    // 2번 태그 ID가 맞을경우
    //digitalWrite(LED_Y, HIGH);                // 3번핀 에 연결된 led 켜지기 
    //digitalWrite(LED_R, LOW);                 // 4번핀 에 연결된 led 꺼지기 
    digitalWrite(LED_R, HIGH);                 // 4번핀 에 연결된 led 켜지기 
    Serial.println("Hello, Eduino~");        // 시리얼 모니터에 "Hello, Eduino~" 출력
    tone(6,523,100);                 
    delay(500);
    
  }else {                                   // 다른 태그 ID일 경우
    //digitalWrite(LED_R, HIGH);              // 3번핀 에 연결된 led 켜지기 
    //digitalWrite(LED_Y, LOW);               // 4번핀 에 연결된 led 꺼지기
    digitalWrite(LED_R, LOW);               // 4번핀 에 연결된 led 꺼지기
    Serial.println("Who are you?");        // 시리얼 모니터에 "Who are you?" 출력 
    tone(6,523,100);                
    delay(300);
    tone(6,523,100);
    delay(500);
  }  
} 
