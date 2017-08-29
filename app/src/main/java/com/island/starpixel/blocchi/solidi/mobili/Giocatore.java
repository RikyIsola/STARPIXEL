package com.island.starpixel.blocchi.solidi.mobili;
import com.island.starpixel.*;
public class Giocatore extends Mobile
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
		return"GIOCATORE";
	}
	public String nomeInglese()
	{
		return"PLAYER";
	}
	public String descrizioneItaliano()
	{
		return"QUESTO SEI TU";
	}
	public String descrizioneInglese()
	{
		return"YUO ARE HIM";
	}
}
