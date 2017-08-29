package com.island.starpixel.blocchi.terreni;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
public class Neve extends Terreno
{
	public int vita()
	{
		return 25;
	}
	public String nomeItaliano()
	{
		return"NEVE";
	}
	public String nomeInglese()
	{
		return"SNOW";
	}
	public int immagine()
	{
		return R.drawable.neve;
	}
	protected Blocco[]prodotti(Blocchi b)
	{
		return new Blocco[]{b.PallaDiNeve,b.MuroNeve,b.Ferro,b.Supertanium};
	}
}
