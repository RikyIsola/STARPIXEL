package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
public class Sensori extends Solido
{
	public int immagine()
	{
		return R.drawable.sensori;
	}
	public String nomeItaliano()
	{
		return"SENSORI";
	}
	public String nomeInglese()
	{
		return"SENSORS";
	}
	public String descrizioneItaliano()
	{
		return"POSIZIONALO DA QUALCHE PARTE E USA LA CONSOLE DI COMANDO PER VEDERE TUTTO";
	}
	public String descrizioneInglese()
	{
		return"PLACE IT SOMEWHERE AND USE THE COMMAND CONSOLE TO SEE EVERYTHING";
	}
	public int vita()
	{
		return 15;
	}
}
