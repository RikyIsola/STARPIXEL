package com.island.starpixel.blocchi.terreni;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
public class Palude extends Terreno
{
	public int vita()
	{
		return 75;
	}
	public String nomeItaliano()
	{
		return"PALUDE";
	}
	public String nomeInglese()
	{
		return"SWAMP";
	}
	public int immagine()
	{
		return R.drawable.palude;
	}
	protected Blocco[]prodotti(Blocchi b)
	{
		return new Blocco[]{b.Ferro,b.Supertanium,b.MuroPalude};
	}
}
