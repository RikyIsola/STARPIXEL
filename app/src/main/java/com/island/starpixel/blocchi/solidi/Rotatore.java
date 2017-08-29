package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
public class Rotatore extends Solido
{
	public int immagine()
	{
		return R.drawable.rotatore;
	}
	public String nomeItaliano()
	{
		return"ROTATORE";
	}
	public String nomeInglese()
	{
		return"ROTATIONS";
	}
	public String descrizioneItaliano()
	{
		return"POSIZIONALO DA QUALCHE PARTE E USA LA CONSOLE DI COMANDO PER ROTEARE LA NAVE O IL PIANETA";
	}
	public String descrizioneInglese()
	{
		return"PLACE IT SOMEWHERE AND USE THE COMMAND CONSOLE TO ROTATE THE SHIP OR THE PLANET";
	}
	public int vita()
	{
		return 20;
	}
}
