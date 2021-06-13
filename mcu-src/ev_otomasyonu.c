#include "seri_iletim.h"
#include "benim_LCD.c"
//#include <dijit.c>
//#include <lcd.c>
//#include <input.c>

#use I2C(master, scl=PIN_C3, sda=PIN_C4)
#define blink  PIN_C5  // max6953 için blink pini//
#define lamp_int PIN_D2
                       //15 17    23          34    42 44      53 54           68
const char msg[]="Haberlesme DALI TV ACIK TV KAPALI SET ACIK SET KAPALI KOMBI SICAKLIK
CAMASIR MAKINESI ACIK CAMASIR MAKINESI KAPALI OCAGI YAK OCAGI KAPAT FIRIN ACIK FIRIN KAPALI
KLIMA SIC. T.T.A.T.L. KOMBI ACIK KOMBI KAPALI  PERDE ";

unsigned int8 prg=0,kombi=15,ses=0;
char gelen[30];
byte komut=0;

void max_kur()
{
   byte x;
   int8 max_reg_adr[5]={1,2,3,4,7};//Max adres bilgileri
   int8 max_reg_data[5]={0xFF,0xFF,0x01,0x81,0}; // Registerlere ait uygun veriler.
   for(x=0;x<5;x++)
   {
     i2c_start();     // Start condition
     i2c_write(0xA0); // Device slav address
     i2c_write(max_reg_adr[x]);    // Device command
     i2c_write(max_reg_data[x]);   // Device data
     i2c_stop();      // Stop condition
   }
    
}

void max_data_gon(int8 adr,char data)
{
     i2c_start();     // Start condition2
     i2c_write(0xA0); // Device address
     i2c_write(adr);    // Device command
     i2c_write(data);    // Device data
     i2c_stop();      // Stop condition
}
void max_sil()
{
  max_data_gon(0x20,' ');max_data_gon(0x21,' '); 
  max_data_gon(0x22,' ');max_data_gon(0x23,' ');
}
void max_kaydir(int8 hrf_0,int8 hrf_1)
{
   int8 index,dijit=0x24,hrf=0,hrf_sayisi;
   //char data[];
   hrf_sayisi=hrf_1-hrf_0;
   kay:
   if (hrf==hrf_sayisi+1)
      {
         hrf=0;
         return;
      }
     for(index=hrf_0;index<(hrf_0+hrf+1);index++)//index :kaydýrma miktarýný sayar
       {                               //hrf: dijitleri ve gönderilecek datalarý sayar 
         max_data_gon((dijit-hrf+index-hrf_0),msg[index]);
        // max_data_gon((dijit-hrf),msg[index]);

       }
       delay_ms(300);
       hrf++;
       goto kay;
  } 
