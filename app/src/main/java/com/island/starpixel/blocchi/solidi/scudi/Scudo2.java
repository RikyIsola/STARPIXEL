package com.island.starpixel.blocchi.solidi.scudi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.strumenti.scudi.*;
public class Scudo2 extends Scudo
{
	public Scudi colore(Blocchi b)
	{
		return b.Scudi2;
	}
	public int immagine()
	{
		return R.drawable.scudi2;
	}
	public String nomeItaliano()
	{
		return"SCUDO 2";
	}
	public String nomeInglese()
	{
		return"SHIELD 2";
	}
	public String descrizioneItaliano()
	{
		return"PROTEGGE LA TUA NAVE CON 150 UNITA DI PROTEZIONE";
	}
	public String descrizioneInglese()
	{
		return"IT PROTECTS YOUR SHIP WITH 150 UNITS OF PROTECTION";
	}
	public int vita()
	{
		return 150;
	}
}
