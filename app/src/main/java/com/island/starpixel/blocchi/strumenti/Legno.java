package com.island.starpixel.blocchi.strumenti;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
public class Legno extends Strumento
{
	public int immagine()
	{
		return R.drawable.legno;
	}
	public String nomeItaliano()
	{
		return"LEGNO";
	}
	public String nomeInglese()
	{
		return"WOOD";
	}
	public String descrizioneItaliano()
	{
		return"USALO PER COSTRUIRE ATTREZZI E MURI";
	}
	public String descrizioneInglese()
	{
		return"USE IT TO BUILD TOOLS AND WALLS";
	}
	protected Blocco[] prodotti(Blocchi b)
	{
		return new Blocco[]{b.TerrenoLegno,b.MuroLegno,b.AttrezziLegno,b.PortaLegno,b.TorciaLegno,b.ArcoLegno};
	}
}
