package com.island.starpixel.blocchi.solidi.armi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.strumenti.proiettili.*;
public class Arma1 extends Arma
{
	public Proiettile siluro(Blocchi b)
	{
		return b.Siluro1;
	}
	public int immagine()
	{
		return R.drawable.armi1;
	}
	public String nomeItaliano()
	{
		return"ARMI 1";
	}
	public String nomeInglese()
	{
		return"WEAPONS 1";
	}
	public int vita()
	{
		return 20;
	}
}
