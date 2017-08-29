package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.terreni.*;
public class MontagnaInnevata extends Solido
{
	public int riproduzione()
	{
		return 5000;
	}
	public int vita()
	{
		return 15;
	}
	public Terreno[]riproduzione(Blocchi b)
	{
		return new Terreno[]{b.Neve};
	}
	public int immagine()
	{
		return R.drawable.montagnaneve;
	}
	public String nomeItaliano()
	{
		return"MONTAGNA INNEVATA";
	}
	public String nomeInglese()
	{
		return"SNOW COVERED MOUNTAIN";
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
		return new Blocco[]{b.Neve};
	}
}
