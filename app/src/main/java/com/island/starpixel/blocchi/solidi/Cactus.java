package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.terreni.*;

public class Cactus extends Solido
{
	public int riproduzione()
	{
		return 10000;
	}
	public int vita()
	{
		return 5;
	}
	public Terreno[]riproduzione(Blocchi b)
	{
		return new Terreno[]{b.Sabbia};
	}
	public int immagine()
	{
		return R.drawable.cactus;
	}
	public String nomeItaliano()
	{
		return"CACTUS";
	}
	public String nomeInglese()
	{
		return"CACTUS";
	}
	public String descrizioneItaliano()
	{
		return"LO PUOI TROVARE NEI DESERTI";
	}
	public String descrizioneInglese()
	{
		return"YOU CAN FIND IT IN DESERTS";
	}
	protected Blocco[]prodotti(Blocchi b)
	{
		return new Blocco[]{b.AttrezziCactus,b.Sabbia,b.TerrenoCactus,b.MuroCactus,b.TorciaCactus,b.PortaCactus,b.ArcoCactus};
	}
}