void led()
{
   output_high(PIN_B7); delay_ms(500);output_low(PIN_B7); delay_ms(500);
}
void step_ileri(int16 hiz,int8 adim)
{
    int8 x,s=0x01;
    output_a(0x00);
    for(x=0;x<adim;x++)
    {
      output_a(s&0b00001111);
      s=s<<1;
      if (s>8) s=1;
      delay_ms(hiz);
    }
}
void step_geri(int16 hiz,int8 adim)
{
    int8 x,s=0x08;
    output_a(0x00);
    for(x=0;x<adim;x++)
    {
      output_a(s&0b00001111);
      s=s>>1;
      if (s<1) s=8;
      delay_ms(hiz);
      
    }
}
#int_rda   // RX ucuna veri gelince meydane gelen kesme
void serihaberlesme_kesmesi () //PC'den gelen verileri almak için
{
   disable_interrupts(int_rda); // int_rda kesmesini pasif yap
   fgets(gelen,PC);   
   enable_interrupts(INT_RDA);
   enable_interrupts(GLOBAL);
}
#int_EXT //RB0 harici kesmesi cep telefonu kontrol devresinden gelen verileri
void  EXT_isr(void) //almak için kullanýldý.
{
  disable_interrupts(int_ext);
  fgets(gelen,DTMF); 
  enable_interrupts(INT_EXT);
  enable_interrupts(GLOBAL);

}
void main()
{
   setup_adc_ports(NO_ANALOGS);
   setup_adc(ADC_OFF);
   setup_psp(PSP_DISABLED);
   setup_spi(SPI_SS_DISABLED);
   setup_wdt(WDT_OFF);
   //setup_timer_0(RTCC_INTERNAL|RTCC_DIV_1|RTCC_8_bit);
   setup_timer_1(T1_DISABLED);
   setup_timer_2(T2_DISABLED,0,1);
   setup_timer_3(T3_DISABLED|T3_DIV_BY_1);
   ext_int_edge(L_TO_H);
   enable_interrupts(INT_EXT);
   enable_interrupts(INT_RDA);
   enable_interrupts(GLOBAL);
  
   max_kur();
   max_kaydir(173,184);max_sil();max_kaydir(0,15);max_sil();delay_ms(500);
   max_data_gon(32,'S');max_data_gon(33,'D');max_data_gon(34,'e');max_data_gon(35,'V');
   fputs("Ses Komutlari ile Ev Kontrol Uygulamasi Version 1.0",PC);//Seri porta string gönderir. return ile birlikte.
   //fprintf(PC,"Version 1.1  "); fputc(13,PC);// string olarak ifadeyi gönderir return(13), line-feed gitmez.
   fputs("Elektronik Kart ile Haberlesme Basladi",PC);
   fputs("Kontrol Komutu...:",PC);

   while(TRUE) {
       
     gelen[0]='0';gelen[1]='0';gelen[2]='0';gelen[3]=0;
     led();
     //printf("Kontrol Komutu: ");
    // if (kbhit(PC)) fgets(gelen,PC);  //Seri porttan entere basýlýncaya kadar gelen karakterleri okur
                   // ve char tipinde bir diziye atar.
     if (gelen[0]==42)//'*')
     {
       // gelen karkaterler byte olarak sayýsal deðere çevrilir.
       komut=(gelen[1]-48)*100+(gelen[2]-48)*10+gelen[3]-48;
                              // ilk basýlan karakter sýfýrýncý karakter
                              // sayýsal olarakta en soldaki karakter
      if (gelen[4]==35)//'#')
        {
            switch(komut)
            {
             case 0:output_d(0x00);printf("OK \r");break;   //tüm lambalar söndür
             case 1:output_high(PIN_D0);printf("OK \r");break;//printf("Salon lambasi yandi \r");break;
             case 2:output_low(PIN_D0);printf("OK \r");break;//printf("Salon lambasi kapandi \r");break;
             //case 3:printf("Salon lambasi parlaklik artti \r");break;
             //case 4:printf("Salon lambasi parlaklik azaldi \r");break;
             case 5: output_high(PIN_D1);printf("OK \r");break;// printf("Oturma odasi lambasi yandi \r");break;
             case 6:output_low(PIN_D1);printf("OK \r");break;// printf("Oturma odasi lambasi kapandi \r");break;
             case 7: output_high(PIN_D2);printf("OK \r");break;// printf("Cocuk odasi lambasi yandi \r");break;
             case 8:output_low(PIN_D2);printf("OK \r");break;// printf("Cocuk odasi lambasi kapandi \r");break;
             case 9: output_high(PIN_D3);printf("OK \r");break;// printf("Mutfak lambasi yandi \r");break;
             case 16:output_low(PIN_D3);printf("OK \r");break;// printf("Mutfak lambasi kapandi \r");break;
             case 17: output_high(PIN_D4);printf("OK \r");break;// printf("Yatak odasi lambasi yandi \r");break;
             case 18:output_low(PIN_D4);printf("OK \r");break;// printf("Yatak odasi lambasi kapandi \r");break;
             case 23: output_high(PIN_D5);printf("OK \r");break;// printf("Antre lambasi yandi \r");break;
             case 24:output_low(PIN_D5);printf("OK \r");break;// printf("Antre lambasi kapandi \r");break;
             
             case 30:{
                        output_high(PIN_D6);
                        printf("OK \r");max_kaydir(16,23);max_sil();
                        break;//TV acildi
                     }
             case 31:{
                         output_low(PIN_D6);
                         printf("OK \r");max_kaydir(24,33);max_sil();
                         break;//TV kapandi
                     }
             case 32:{
                         printf("OK \r");prg++;//TV program arttir
                         max_data_gon(32,'P');max_data_gon(33,' ');max_data_gon(35,(48+prg%10u));
                         max_data_gon(34,(48+(prg/10u)%10u));
                         break;
                     }
            case 33:{
                         printf("OK \r");prg--;//TV program azalt
                         if (prg<1)prg=99;
                         max_data_gon(32,'P');max_data_gon(33,' ');max_data_gon(35,(48+prg%10u));
                         max_data_gon(34,(48+(prg/10u)%10u));
                         break;
                     }
            case 34:{
                         printf("OK \r");ses++;   //TV ses arttirildi
                         if (ses<1)ses=1;if (ses>9)ses=9;
                         max_data_gon(32,'S');max_data_gon(33,'E');max_data_gon(34,'S');
                         max_data_gon(35,(48+ses%10u));
                         break;
                     }
            case 35:{
                         printf("OK \r");ses--;  //TV ses azaltildi
                         if (ses<1)ses=1;if (ses>9)ses=9;
                         max_data_gon(32,'S');max_data_gon(33,'E');max_data_gon(34,'S');
                         max_data_gon(35,(48+ses%10u));
                         break;
                     }
            case 36:{
                         output_high(PIN_D7);
                         printf("OK \r");max_kaydir(34,42);max_sil();
                         break;//Muzik seti acildi
                     }
            case 37:{
                         output_low(PIN_D7);
                         printf("OK \r");max_kaydir(43,53);max_sil();
                         break;//Muzik seti kapandi
                     }
            case 38:{
                         printf("OK \r");ses++;//Muzik seti sesi arttirildi
                         if (ses<1)ses=1;if (ses>9)ses=9;
                         max_data_gon(32,'S');max_data_gon(33,'E');max_data_gon(34,'T');
                         max_data_gon(35,' '); delay_ms(1000);
                         max_data_gon(32,'S');max_data_gon(33,'E');max_data_gon(34,'S');
                         max_data_gon(35,(48+ses%10u));
                         break;
                     }
           case 39:{
                         printf("OK \r");ses--;//Muzik seti sesi azaltildi
                         if (ses<1)ses=1;if (ses>9)ses=9;
                         max_data_gon(32,'S');max_data_gon(33,'E');max_data_gon(34,'T');
                         max_data_gon(35,' '); delay_ms(1000);
                         max_data_gon(32,'S');max_data_gon(33,'E');max_data_gon(34,'S');
                         max_data_gon(35,(48+ses%10u));
                         break;
                     }
           case 40:{
                         printf("OK \r");
                         max_data_gon(32,'T');max_data_gon(33,'R');max_data_gon(34,'T');
                         max_data_gon(35,'1');
                         break;
                     }
           case 41:{
                         printf("OK \r");
                         max_data_gon(32,'T');max_data_gon(33,'R');max_data_gon(34,'T');
                         max_data_gon(35,'2');
                         break;
                     }
           case 42:{
                         printf("OK \r");
                         max_data_gon(32,'N');max_data_gon(33,'T');max_data_gon(34,'V');
                         max_data_gon(35,' ');
                         break;
                     }
           case 43:{
                         printf("OK \r");
                         max_data_gon(32,'A');max_data_gon(33,'T');max_data_gon(34,'V');
                         max_data_gon(35,' ');
                         break;
                     }
           case 44:{
                         printf("OK \r");
                         max_data_gon(32,'K');max_data_gon(33,'N');max_data_gon(34,'L');
                         max_data_gon(35,'D');
                         break;
                     }
            case 45:{
                         printf("OK \r");
                         max_data_gon(32,'S');max_data_gon(33,'T');max_data_gon(34,'A');
                         max_data_gon(35,'R');
                         break;
                     }
           case 46:{
                         printf("OK \r");
                         max_data_gon(32,'S');max_data_gon(33,'T');max_data_gon(34,'V');
                         max_data_gon(35,' ');
                         break;
                    }
           case 47:{
                         printf("OK \r");
                         max_data_gon(32,'K');max_data_gon(33,'A');max_data_gon(34,'N');
                         max_data_gon(35,'L');delay_ms(1000);
                         max_data_gon(32,' ');max_data_gon(33,'2');max_data_gon(34,'6');
                         max_data_gon(35,' ');
                         break;
                  }
           case 60:{
                         printf("OK \r");kombi++;//Kombi sicakligi arttirildi
                         max_kaydir(54,68);max_sil();
                         max_data_gon(32,(48+(kombi/10)%10u));max_data_gon(33,(48+kombi%10u));
                         max_data_gon(34,' '); max_data_gon(35,'C');
                         break;
                     }
           case 61:{
                         printf("OK \r");kombi--;//Kombi sicakligi azaltildi
                         max_kaydir(54,68);max_sil();
                         max_data_gon(32,(48+(kombi/10)%10u));max_data_gon(33,(48+kombi%10u));
                         max_data_gon(34,' '); max_data_gon(35,'C');
                         break;
                     }
           case 62:{
                         output_high(PIN_E1);
                         printf("OK \r");//Camasir makinesi acildi
                         max_kaydir(70,92);max_sil(); 
                         break;
                     }
           case 63:{
                         output_low(PIN_E1);
                         printf("OK \r");//Camasir makinesi kapatildi
                         max_kaydir(92,115);max_sil(); 
                         break;
                     }
           case 64:{
                         printf("OK \r");     //Ocak yandi
                         max_kaydir(116,126);max_sil();                 
                         break;
                     }
           case 65:{
                         printf("OK \r");      //Ocak kapandi
                         max_kaydir(126,137);max_sil(); break;
                    }
           case 66:
                    {
                         output_high(PIN_E2);
                         printf("OK \r");      //Fýrýn Acildi
                         max_kaydir(138,148);max_sil(); 
                         break;
                    }
           case 67:
                 {                      
                       output_low(PIN_E2);
                       printf("OK \r");      //Fýrýn Kapandi
                       max_kaydir(149,161);max_sil(); 
                       break;
                 }
           case 68:
                 {
                      output_high(PIN_E0);
                      printf("OK \r");
                      max_kaydir(185,195);max_sil();  //kOMBÝ aaçýk
                      break;
                 }
           case 69:
                 {
                      output_low(PIN_E0);
                      printf("OK \r");
                      max_kaydir(196,210);max_sil();  //KOMBI KAPALI
                      break;
                 }
           case 90:
                 {
                      printf("OK \r");
                      max_kaydir(210,216);max_sil();//PERDELER AÇçççç 1 tur 48
                      step_geri(35,100);// (hiz,adim)//adým 7,5 derece
                      break;
                 }
            case 91:
                 {
                      printf("OK \r");
                      max_kaydir(210,216);max_sil();//PERDELER Kapattt
                      step_ileri(35,100); // (hiz,adim)
                      break;
                 }

          
            default:printf("Komut Algilanamadi !!! \r ");break;
            }//Switch 
        }// if '#' için
     }// if '*' için
        
 }//While

}
