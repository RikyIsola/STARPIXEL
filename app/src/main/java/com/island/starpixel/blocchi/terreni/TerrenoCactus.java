package com.island.starpixel.blocchi.terreni;
import com.island.starpixel.*;
public class TerrenoCactus extends Terreno
{
	public int vita()
	{
		return 5;
	}
	public String descrizioneInglese()
	{
		return"WEAK TERRAIN";
	}
	public String descrizioneItaliano()
	{
		return"TERRENO DEBOLE";
	}
	public String nomeItaliano()
	{
		return"TERRENO CACTUS";
	}
	public String nomeInglese()
	{
		return"CACTUS FLOR";
	}
	public int immagine()
	{
		return R.drawable.terrenocactus;
	}
}
