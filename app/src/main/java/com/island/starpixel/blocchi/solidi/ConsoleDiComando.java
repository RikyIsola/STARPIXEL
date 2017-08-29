package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
public class ConsoleDiComando extends Solido
{
	public int immagine()
	{
		return R.drawable.consoledicomando;
	}
	public String nomeItaliano()
	{
		return"CONSOLE DI COMANDO";
	}
	public String nomeInglese()
	{
		return"COMMAND CONSOLE";
	}
	public String descrizioneItaliano()
	{
		return"TOCCALO PER APRIRE LA SCHERMATA DI CONTROLLO DEI SENSORI,DEI MOTORI,DEL TELETRASPORTO.....";
	}
	public String descrizioneInglese()
	{
		return"TOUCH IT TO OPEN THE COMMAND SCREEN OF THE SENSORS,THE MOTORS,THE TELEPORT.....";
	}
	public int vita()
	{
		return 30;
	}
}
