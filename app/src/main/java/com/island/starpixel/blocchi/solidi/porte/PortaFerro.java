package com.island.starpixel.blocchi.solidi.porte;
import com.island.starpixel.*;
public class PortaFerro extends Porta
{
	public int immagine()
	{
		return R.drawable.portaferro;
	}
	public String nomeItaliano()
	{
		return"PORTA DI FERRO";
	}
	public String nomeInglese()
	{
		return"IRON DOOR";
	}
	public int vita()
	{
		return 150;
	}
}
