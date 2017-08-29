package com.island.starpixel.blocchi.solidi.liquidi;
import com.island.starpixel.*;
public class Ghiaccio extends Liquido
{
	public int immagine()
	{
		return R.drawable.ghiaccio;
	}
	public String nomeItaliano()
	{
		return"GHIACCIO";
	}
	public String nomeInglese()
	{
		return"ICE";
	}
	public int vita()
	{
		return 3;
	}
}
