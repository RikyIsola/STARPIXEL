package com.island.starpixel.blocchi.solidi.scudi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.strumenti.scudi.*;
import com.island.starpixel.blocchi.*;
public class Scudo1 extends Scudo
{
	public Scudi colore(Blocchi b)
	{
		return b.Scudi1;
	}
	public int immagine()
	{
		return R.drawable.scudi1;
	}
	public String nomeItaliano()
	{
		return"SCUDO 1";
	}
	public String nomeInglese()
	{
		return"SHIELD 1";
	}
	public String descrizioneItaliano()
	{
		return"PROTEGGE LA TUA NAVE CON 90 UNITA DI PROTEZIONE";
	}
	public String descrizioneInglese()
	{
		return"IT PROTECTS YOUR SHIP WITH 90 UNITS OF PROTECTION";
	}
	public int vita()
	{
		return 90;
	}
}
