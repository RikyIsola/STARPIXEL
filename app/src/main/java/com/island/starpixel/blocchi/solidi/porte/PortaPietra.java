package com.island.starpixel.blocchi.solidi.porte;
import com.island.starpixel.*;
public class PortaPietra extends Porta
{
	public int immagine()
	{
		return R.drawable.portapietra;
	}
	public String nomeItaliano()
	{
		return"PORTA DI PIETRA";
	}
	public String nomeInglese()
	{
		return"STONE DOOR";
	}
	public int vita()
	{
		return 100;
	}
}
