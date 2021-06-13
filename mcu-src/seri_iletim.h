//#include <18F452.h>
//#device adc=8

//!#FUSES NOWDT                    //No Watch Dog Timer
//!#FUSES WDT128                   //Watch Dog Timer uses 1:128 Postscale
//!#FUSES XT                       //Crystal osc <= 4mhz
//!#FUSES NOPROTECT                //Code not protected from reading
//!#FUSES NOOSCSEN                 //Oscillator switching is disabled, main oscillator is source
//!#FUSES BROWNOUT                 //Reset when brownout detected
//!#FUSES BORV20                   //Brownout reset at 2.0V
//!#FUSES NOPUT                    //No Power Up Timer
//!#FUSES STVREN                   //Stack full/underflow will cause reset
//!#FUSES NODEBUG                  //No Debug mode for ICD
//!#FUSES LVP                      //Low Voltage Programming on B3(PIC16) or B5(PIC18)
//!#FUSES NOWRT                    //Program memory not write protected
//!#FUSES NOWRTD                   //Data EEPROM not write protected
//!#FUSES NOWRTB                   //Boot block not write protected
//!#FUSES NOWRTC                   //configuration not registers write protected
//!#FUSES NOCPD                    //No EE protection
//!#FUSES NOCPB                    //No Boot Block code protection
//!#FUSES NOEBTR                   //Memory not protected from table reads
//!#FUSES NOEBTRB                  //Boot block not protected from table reads
//#if defined(__PCH__)
#include <18F452.h>
#fuses XT,NOWDT,NOPROTECT,NOLVP
#use delay(clock=4000000)
//#endif
#use rs232(baud=19200,parity=N,xmit=PIN_C6,rcv=PIN_C7,bits=8,stop=1,stream=PC)
#use rs232(baud=19200,parity=N,xmit=PIN_B1,rcv=PIN_B2,bits=8,stop=1,stream=DTMF)
//#use rs232(baud=9600,parity=N,xmit=PIN_D0,rcv=PIN_D1,bits=8,stop=1,stream=LAMP)





