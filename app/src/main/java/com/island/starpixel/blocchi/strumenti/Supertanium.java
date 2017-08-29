package com.island.starpixel.blocchi.strumenti;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
public class Supertanium extends Strumento
{
	public int immagine()
	{
		return R.drawable.supertanium;
	}
	public String nomeItaliano()
	{
		return"SUPERTANIUM";
	}
	public String nomeInglese()
	{
		return"SUPERTANIUM";
	}
	public String descrizioneItaliano()
	{
		return"SPECIALE MATERIALE CHE PERMETTE LA COSTRUZIONE DI COMPLESSE ATTREZZATURE";
	}
	public String descrizioneInglese()
	{
		return"SPECIAL MATERIAL THAT PERMITS THE BUILDING OF COMPLEX TOOLS";
	}
	protected Blocco[]prodotti(Blocchi b)
	{
		return new Blocco[]{b.Sensori,b.Motore,b.Scudo1,b.Laser1,b.ConsoleDiComando,b.Rotatore,b.BancaDati,b.LuceElettrica,b.Teletrasporto};
	}
}
