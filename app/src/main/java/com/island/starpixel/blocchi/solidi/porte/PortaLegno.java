package com.island.starpixel.blocchi.solidi.porte;
import com.island.starpixel.*;
public class PortaLegno extends Porta
{
	public int immagine()
	{
		return R.drawable.portalegno;
	}
	public String nomeItaliano()
	{
		return"PORTA DI LEGNO";
	}
	public String nomeInglese()
	{
		return"WOODEN DOOR";
	}
	public int vita()
	{
		return 25;
	}
}
