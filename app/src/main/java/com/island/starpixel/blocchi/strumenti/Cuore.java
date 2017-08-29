package com.island.starpixel.blocchi.strumenti;
import com.island.starpixel.*;
public class Cuore extends Strumento
{
	public int immagine()
	{
		return R.drawable.vita;
	}
	public String nomeItaliano()
	{
		return"CUORE";
	}
	public String nomeInglese()
	{
		return"HEARTH";
	}
	public String descrizioneItaliano()
	{
		return"TOCCA UN OGGETTO PER RIPARARLO COMPLETAMMENTE";
	}
	public String descrizioneInglese()
	{
		return"TOUCH AN OBJECT TO RAPAIR IT COMPLETELY";
	}
}
