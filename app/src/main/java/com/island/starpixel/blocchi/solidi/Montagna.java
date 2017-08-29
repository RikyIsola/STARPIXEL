package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.terreni.*;
public class Montagna extends Solido
{
	public int riproduzione()
	{
		return 5000;
	}
	public int vita()
	{
		return 20;
	}
	public Terreno[]riproduzione(Blocchi b)
	{
		return new Terreno[]{b.Pietra};
	}
	public int immagine()
	{
		return R.drawable.montagna;
	}
	public String nomeItaliano()
	{
		return"MONTAGNA";
	}
	public String nomeInglese()
	{
		return"MOUNTAIN";
	}
	public String descrizioneItaliano()
	{
		return"PUOI ESTRARCI MINERALI";
	}
	public String descrizioneInglese()
	{
		return"YOU CAN EXTRACT MINERALS FROM IT";
	}
	protected Blocco[]prodotti(Blocchi b)
	{
		return new Blocco[]{b.Pietra};
	}
}
