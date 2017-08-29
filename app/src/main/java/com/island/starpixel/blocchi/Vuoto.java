package com.island.starpixel.blocchi;
import com.island.starpixel.*;

public class Vuoto extends Blocco
{
	public int immagine()
	{
		return R.drawable.vuoto;
	}
	public String nomeItaliano()
	{
		return"VUOTO";
	}
	public String nomeInglese()
	{
		return"VOID";
	}
	public String descrizioneItaliano()
	{
		return"L'UNIVERSO É COMPOSTO PER LA MAGGIOR PARTE DA VUOTO";
	}
	public String descrizioneInglese()
	{
		return"THE UNIVERSE IS COMPOSED FOR THE GREATEST PART BY VOID";
	}
	public int vita()
	{
		return 1;
	}
}
