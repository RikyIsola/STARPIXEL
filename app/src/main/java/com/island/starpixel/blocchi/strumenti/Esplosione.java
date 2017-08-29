package com.island.starpixel.blocchi.strumenti;
import com.island.starpixel.*;
public class Esplosione extends Strumento
{
	public int immagine()
	{
		return R.drawable.esplosione;
	}
	public String descrizioneItaliano()
	{
		return"DISTRUGGE TERRENI E SOLIDI";
	}
	public String descrizioneInglese()
	{
		return"IT DESTROY TERRAINS AND SOLIDS";
	}
	public String nomeItaliano()
	{
		return"ESPLOSIONE";
	}
	public String nomeInglese()
	{
		return"EXPLOSION";
	}
	public int vita()
	{
		return 20;
	}
}
