package com.island.starpixel.blocchi.solidi.mobili;
import com.island.starpixel.*;
public class AltroGiocatore extends Mobile
{
	public int vita()
	{
		return 100;
	}
	public double velocita()
	{
		return 0.1;
	}
	public int immagine()
	{
		return R.drawable.uomo;
	}
	public String nomeItaliano()
	{
		return"ALTRO GIOCATORE";
	}
	public String nomeInglese()
	{
		return"OTHER PLAYER";
	}
	public String descrizioneItaliano()
	{
		return"QUESTO É UN ALTRO GIOCATORE";
	}
	public String descrizioneInglese()
	{
		return"THIS IS ANOTHER PLAYER";
	}
}
