package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
public class Motore extends Solido
{
	public int immagine()
	{
		return R.drawable.motore;
	}
	public String nomeItaliano()
	{
		return"MOTORI";
	}
	public String nomeInglese()
	{
		return"MOTORS";
	}
	public String descrizioneItaliano()
	{
		return"POSIZIONALO DA QUALCHE PARTE E USA LA CONSOLE DI COMANDO PER VIAGGIARE";
	}
	public String descrizioneInglese()
	{
		return"PLACE IT SOMEWHERE AND USE THE COMMAND CONSOLE TO TRAVEL";
	}
	public int vita()
	{
		return 20;
	}
}
