package com.island.starpixel.blocchi.terreni;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.solidi.*;
import com.island.starpixel.blocchi.*;
public class Pietra extends Terreno
{
	public int vita()
	{
		return 100;
	}
	public String nomeItaliano()
	{
		return"PIETRA";
	}
	public String nomeInglese()
	{
		return"STONE";
	}
	public int immagine()
	{
		return R.drawable.pietra;
	}
	protected Blocco[]prodotti(Blocchi b)
	{
		return new Blocco[]{b.MuroPietra,b.Ferro,b.Supertanium,b.AttrezziPietra,b.PortaPietra,b.ArcoPietra};
	}
}
