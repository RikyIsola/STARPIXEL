package com.island.starpixel.blocchi.terreni;
import com.island.starpixel.*;

public class Teletrasporto extends Terreno
{
	public int vita()
	{
		return 15;
	}
	public String descrizioneInglese()
	{
		return"PLACE A THING OR A PERSON OVER IT AND USE THE COMMAND CONSOLE TO TELEPORT IT";
	}
	public String descrizioneItaliano()
	{
		return"METTICI SOPRA UN OGGETTO O UNA PERSONA E USA LA CONSOLE DI COMANDO PER TELETRASPORTARLO";
	}
	public String nomeItaliano()
	{
		return"TELETRASPORTO";
	}
	public String nomeInglese()
	{
		return"TELEPORT";
	}
	public int immagine()
	{
		return R.drawable.teletrasporto;
	}
}
