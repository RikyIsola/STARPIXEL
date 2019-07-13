package com.island.starpixel.blocchi.solidi.armi;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.solidi.*;
import com.island.starpixel.blocchi.strumenti.proiettili.*;
public abstract class Arma extends Solido
{
	public abstract Proiettile siluro(Blocchi b);
	public String descrizioneItaliano()
	{
		return"POSIZIONALO DA QUALCHE PARTE E USA LA CONSOLE DI COMANDO PER SPARARE";
	}
	public String descrizioneInglese()
	{
		return"PLACE IT SOMEWHERE AND USE THE COMMAND CONSOLE TO FIRE";
	}
}
