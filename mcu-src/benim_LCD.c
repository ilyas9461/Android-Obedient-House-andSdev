//////////////////////////////////////////////////////////////
//                    benim_LCD.c                           // 
// Bu LCD sürücü dosyasý 2x16 HD44780 uyumlu LCD'ler        //
// için yazýlmýþtýr. LCD'den sadece okuma yapýlacaðý için   //
// R/W ucu þaseye baðlanmalýdýr. LCD baðlantýsý aþaðýdadýr. //
//////////////////////////////////////////////////////////////
// RB0 D4
// RB1 D5
// RB2 D6
// RB3 D7
// RB4 RS
// RB5 E
// R/W ucu direkt þaseye baðlanacak

#define e   pin_b5 // LCD'nin E ucu RB5 pinine baðlý
#define rs  pin_b4 // LCD'nin RS ucu RB4 pinine baðlý

//****** LCD'ye Komut Gönderme Fonksiyonu **********
void lcd_komut(byte komut)
{
   output_b(komut>>4);  // Komutun yüksek deðerli 4 bitini gönder
   output_low(rs);       // LCD komut almak için ayarlandý
   output_high(e);       // E ucu lojik-1'den lojik-0'a çekiliyor
   output_low(e);
   delay_ms(2);         // 2 msn gecikme veriliyor

   output_b(komut&0x0F); // Komutun düþük deðerli 4 bitini gönder
   output_low(rs);       // LCD komut almak için ayarlandý
   output_high(e);        // E ucu lojik-1'den lojik-0'a çekiliyor
   output_low(e);
   delay_ms(2);        // 2 msn gecikme veriliyor
}

//******* LCD'ye Veri Gönderme Fonksiyonu **********
void lcd_veri(byte veri)
{
   output_b(veri>>4);    // Verinin yüksek deðerli 4 bitini gönder
   output_high(rs);      // LCD veri almak için ayarlandý
   output_high(e);       // E ucu lojik-1'den lojik-0'a çekiliyor
   output_low(e);
   delay_ms(1);          // 1 msn gecikme veriliyor

   output_b(veri&0x0F); // Verinin düþük deðerli 4 bitini gönder
   output_high(rs);     // LCD veri almak için ayarlandý
   output_high(e);      // E ucu lojik-1'den lojik-0'a çekiliyor
   output_low(e);
   delay_ms(1);         // 1 msn gecikme veriliyor
}

//******* LCD'de Ýmlec Konumlandýrma Fonksiyonu ********
void imlec(byte satir, byte sutun)
{
   if (satir==1)      // Eðer satýr deðiþkeni "1" ise
      lcd_komut(0x80|(sutun-1));

   if (satir==2)      // Eðer satýr deðiþkeni "2" ise
      lcd_komut(0x80|(0x40+(sutun-1)));
}

//********* LCD Baþlangýç Ayarlarý Fonksiyonu ******
void lcd_hazirla()
{
   lcd_komut(0x02);// LCD'yi kullanýma hazýr hale getir, imleç                // 1.satýr 1.sütunda komutu
   lcd_komut(0x28);//2 satýr, 4 bit iletiþim, 5x8 dot matris seçildi 
   lcd_komut(0x08);//Display kapalý,alt çizgi ve yanýp sönme yok
   lcd_komut(0x0C);//Display açýk,imleç alt çizgi ve yanýp sönme yok
   lcd_komut(0x06);// Her veri yazýldýðýnda imleç bir saða gitsin
   lcd_komut(0x01);// Display sil. Ýmleç 1.satýr 1.sütunda
   delay_ms(1);    // 1 msn bekle
}

