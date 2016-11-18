#include <SPI.h>
#include <String.h>
#include <Ethernet.h>

byte mac[] = { 0x90, 0xA2, 0xDA, 0x00, 0x9B, 0x36 };
byte ip[] = { 192, 168, 100, 10 };
EthernetServer server(8090);

int led1 = 5;
int led2 = 6;
int led3 = 7;

String readString = String(30);

String statusLed;

void setup() {
  Ethernet.begin(mac, ip);
  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(led3, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  
  EthernetClient client = server.available();
  
  if(client) 
  {
    while(client.connected())
    {
      if(client.available()) 
      {
        char c = client.read();
        
        if(readString.length() < 30) {
          readString += (c);
        }
        
        if(c == '\n')
        {
          
          if(readString.indexOf("led1") >= 0) {
            digitalWrite(led1, !digitalRead(led1));
          }
          
          if(readString.indexOf("led2") >= 0) {
            digitalWrite(led2, !digitalRead(led2));
          }
          
          if(readString.indexOf("led3") >= 0) {
            digitalWrite(led3, !digitalRead(led3));
          }
          
          // cabeçalho http padrão
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println();
         
          client.println("<!doctype html>");
          client.println("<html>");
          client.println("<head>");
          client.println("<title>Tutorial</title>");
          client.println("<meta name=\"viewport\" content=\"width=320\">");
          client.println("<meta name=\"viewport\" content=\"width=device-width\">");
          client.println("<meta charset=\"utf-8\">");
          client.println("<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">");
          client.println("<meta http-equiv=\"refresh\" content=\"2.URL=http//192.168.0.6:80\">");
          client.println("</head>");
          client.println("<body>");
          client.println("<center>");
          
          client.println("<font size=\"5\" face=\"verdana\" color=\"green\">Android</font>");
          client.println("<font size=\"3\" face=\"verdana\" color=\"red\"> & </font>");
          client.println("<font size=\"5\" face=\"verdana\" color=\"blue\">Arduino</font><br />");
          
          if(digitalRead(led1)) {
            statusLed = "Ligado";
          } else {
            statusLed = "Desligado";
          }
          client.println("<form action=\"led1\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Led 1 - "+statusLed+"</button>");
          client.println("</form> <br />");
          
          if(digitalRead(led2)) {
            statusLed = "Ligado";
          } else {
            statusLed = "Desligado";
          }
          client.println("<form action=\"led2\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Led 2 - "+statusLed+"</button>");
          client.println("</form> <br />");
          
          if(digitalRead(led3)) {
            statusLed = "Ligado";
          } else {
            statusLed = "Desligado";
          }
          client.println("<form action=\"led3\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Led 3 - "+statusLed+"</button>");
          client.println("</form> <br />");
          
          client.println("</center>");
          client.println("</body>");
          client.println("</html>");
        
          readString = "";
          
          client.stop();
        }
      }
      
    }
  }
  
}
