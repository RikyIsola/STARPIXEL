package com.island.starpixel.blocchi.solidi.armi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.strumenti.proiettili.*;

public class Arma2 extends Arma
{
	public Proiettile siluro(Blocchi b)
	{
		return b.Siluro2;
	}
	public int immagine()
	{
		return R.drawable.armi2;
	}
	public String nomeItaliano()
	{
		return"ARMI 2";
	}
	public String nomeInglese()
	{
		return"WEAPONS 2";
	}
	public int vita()
	{
		return 30;
	}
}
