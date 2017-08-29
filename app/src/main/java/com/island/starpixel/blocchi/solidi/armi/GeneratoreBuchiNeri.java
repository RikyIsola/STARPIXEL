package com.island.starpixel.blocchi.solidi.armi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.strumenti.proiettili.*;
public class GeneratoreBuchiNeri extends Arma
{
	public Proiettile siluro(Blocchi b)
	{
		return null;
	}
	public int immagine()
	{
		return R.drawable.generatorebuchineri;
	}
	public String nomeItaliano()
	{
		return"GENERATORE DI BUCHI NERI";
	}
	public String nomeInglese()
	{
		return"BLACK HOLE GENERATOR";
	}
	public int vita()
	{
		return 100;
	}
}
