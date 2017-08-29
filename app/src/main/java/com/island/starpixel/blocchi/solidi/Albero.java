package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.terreni.*;
import com.island.starpixel.blocchi.*;
public class Albero extends Solido
{
	public int riproduzione()
	{
		return 3000;
	}
	public int vita()
	{
		return 10;
	}
	public Terreno[]riproduzione(Blocchi b)
	{
		return new Terreno[]{b.Erba};
	}
	public int immagine()
	{
		return R.drawable.albero;
	}
	public String nomeItaliano()
	{
		return"ALBERO";
	}
	public String nomeInglese()
	{
		return"TREE";
	}
	public String descrizioneItaliano()
	{
		return"PUOI ESTRARCI IL LEGNO";
	}
	public String descrizioneInglese()
	{
		return"YOU CAN EXTRACT WOOD FROM IT";
	}
	public Blocco[]prodotti(Blocchi b)
	{
		return new Blocco[]{b.Erba,b.Legno,b.Palude,b.Cuore};
	}
}
