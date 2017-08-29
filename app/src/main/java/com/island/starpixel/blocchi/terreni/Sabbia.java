package com.island.starpixel.blocchi.terreni;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;

public class Sabbia extends Terreno
{
	public int vita()
	{
		return 10;
	}
	public String nomeItaliano()
	{
		return"SABBIA";
	}
	public String nomeInglese()
	{
		return"SAND";
	}
	public int immagine()
	{
		return R.drawable.sabbia;
	}
	protected Blocco[]prodotti(Blocchi b)
	{
		return new Blocco[]{b.MuroSabbia,b.Ferro,b.Supertanium};
	}
}
