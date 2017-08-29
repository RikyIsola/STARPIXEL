package com.island.starpixel.blocchi.solidi.torcia;
import com.island.starpixel.blocchi.solidi.*;
public abstract class Torcia extends Solido
{
	public String descrizioneItaliano()
	{
		return"USALA PER ILLUMINARE L'AREA";
	}
	public String descrizioneInglese()
	{
		return"USE IT TO ILLUMINATE THE AREA";
	}
	public int vita()
	{
		return 1;
	}
}
