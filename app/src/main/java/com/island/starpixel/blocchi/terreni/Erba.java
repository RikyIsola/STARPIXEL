package com.island.starpixel.blocchi.terreni;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
public class Erba extends Terreno
{
	public int vita()
	{
		return 75;
	}
	public String nomeItaliano()
	{
		return"ERBA";
	}
	public String nomeInglese()
	{
		return"GRASS";
	}
	public int immagine()
	{
		return R.drawable.erba;
	}
	protected Blocco[]prodotti(Blocchi b)
	{
		return new Blocco[]{b.MuroErba,b.Supertanium,b.Ferro};
	}
}
